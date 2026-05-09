package com.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.BangfuxuqiuEntity;
import com.entity.view.BangfuxuqiuView;
import com.entity.MessageEntity;

import com.service.BangfuxuqiuService;
import com.service.MessageService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 帮扶需求
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@RestController
@RequestMapping("/bangfuxuqiu")
public class BangfuxuqiuController {
    @Autowired
    private BangfuxuqiuService bangfuxuqiuService;
    @Autowired
    private MessageService messageService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, BangfuxuqiuEntity bangfuxuqiu, HttpServletRequest request) {
        EntityWrapper<BangfuxuqiuEntity> ew = new EntityWrapper<BangfuxuqiuEntity>();
        PageUtils page = bangfuxuqiuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfuxuqiu), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, BangfuxuqiuEntity bangfuxuqiu, HttpServletRequest request) {
        EntityWrapper<BangfuxuqiuEntity> ew = new EntityWrapper<BangfuxuqiuEntity>();
        PageUtils page = bangfuxuqiuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfuxuqiu), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        BangfuxuqiuEntity bangfuxuqiu = bangfuxuqiuService.selectById(id);
        return R.ok().put("data", bangfuxuqiu);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        BangfuxuqiuEntity bangfuxuqiu = bangfuxuqiuService.selectById(id);
        return R.ok().put("data", bangfuxuqiu);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BangfuxuqiuEntity bangfuxuqiu, HttpServletRequest request) {
        if (bangfuxuqiuService.selectCount(new EntityWrapper<BangfuxuqiuEntity>().eq("xuqiubianhao", bangfuxuqiu.getXuqiubianhao())) > 0) {
            return R.error("需求编号已存在");
        }
        bangfuxuqiu.setId(new Date().getTime());
        bangfuxuqiuService.insert(bangfuxuqiu);
        return R.ok();
    }
    
    /**
     * 前端保存（游客可提交；生产可去掉 IgnoreAuth）
     */
    @IgnoreAuth
    @RequestMapping("/add")
    public R add(@RequestBody BangfuxuqiuEntity bangfuxuqiu, HttpServletRequest request) {
        if (StringUtils.isBlank(bangfuxuqiu.getXuqiubianhao())) {
            return R.error("需求编号不能为空");
        }
        if (bangfuxuqiuService.selectCount(new EntityWrapper<BangfuxuqiuEntity>().eq("xuqiubianhao", bangfuxuqiu.getXuqiubianhao())) > 0) {
            return R.error("需求编号已存在，请重试");
        }
        bangfuxuqiu.setId(new Date().getTime());
        if (bangfuxuqiu.getSfsh() == null) {
            bangfuxuqiu.setSfsh("待审核");
        }
        bangfuxuqiuService.insert(bangfuxuqiu);
        return R.ok();
    }

    /**
     * 修改（用户端审核需求；演示环境放开鉴权）
     */
    @IgnoreAuth
    @RequestMapping("/update")
    public R update(@RequestBody BangfuxuqiuEntity bangfuxuqiu, HttpServletRequest request) {
        // 获取原数据，检查是否是审核操作
        BangfuxuqiuEntity oldEntity = bangfuxuqiuService.selectById(bangfuxuqiu.getId());
        bangfuxuqiuService.updateById(bangfuxuqiu);
        
        // 检查是否是审核操作（sfsh 字段有变化）
        if (oldEntity != null && StringUtils.isNotBlank(bangfuxuqiu.getSfsh())
                && !StringUtils.equals(oldEntity.getSfsh(), bangfuxuqiu.getSfsh())) {
            // 发送审核结果消息
            MessageEntity message = new MessageEntity();
            message.setUserid(oldEntity.getUserid());
            message.setTitle("需求审核结果");
            String content = "您的帮扶需求（" + oldEntity.getXuqiubianhao() + "）已完成审核。";
            if ("通过".equals(bangfuxuqiu.getSfsh()) || "已通过".equals(bangfuxuqiu.getSfsh()) || "是".equals(bangfuxuqiu.getSfsh())) {
                content += "审核结果：通过";
            } else if ("驳回".equals(bangfuxuqiu.getSfsh()) || "已拒绝".equals(bangfuxuqiu.getSfsh())) {
                content += "审核结果：驳回";
            } else {
                content += "审核结果：" + bangfuxuqiu.getSfsh();
            }
            if (StringUtils.isNotBlank(bangfuxuqiu.getShhf())) {
                content += "，审核意见：" + bangfuxuqiu.getShhf();
            }
            message.setContent(content);
            message.setStatus("未读");
            message.setType("审核通知");
            message.setAddtime(new Date());
            messageService.insert(message);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        bangfuxuqiuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
