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

import com.entity.BangfupingjiaEntity;
import com.service.BangfupingjiaService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;

/**
 * 帮扶评价
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@RestController
@RequestMapping("/bangfupingjia")
public class BangfupingjiaController {
    @Autowired
    private BangfupingjiaService bangfupingjiaService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, BangfupingjiaEntity bangfupingjia, HttpServletRequest request) {
        EntityWrapper<BangfupingjiaEntity> ew = new EntityWrapper<BangfupingjiaEntity>();
        PageUtils page = bangfupingjiaService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfupingjia), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, BangfupingjiaEntity bangfupingjia, HttpServletRequest request) {
        EntityWrapper<BangfupingjiaEntity> ew = new EntityWrapper<BangfupingjiaEntity>();
        PageUtils page = bangfupingjiaService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfupingjia), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        BangfupingjiaEntity bangfupingjia = bangfupingjiaService.selectById(id);
        return R.ok().put("data", bangfupingjia);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        BangfupingjiaEntity bangfupingjia = bangfupingjiaService.selectById(id);
        return R.ok().put("data", bangfupingjia);
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BangfupingjiaEntity bangfupingjia, HttpServletRequest request) {
        bangfupingjia.setId(new Date().getTime());
        // 计算综合评分
        int zonghe = 0;
        int count = 0;
        if (bangfupingjia.getFuwutaidu() != null) {
            zonghe += bangfupingjia.getFuwutaidu();
            count++;
        }
        if (bangfupingjia.getBangfuxiaoguo() != null) {
            zonghe += bangfupingjia.getBangfuxiaoguo();
            count++;
        }
        if (bangfupingjia.getXiangyingsudu() != null) {
            zonghe += bangfupingjia.getXiangyingsudu();
            count++;
        }
        if (count > 0) {
            bangfupingjia.setZonghepingfen(zonghe / count);
        }
        bangfupingjiaService.insert(bangfupingjia);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody BangfupingjiaEntity bangfupingjia, HttpServletRequest request) {
        bangfupingjia.setId(new Date().getTime());
        // 计算综合评分
        int zonghe = 0;
        int count = 0;
        if (bangfupingjia.getFuwutaidu() != null) {
            zonghe += bangfupingjia.getFuwutaidu();
            count++;
        }
        if (bangfupingjia.getBangfuxiaoguo() != null) {
            zonghe += bangfupingjia.getBangfuxiaoguo();
            count++;
        }
        if (bangfupingjia.getXiangyingsudu() != null) {
            zonghe += bangfupingjia.getXiangyingsudu();
            count++;
        }
        if (count > 0) {
            bangfupingjia.setZonghepingfen(zonghe / count);
        }
        bangfupingjiaService.insert(bangfupingjia);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BangfupingjiaEntity bangfupingjia, HttpServletRequest request) {
        bangfupingjiaService.updateById(bangfupingjia);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        bangfupingjiaService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}
