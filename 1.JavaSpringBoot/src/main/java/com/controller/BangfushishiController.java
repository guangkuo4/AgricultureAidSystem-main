package com.controller;

import java.util.Arrays;
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
import com.annotation.IgnoreAuth;

import com.entity.BangfushishiEntity;
import com.service.BangfushishiService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 帮扶实施
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@RestController
@RequestMapping("/bangfushishi")
public class BangfushishiController {
    @Autowired
    private BangfushishiService bangfushishiService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, BangfushishiEntity bangfushishi, HttpServletRequest request) {
        EntityWrapper<BangfushishiEntity> ew = new EntityWrapper<BangfushishiEntity>();
        PageUtils page = bangfushishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfushishi), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, BangfushishiEntity bangfushishi, HttpServletRequest request) {
        EntityWrapper<BangfushishiEntity> ew = new EntityWrapper<BangfushishiEntity>();
        PageUtils page = bangfushishiService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfushishi), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        BangfushishiEntity bangfushishi = bangfushishiService.selectById(id);
        return R.ok().put("data", bangfushishi);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        BangfushishiEntity bangfushishi = bangfushishiService.selectById(id);
        return R.ok().put("data", bangfushishi);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BangfushishiEntity bangfushishi, HttpServletRequest request) {
        bangfushishi.setId(new Date().getTime());
        bangfushishiService.insert(bangfushishi);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody BangfushishiEntity bangfushishi, HttpServletRequest request) {
        bangfushishi.setId(new Date().getTime());
        bangfushishiService.insert(bangfushishi);
        return R.ok();
    }

    /**
     * 修改（用户端更新对接进度；演示环境放开鉴权）
     */
    @IgnoreAuth
    @RequestMapping("/update")
    public R update(@RequestBody BangfushishiEntity bangfushishi, HttpServletRequest request) {
        bangfushishiService.updateById(bangfushishi);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        bangfushishiService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
