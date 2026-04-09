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
 * 帮扶实施
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@TableName("bangfushishi")
public class BangfushishiEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public BangfushishiEntity() {
		
	}
	
	public BangfushishiEntity(T t) {
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
	 * 实施编号
	 */
					
	private String shishibianhao;
	
	/**
	 * 需求编号
	 */
					
	private String xuqiubianhao;
	
	/**
	 * 资源编号
	 */
					
	private String ziyuanbianhao;
	
	/**
	 * 帮扶类型
	 */
					
	private String bangfuleixing;
	
	/**
	 * 帮扶形式
	 */
					
	private String bangfuxingshi;
	
	/**
	 * 对接时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date duijieshijian;
	
	/**
	 * 帮扶内容
	 */
					
	private String bangfuneirong;
	
	/**
	 * 现场照片
	 */
					
	private String xianchangzhaopian;
	
	/**
	 * 帮扶进度
	 */
					
	private String bangfujindu;
	
	/**
	 * 帮扶状态
	 */
					
	private String bangfuzhuangtai;
	
	/**
	 * 完成时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date wanchengshijian;
	
	/**
	 * 帮扶方账号
	 */
					
	private String bangfufangzhanghao;
	
	/**
	 * 需求方账号
	 */
					
	private String xuqiufangzhanghao;
	
	
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
	 * 设置：资源编号
	 */
	public void setZiyuanbianhao(String ziyuanbianhao) {
		this.ziyuanbianhao = ziyuanbianhao;
	}
	/**
	 * 获取：资源编号
	 */
	public String getZiyuanbianhao() {
		return ziyuanbianhao;
	}
	/**
	 * 设置：帮扶类型
	 */
	public void setBangfuleixing(String bangfuleixing) {
		this.bangfuleixing = bangfuleixing;
	}
	/**
	 * 获取：帮扶类型
	 */
	public String getBangfuleixing() {
		return bangfuleixing;
	}
	/**
	 * 设置：帮扶形式
	 */
	public void setBangfuxingshi(String bangfuxingshi) {
		this.bangfuxingshi = bangfuxingshi;
	}
	/**
	 * 获取：帮扶形式
	 */
	public String getBangfuxingshi() {
		return bangfuxingshi;
	}
	/**
	 * 设置：对接时间
	 */
	public void setDuijieshijian(Date duijieshijian) {
		this.duijieshijian = duijieshijian;
	}
	/**
	 * 获取：对接时间
	 */
	public Date getDuijieshijian() {
		return duijieshijian;
	}
	/**
	 * 设置：帮扶内容
	 */
	public void setBangfuneirong(String bangfuneirong) {
		this.bangfuneirong = bangfuneirong;
	}
	/**
	 * 获取：帮扶内容
	 */
	public String getBangfuneirong() {
		return bangfuneirong;
	}
	/**
	 * 设置：现场照片
	 */
	public void setXianchangzhaopian(String xianchangzhaopian) {
		this.xianchangzhaopian = xianchangzhaopian;
	}
	/**
	 * 获取：现场照片
	 */
	public String getXianchangzhaopian() {
		return xianchangzhaopian;
	}
	/**
	 * 设置：帮扶进度
	 */
	public void setBangfujindu(String bangfujindu) {
		this.bangfujindu = bangfujindu;
	}
	/**
	 * 获取：帮扶进度
	 */
	public String getBangfujindu() {
		return bangfujindu;
	}
	/**
	 * 设置：帮扶状态
	 */
	public void setBangfuzhuangtai(String bangfuzhuangtai) {
		this.bangfuzhuangtai = bangfuzhuangtai;
	}
	/**
	 * 获取：帮扶状态
	 */
	public String getBangfuzhuangtai() {
		return bangfuzhuangtai;
	}
	/**
	 * 设置：完成时间
	 */
	public void setWanchengshijian(Date wanchengshijian) {
		this.wanchengshijian = wanchengshijian;
	}
	/**
	 * 获取：完成时间
	 */
	public Date getWanchengshijian() {
		return wanchengshijian;
	}
	/**
	 * 设置：帮扶方账号
	 */
	public void setBangfufangzhanghao(String bangfufangzhanghao) {
		this.bangfufangzhanghao = bangfufangzhanghao;
	}
	/**
	 * 获取：帮扶方账号
	 */
	public String getBangfufangzhanghao() {
		return bangfufangzhanghao;
	}
	/**
	 * 设置：需求方账号
	 */
	public void setXuqiufangzhanghao(String xuqiufangzhanghao) {
		this.xuqiufangzhanghao = xuqiufangzhanghao;
	}
	/**
	 * 获取：需求方账号
	 */
	public String getXuqiufangzhanghao() {
		return xuqiufangzhanghao;
	}

}
