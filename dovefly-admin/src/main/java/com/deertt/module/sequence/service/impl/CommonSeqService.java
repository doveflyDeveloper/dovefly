package com.deertt.module.sequence.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.deertt.frame.base.exception.BusinessException;
import com.deertt.module.sequence.service.ICommonSeqService;
import com.deertt.module.sequence.service.ISequenceCodeService;
import com.deertt.module.sequence.service.ISequenceService;
import com.deertt.module.sequence.vo.SequenceVo;

/**
 * Created by dove.zhang on 2017/1/23.
 */
@Service
public class CommonSeqService implements ICommonSeqService {
	public static Logger logger = Logger.getLogger(CommonSeqService.class);
	
	@Autowired
	private ISequenceCodeService sequenceCodeService;
	
	@Autowired
	private ISequenceService sequenceService;

	private static final long MAX_LONG = 999999999999999L;
	private static final int SEQUENCE_STEP = 100;
	private static final int DB_PRODUCE_SEQUENCE_LENGTH = 15;
	private static final String DATE_FORMAT_TEMPLATE = "yyyyMMddHHmmss";
	
//	private static Set<String> keySet = new HashSet<String>();
	
	private static Map<String, String> seqCodeMap = new HashMap<>();//取数据库sequence对应的code放入内存中
	
	private static Map<String, SeqCache> seqCacheMap = new ConcurrentHashMap<>();
	
	public static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT_TEMPLATE);
		}
	};

    public String queryNextSequenceId(String sequenceName) {
    	String dateLength14 = dateFormat.get().format(new Date());
        String code = this.getBizCode(sequenceName);
        String nextSeq = this.getNextSeq(sequenceName);
        String sequence = dateLength14 + code + nextSeq;
//        if (keySet.contains(code + nextSeq)) {
//        	System.out.println("---------------------存在" + code + nextSeq);
//        } else {
//        	keySet.add(code + nextSeq);
//        }
//        logger.info("sequenceName:{},序列号为:{}", sequenceName, sequence);
        return sequence;
    }
    
    /**
     * 获取业务编号（先从缓存取，取不到再尝试从数据库取）
     * @param sequenceName
     * @return
     */
    public String getBizCode(String sequenceName) {
    	if (StringUtils.isBlank(sequenceName)) {
            throw new BusinessException("序列号名称不能为空");
        }
        String code = seqCodeMap.get(sequenceName);
        if (code == null) {
            code = sequenceCodeService.findCodeBySeqName(sequenceName);
            if (StringUtils.isBlank(code)) {
                throw new BusinessException("序列号【" + sequenceName + "】不存在");
            }
            seqCodeMap.put(sequenceName, code);
        }
        return code;
    }
    
    /**
     * 返回当前序列的值（先从缓存取，如果缓存预取的值已用完，则从数据库重新预取100个并缓存），补足为15位字符串返回
     * @param sequenceName
     * @return
     * @throws OspException
     */
    public String getNextSeq(String sequenceName) {
        SeqCache sc = seqCacheMap.get(sequenceName);
        if (sc == null) {
        	synchronized (this) {
        		sc = seqCacheMap.get(sequenceName);
        		if (sc == null) {
        			sc = new SeqCache(sequenceName);
        			seqCacheMap.put(sequenceName, sc);
        		}
        	}
        }
        long currentValue = sc.nextSeq();
        logger.error("*/INSERT INTO bus_sequence_result (thread_id, seq_value) VALUES (" + (Thread.currentThread().getId()) + ", " + currentValue + ");");
        return StringUtils.leftPad(String.valueOf(currentValue), DB_PRODUCE_SEQUENCE_LENGTH, "0");
    }
    
    private class SeqCache {
        
        private String seqName;
        
        private long currentValue;
        
        private long loadDbThreshold;
        
        public SeqCache(String seqName) {
        	this.seqName = seqName;
        	this.currentValue = 0;
        	this.loadDbThreshold = 0;
        }

        public synchronized long nextSeq() {
            ++currentValue;
            if (currentValue >= loadDbThreshold) {
            	//创建缓存序列对象，即从数据库拉取最新的序列值
            	SequenceVo sequenceVo = loadCurrentSeqFromDb();
            	
            	currentValue = sequenceVo.getSequence_value();
            	if (currentValue >= MAX_LONG) {
            		currentValue = 0;
            	}
            	
            	loadDbThreshold = currentValue + SEQUENCE_STEP;
            	if (loadDbThreshold > MAX_LONG) {
            		loadDbThreshold = MAX_LONG;
            	}
            	
            }
            return currentValue;
        }
        
        /**
         * 从数据库预加载{SEQUENCE_STEP}个新的序列号
         * @param sequenceName
         * @return
         * @throws OspException
         */
        private SequenceVo loadCurrentSeqFromDb() {
            SequenceVo sequenceVo = sequenceService.findBySeqName(this.seqName);
            if (sequenceVo == null) {
                try {
                    sequenceVo = new SequenceVo();
                    sequenceVo.setSequence_name(this.seqName);
                    sequenceVo.setSequence_step(SEQUENCE_STEP);
                    sequenceVo.setSequence_value(SEQUENCE_STEP + 0L);
                    sequenceVo.setVersion(0L);
                    sequenceService.insert(sequenceVo);
//                    logger.info("create db sequence success,sequenceName={}", this.seqName);
                } catch (DuplicateKeyException e) {
                    logger.info("插入失败");
                    sequenceVo = sequenceService.findBySeqName(this.seqName);
                    sequenceVo.setSequence_value(sequenceVo.getSequence_value() + SEQUENCE_STEP);
                    updateSequence(sequenceVo);
                }
            } else {
            	sequenceVo.setSequence_value(sequenceVo.getSequence_value() + SEQUENCE_STEP);
                updateSequence(sequenceVo);
            }
            
            return sequenceVo;
        }
        
        /**
         * 乐观锁更新
         * @param seqVo
         * @throws OspException
         */
        private void updateSequence(SequenceVo seqVo) {
            //如果更新失败,重新查找 再次更新
        	int updateStatus = 0;
            for (int i = 0; i < 3; i++) {
            	updateStatus = sequenceService.updateByIdAndVersion(seqVo.getSequence_value(), seqVo.getId(), seqVo.getVersion());
            	if (updateStatus == 1) {
            		break;
            	}
            	SequenceVo currModel = sequenceService.findBySeqName(seqVo.getSequence_name());
            	seqVo.setSequence_value(currModel.getSequence_value() + SEQUENCE_STEP);
            	seqVo.setVersion(currModel.getVersion());
            }
            if (updateStatus != 1) {
                logger.info("序列号更新失败!");
                new BusinessException("序列号【" + seqVo.getSequence_name() + "更新失败");
            }
        }
    }

}
