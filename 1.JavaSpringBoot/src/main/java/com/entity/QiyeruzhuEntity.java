package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 企业入驻申请
 * 数据库通用操作实体类（普通增删改查）
 */
@TableName("qiyeruzhu")
public class QiyeruzhuEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public QiyeruzhuEntity() {
    }

    public QiyeruzhuEntity(T t) {
        try {
            BeanUtils.copyProperties(this, t);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** 主键id */
    @TableId
    private Long id;

    /** 企业名称 */
    private String qiyemingcheng;

    /** 联系人 */
    private String lianxiren;

    /** 联系电话 */
    private String lianxidianhua;

    /** 入驻理由 */
    private String ruzhuliyou;

    /** 营业执照(图片/文件) */
    private String yingyezhizhao;

    /** 申请账号 */
    private String shenqingzhanghao;

    /** 申请密码 */
    private String shenqingmima;

    /** 是否审核 */
    private String sfsh;

    /** 审核回复 */
    private String shhf;

    /** 审核时间 */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date shenhetime;

    /** 申请人用户id */
    private Long userid;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date addtime;

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQiyemingcheng() {
        return qiyemingcheng;
    }

    public void setQiyemingcheng(String qiyemingcheng) {
        this.qiyemingcheng = qiyemingcheng;
    }

    public String getLianxiren() {
        return lianxiren;
    }

    public void setLianxiren(String lianxiren) {
        this.lianxiren = lianxiren;
    }

    public String getLianxidianhua() {
        return lianxidianhua;
    }

    public void setLianxidianhua(String lianxidianhua) {
        this.lianxidianhua = lianxidianhua;
    }

    public String getRuzhuliyou() {
        return ruzhuliyou;
    }

    public void setRuzhuliyou(String ruzhuliyou) {
        this.ruzhuliyou = ruzhuliyou;
    }

    public String getYingyezhizhao() {
        return yingyezhizhao;
    }

    public void setYingyezhizhao(String yingyezhizhao) {
        this.yingyezhizhao = yingyezhizhao;
    }

    public String getShenqingzhanghao() {
        return shenqingzhanghao;
    }

    public void setShenqingzhanghao(String shenqingzhanghao) {
        this.shenqingzhanghao = shenqingzhanghao;
    }

    public String getShenqingmima() {
        return shenqingmima;
    }

    public void setShenqingmima(String shenqingmima) {
        this.shenqingmima = shenqingmima;
    }

    public String getSfsh() {
        return sfsh;
    }

    public void setSfsh(String sfsh) {
        this.sfsh = sfsh;
    }

    public String getShhf() {
        return shhf;
    }

    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    public Date getShenhetime() {
        return shenhetime;
    }

    public void setShenhetime(Date shenhetime) {
        this.shenhetime = shenhetime;
    }
}

