package com.entity.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 企业入驻申请
 * 手机端接口返回实体辅助类
 */
public class QiyeruzhuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String qiyemingcheng;
    private String lianxiren;
    private String lianxidianhua;
    private String yingyezhizhao;
    private String shenqingzhanghao;
    private String sfsh;
    private String shhf;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private Date shenhetime;

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

