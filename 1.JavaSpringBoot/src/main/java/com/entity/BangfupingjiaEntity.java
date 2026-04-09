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
 * 帮扶评价
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@TableName("bangfupingjia")
public class BangfupingjiaEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public BangfupingjiaEntity() {
		
	}
	
	public BangfupingjiaEntity(T t) {
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
	 * 评价编号
	 */
					
	private String pingjiabianhao;
	
	/**
	 * 实施编号
	 */
					
	private String shishibianhao;
	
	/**
	 * 服务态度(1-5星)
	 */
					
	private Integer fuwutaidu;
	
	/**
	 * 帮扶效果(1-5星)
	 */
					
	private Integer bangfuxiaoguo;
	
	/**
	 * 响应速度(1-5星)
	 */
					
	private Integer xiangyingsudu;
	
	/**
	 * 综合评分
	 */
					
	private Integer zonghepingfen;
	
	/**
	 * 评价文字
	 */
					
	private String pingjias;
	
	/**
	 * 帮扶方评价
	 */
					
	private String bangfufangpingjia;
	
	/**
	 * 需求方评价
	 */
					
	private String xuqiufangpingjia;
	
	/**
	 * 是否归档
	 */
					
	private String shifouguidang;
	
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
	 * 设置：评价编号
	 */
	public void setPingjiabianhao(String pingjiabianhao) {
		this.pingjiabianhao = pingjiabianhao;
	}
	/**
	 * 获取：评价编号
	 */
	public String getPingjiabianhao() {
		return pingjiabianhao;
	}
	/**
	 * 设置：实施编号
	 */
	public void setShishibianhao(String shishibianhao) {
		this.shishibianhao = shishibianhao;
	}
	/**
	 * 获取：实施编号
	 */
	public String getShishibianhao() {
		return shishibianhao;
	}
	/**
	 * 设置：服务态度(1-5星)
	 */
	public void setFuwutaidu(Integer fuwutaidu) {
		this.fuwutaidu = fuwutaidu;
	}
	/**
	 * 获取：服务态度(1-5星)
	 */
	public Integer getFuwutaidu() {
		return fuwutaidu;
	}
	/**
	 * 设置：帮扶效果(1-5星)
	 */
	public void setBangfuxiaoguo(Integer bangfuxiaoguo) {
		this.bangfuxiaoguo = bangfuxiaoguo;
	}
	/**
	 * 获取：帮扶效果(1-5星)
	 */
	public Integer getBangfuxiaoguo() {
		return bangfuxiaoguo;
	}
	/**
	 * 设置：响应速度(1-5星)
	 */
	public void setXiangyingsudu(Integer xiangyingsudu) {
		this.xiangyingsudu = xiangyingsudu;
	}
	/**
	 * 获取：响应速度(1-5星)
	 */
	public Integer getXiangyingsudu() {
		return xiangyingsudu;
	}
	/**
	 * 设置：综合评分
	 */
	public void setZonghepingfen(Integer zonghepingfen) {
		this.zonghepingfen = zonghepingfen;
	}
	/**
	 * 获取：综合评分
	 */
	public Integer getZonghepingfen() {
		return zonghepingfen;
	}
	/**
	 * 设置：评价文字
	 */
	public void setPingjias(String pingjias) {
		this.pingjias = pingjias;
	}
	/**
	 * 获取：评价文字
	 */
	public String getPingjias() {
		return pingjias;
	}
	/**
	 * 设置：帮扶方评价
	 */
	public void setBangfufangpingjia(String bangfufangpingjia) {
		this.bangfufangpingjia = bangfufangpingjia;
	}
	/**
	 * 获取：帮扶方评价
	 */
	public String getBangfufangpingjia() {
		return bangfufangpingjia;
	}
	/**
	 * 设置：需求方评价
	 */
	public void setXuqiufangpingjia(String xuqiufangpingjia) {
		this.xuqiufangpingjia = xuqiufangpingjia;
	}
	/**
	 * 获取：需求方评价
	 */
	public String getXuqiufangpingjia() {
		return xuqiufangpingjia;
	}
	/**
	 * 设置：是否归档
	 */
	public void setShifouguidang(String shifouguidang) {
		this.shifouguidang = shifouguidang;
	}
	/**
	 * 获取：是否归档
	 */
	public String getShifouguidang() {
		return shifouguidang;
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
