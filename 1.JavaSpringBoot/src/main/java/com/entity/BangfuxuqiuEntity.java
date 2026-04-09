package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 帮扶需求
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@TableName("bangfuxuqiu")
public class BangfuxuqiuEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public BangfuxuqiuEntity() {
		
	}
	
	public BangfuxuqiuEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 主键id
	 */
    @TableId(type = IdType.AUTO)
    private Long id;
	/**
	 * 需求编号
	 */
					
	private String xuqiubianhao;
	
	/**
	 * 需求类型
	 */
					
	private String xuqiuleixing;
	
	/**
	 * 需求描述
	 */
					
	private String xuqiumiaoshu;
	
	/**
	 * 需求图片
	 */
					
	private String xuqiutupian;
	
	/**
	 * 需求紧急程度
	 */
					
	private String xuqiujinji;
	
	/**
	 * 期望帮扶时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat 		
	private Date qiwangshijian;
	
	/**
	 * 具体地址
	 */
					
	private String jutidizhi;
	
	/**
	 * 申请人账号
	 */
					
	private String shenqingrenzhanghao;
	
	/**
	 * 申请人姓名
	 */
					
	private String shenqingrenxingming;
	
	/**
	 * 申请日期
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
	@DateTimeFormat 		
	private Date shenqingridi;
	
	/**
	 * 是否审核
	 */
					
	private String sfsh;
	
	/**
	 * 审核回复
	 */
					
	private String shhf;
	
	/**
	 * 用户id
	 */
					
	private Long userid;
	
	
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
	private Date addtime;

	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 设置：需求编号
	 */
	public void setXuqiubianhao(String xuqiubianhao) {
		this.xuqiubianhao = xuqiubianhao;
	}
	/**
	 * 获取：需求编号
	 */
	public String getXuqiubianhao() {
		return xuqiubianhao;
	}
	/**
	 * 设置：需求类型
	 */
	public void setXuqiuleixing(String xuqiuleixing) {
		this.xuqiuleixing = xuqiuleixing;
	}
	/**
	 * 获取：需求类型
	 */
	public String getXuqiuleixing() {
		return xuqiuleixing;
	}
	/**
	 * 设置：需求描述
	 */
	public void setXuqiumiaoshu(String xuqiumiaoshu) {
		this.xuqiumiaoshu = xuqiumiaoshu;
	}
	/**
	 * 获取：需求描述
	 */
	public String getXuqiumiaoshu() {
		return xuqiumiaoshu;
	}
	/**
	 * 设置：需求图片
	 */
	public void setXuqiutupian(String xuqiutupian) {
		this.xuqiutupian = xuqiutupian;
	}
	/**
	 * 获取：需求图片
	 */
	public String getXuqiutupian() {
		return xuqiutupian;
	}
	/**
	 * 设置：需求紧急程度
	 */
	public void setXuqiujinji(String xuqiujinji) {
		this.xuqiujinji = xuqiujinji;
	}
	/**
	 * 获取：需求紧急程度
	 */
	public String getXuqiujinji() {
		return xuqiujinji;
	}
	/**
	 * 设置：期望帮扶时间
	 */
	public void setQiwangshijian(Date qiwangshijian) {
		this.qiwangshijian = qiwangshijian;
	}
	/**
	 * 获取：期望帮扶时间
	 */
	public Date getQiwangshijian() {
		return qiwangshijian;
	}
	/**
	 * 设置：具体地址
	 */
	public void setJutidizhi(String jutidizhi) {
		this.jutidizhi = jutidizhi;
	}
	/**
	 * 获取：具体地址
	 */
	public String getJutidizhi() {
		return jutidizhi;
	}
	/**
	 * 设置：申请人账号
	 */
	public void setShenqingrenzhanghao(String shenqingrenzhanghao) {
		this.shenqingrenzhanghao = shenqingrenzhanghao;
	}
	/**
	 * 获取：申请人账号
	 */
	public String getShenqingrenzhanghao() {
		return shenqingrenzhanghao;
	}
	/**
	 * 设置：申请人姓名
	 */
	public void setShenqingrenxingming(String shenqingrenxingming) {
		this.shenqingrenxingming = shenqingrenxingming;
	}
	/**
	 * 获取：申请人姓名
	 */
	public String getShenqingrenxingming() {
		return shenqingrenxingming;
	}
	/**
	 * 设置：申请日期
	 */
	public void setShenqingridi(Date shenqingridi) {
		this.shenqingridi = shenqingridi;
	}
	/**
	 * 获取：申请日期
	 */
	public Date getShenqingridi() {
		return shenqingridi;
	}
	/**
	 * 设置：是否审核
	 */
	public void setSfsh(String sfsh) {
		this.sfsh = sfsh;
	}
	/**
	 * 获取：是否审核
	 */
	public String getSfsh() {
		return sfsh;
	}
	/**
	 * 设置：审核回复
	 */
	public void setShhf(String shhf) {
		this.shhf = shhf;
	}
	/**
	 * 获取：审核回复
	 */
	public String getShhf() {
		return shhf;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserid() {
		return userid;
	}

}
