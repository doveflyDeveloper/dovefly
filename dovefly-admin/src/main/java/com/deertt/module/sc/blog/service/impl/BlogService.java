package com.deertt.module.sc.blog.service.impl;

import org.springframework.stereotype.Service;

import com.deertt.frame.base.service.impl.DvBaseService;
import com.deertt.module.sc.blog.dao.IBlogDao;
import com.deertt.module.sc.blog.service.IBlogService;
import com.deertt.module.sc.blog.util.IBlogConstants;
import com.deertt.module.sc.blog.vo.BlogVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@Service
public class BlogService extends DvBaseService<IBlogDao, BlogVo, Integer> implements IBlogService, IBlogConstants {
	
}
