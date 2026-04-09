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
 * 帮扶资源池
 * 数据库通用操作实体类（普通增删改查）
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@TableName("bangfuziyuan")
public class BangfuziyuanEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;


	public BangfuziyuanEntity() {
		
	}
	
	public BangfuziyuanEntity(T t) {
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
	 * 资源编号
	 */
					
	private String ziyuanbianhao;
	
	/**
	 * 帮扶类型
	 */
					
	private String bangfuleixing;
	
	/**
	 * 帮扶方名称
	 */
					
	private String bangfufangming;
	
	/**
	 * 帮扶方类型
	 */
					
	private String bangfufangleixing;
	
	/**
	 * 擅长领域
	 */
					
	private String shanchanglingyu;
	
	/**
	 * 所在地区
	 */
					
	private String suozaidiqu;
	
	/**
	 * 响应时效
	 */
					
	private String xiangyingshixiao;
	
	/**
	 * 可提供帮扶内容
	 */
					
	private String bangfuneirong;
	
	/**
	 * 资质照片
	 */
					
	private String zizhengzhaopian;
	
	/**
	 * 联系人
	 */
					
	private String lianxiren;
	
	/**
	 * 联系电话
	 */
					
	private String lianxidianhua;
	
	/**
	 * 是否审核
	 */
					
	private String sfsh;
	
	/**
	 * 审核回复
	 */
					
	private String shhf;
	
	/**
	 * 发布号
	 */
					
	private String fabuhao;
	
	/**
	 * 最近点击时间
	 */
				
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat 		
	private Date clicktime;
	
	/**
	 * 点击次数
	 */
					
	private Integer clicknum;
	
	
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
	 * 设置：帮扶方名称
	 */
	public void setBangfufangming(String bangfufangming) {
		this.bangfufangming = bangfufangming;
	}
	/**
	 * 获取：帮扶方名称
	 */
	public String getBangfufangming() {
		return bangfufangming;
	}
	/**
	 * 设置：帮扶方类型
	 */
	public void setBangfufangleixing(String bangfufangleixing) {
		this.bangfufangleixing = bangfufangleixing;
	}
	/**
	 * 获取：帮扶方类型
	 */
	public String getBangfufangleixing() {
		return bangfufangleixing;
	}
	/**
	 * 设置：擅长领域
	 */
	public void setShanchanglingyu(String shanchanglingyu) {
		this.shanchanglingyu = shanchanglingyu;
	}
	/**
	 * 获取：擅长领域
	 */
	public String getShanchanglingyu() {
		return shanchanglingyu;
	}
	/**
	 * 设置：所在地区
	 */
	public void setSuozaidiqu(String suozaidiqu) {
		this.suozaidiqu = suozaidiqu;
	}
	/**
	 * 获取：所在地区
	 */
	public String getSuozaidiqu() {
		return suozaidiqu;
	}
	/**
	 * 设置：响应时效
	 */
	public void setXiangyingshixiao(String xiangyingshixiao) {
		this.xiangyingshixiao = xiangyingshixiao;
	}
	/**
	 * 获取：响应时效
	 */
	public String getXiangyingshixiao() {
		return xiangyingshixiao;
	}
	/**
	 * 设置：可提供帮扶内容
	 */
	public void setBangfuneirong(String bangfuneirong) {
		this.bangfuneirong = bangfuneirong;
	}
	/**
	 * 获取：可提供帮扶内容
	 */
	public String getBangfuneirong() {
		return bangfuneirong;
	}
	/**
	 * 设置：资质照片
	 */
	public void setZizhengzhaopian(String zizhengzhaopian) {
		this.zizhengzhaopian = zizhengzhaopian;
	}
	/**
	 * 获取：资质照片
	 */
	public String getZizhengzhaopian() {
		return zizhengzhaopian;
	}
	/**
	 * 设置：联系人
	 */
	public void setLianxiren(String lianxiren) {
		this.lianxiren = lianxiren;
	}
	/**
	 * 获取：联系人
	 */
	public String getLianxiren() {
		return lianxiren;
	}
	/**
	 * 设置：联系电话
	 */
	public void setLianxidianhua(String lianxidianhua) {
		this.lianxidianhua = lianxidianhua;
	}
	/**
	 * 获取：联系电话
	 */
	public String getLianxidianhua() {
		return lianxidianhua;
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
	 * 设置：发布号
	 */
	public void setFabuhao(String fabuhao) {
		this.fabuhao = fabuhao;
	}
	/**
	 * 获取：发布号
	 */
	public String getFabuhao() {
		return fabuhao;
	}
	/**
	 * 设置：最近点击时间
	 */
	public void setClicktime(Date clicktime) {
		this.clicktime = clicktime;
	}
	/**
	 * 获取：最近点击时间
	 */
	public Date getClicktime() {
		return clicktime;
	}
	/**
	 * 设置：点击次数
	 */
	public void setClicknum(Integer clicknum) {
		this.clicknum = clicknum;
	}
	/**
	 * 获取：点击次数
	 */
	public Integer getClicknum() {
		return clicknum;
	}

}
