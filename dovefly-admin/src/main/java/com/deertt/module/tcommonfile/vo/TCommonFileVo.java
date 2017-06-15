package com.deertt.module.tcommonfile.vo;

import java.sql.Timestamp;

import com.deertt.frame.base.vo.DvBaseVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author fengcm
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class TCommonFileVo extends DvBaseVo {
	private static final long serialVersionUID = 1L;
	//----------开始vo的属性 ----------
	//附件id
	private String file_id;
	
	//所属表名
	private String owner_table_name;
	
	//所属单据
	private String owner_bill_id;
	
	//所属列名
	private String owner_column_name;
	
	//文件名称
	private String file_name;
	
	//文件路径
	private String file_url;
	
	//文件类型
	private String file_type;
	
	//文件大小（单位：b）
	private int file_size;
	
	//备注
	private String remark;
	
	//可用状态
	private String usable_state;
	
	//排序编号
	private String order_code;
	
	//创建人员
	private String create_user_id;
	
	//创建时间
	private Timestamp create_time;
	
	//创建ip
	private String create_ip;
	
	//修改人员
	private String modify_user_id;
	
	//修改时间
	private Timestamp modify_time;
	
	//修改ip
	private String modify_ip;
	
	//预留字段1
	private String reserved_1;
	
	//预留字段2
	private String reserved_2;
	
	//预留字段3
	private String reserved_3;
	
	//预留字段4
	private String reserved_4;
	
	//预留字段5
	private String reserved_5;
	
	//----------结束vo的属性----------
	
	//----------开始vo的setter和getter方法----------
	/**
	 * 获得附件id
	 */
	public String getFile_id(){
		return file_id;
	}
	
	/**
	 * 设置附件id
	 */
	public void setFile_id(String file_id){
		this.file_id = file_id;
	}
	
	/**
	 * 获得所属表名
	 */
	public String getOwner_table_name(){
		return owner_table_name;
	}
	
	/**
	 * 设置所属表名
	 */
	public void setOwner_table_name(String owner_table_name){
		this.owner_table_name = owner_table_name;
	}
	
	/**
	 * 获得所属单据
	 */
	public String getOwner_bill_id(){
		return owner_bill_id;
	}
	
	/**
	 * 设置所属单据
	 */
	public void setOwner_bill_id(String owner_bill_id){
		this.owner_bill_id = owner_bill_id;
	}
	
	/**
	 * 获得所属列名
	 */
	public String getOwner_column_name(){
		return owner_column_name;
	}
	
	/**
	 * 设置所属列名
	 */
	public void setOwner_column_name(String owner_column_name){
		this.owner_column_name = owner_column_name;
	}
	
	/**
	 * 获得文件名称
	 */
	public String getFile_name(){
		return file_name;
	}
	
	/**
	 * 设置文件名称
	 */
	public void setFile_name(String file_name){
		this.file_name = file_name;
	}
	
	/**
	 * 获得文件路径
	 */
	public String getFile_url(){
		return file_url;
	}
	
	/**
	 * 设置文件路径
	 */
	public void setFile_url(String file_url){
		this.file_url = file_url;
	}
	
	/**
	 * 获得文件类型
	 */
	public String getFile_type(){
		return file_type;
	}
	
	/**
	 * 设置文件类型
	 */
	public void setFile_type(String file_type){
		this.file_type = file_type;
	}
	
	/**
	 * 获得文件大小（单位：b）
	 */
	public int getFile_size(){
		return file_size;
	}
	
	/**
	 * 设置文件大小（单位：b）
	 */
	public void setFile_size(int file_size){
		this.file_size = file_size;
	}
	
	/**
	 * 获得备注
	 */
	public String getRemark(){
		return remark;
	}
	
	/**
	 * 设置备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	
	/**
	 * 获得可用状态
	 */
	public String getUsable_state(){
		return usable_state;
	}
	
	/**
	 * 设置可用状态
	 */
	public void setUsable_state(String usable_state){
		this.usable_state = usable_state;
	}
	
	/**
	 * 获得排序编号
	 */
	public String getOrder_code(){
		return order_code;
	}
	
	/**
	 * 设置排序编号
	 */
	public void setOrder_code(String order_code){
		this.order_code = order_code;
	}
	
	/**
	 * 获得创建人员
	 */
	public String getCreate_user_id(){
		return create_user_id;
	}
	
	/**
	 * 设置创建人员
	 */
	public void setCreate_user_id(String create_user_id){
		this.create_user_id = create_user_id;
	}
	
	/**
	 * 获得创建时间
	 */
	public Timestamp getCreate_time(){
		return create_time;
	}
	
	/**
	 * 设置创建时间
	 */
	public void setCreate_time(Timestamp create_time){
		this.create_time = create_time;
	}
	
	/**
	 * 获得创建ip
	 */
	public String getCreate_ip(){
		return create_ip;
	}
	
	/**
	 * 设置创建ip
	 */
	public void setCreate_ip(String create_ip){
		this.create_ip = create_ip;
	}
	
	/**
	 * 获得修改人员
	 */
	public String getModify_user_id(){
		return modify_user_id;
	}
	
	/**
	 * 设置修改人员
	 */
	public void setModify_user_id(String modify_user_id){
		this.modify_user_id = modify_user_id;
	}
	
	/**
	 * 获得修改时间
	 */
	public Timestamp getModify_time(){
		return modify_time;
	}
	
	/**
	 * 设置修改时间
	 */
	public void setModify_time(Timestamp modify_time){
		this.modify_time = modify_time;
	}
	
	/**
	 * 获得修改ip
	 */
	public String getModify_ip(){
		return modify_ip;
	}
	
	/**
	 * 设置修改ip
	 */
	public void setModify_ip(String modify_ip){
		this.modify_ip = modify_ip;
	}
	
	/**
	 * 获得预留字段1
	 */
	public String getReserved_1(){
		return reserved_1;
	}
	
	/**
	 * 设置预留字段1
	 */
	public void setReserved_1(String reserved_1){
		this.reserved_1 = reserved_1;
	}
	
	/**
	 * 获得预留字段2
	 */
	public String getReserved_2(){
		return reserved_2;
	}
	
	/**
	 * 设置预留字段2
	 */
	public void setReserved_2(String reserved_2){
		this.reserved_2 = reserved_2;
	}
	
	/**
	 * 获得预留字段3
	 */
	public String getReserved_3(){
		return reserved_3;
	}
	
	/**
	 * 设置预留字段3
	 */
	public void setReserved_3(String reserved_3){
		this.reserved_3 = reserved_3;
	}
	
	/**
	 * 获得预留字段4
	 */
	public String getReserved_4(){
		return reserved_4;
	}
	
	/**
	 * 设置预留字段4
	 */
	public void setReserved_4(String reserved_4){
		this.reserved_4 = reserved_4;
	}
	
	/**
	 * 获得预留字段5
	 */
	public String getReserved_5(){
		return reserved_5;
	}
	
	/**
	 * 设置预留字段5
	 */
	public void setReserved_5(String reserved_5){
		this.reserved_5 = reserved_5;
	}
	
	//----------结束vo的setter和getter方法----------
	@Override
	public boolean isNew() {
		return file_id == null;
	}
}

