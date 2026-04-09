package com.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

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

import com.service.BangfuxuqiuService;
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
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody BangfuxuqiuEntity bangfuxuqiu, HttpServletRequest request) {
        bangfuxuqiu.setId(new Date().getTime());
        bangfuxuqiuService.insert(bangfuxuqiu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BangfuxuqiuEntity bangfuxuqiu, HttpServletRequest request) {
        bangfuxuqiuService.updateById(bangfuxuqiu);
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
