package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.BangfuxiangmuEntity;
import com.entity.view.BangfuxiangmuView;

import com.service.BangfuxiangmuService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 帮扶项目
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@RestController
@RequestMapping("/bangfuxiangmu")
public class BangfuxiangmuController {
    @Autowired
    private BangfuxiangmuService bangfuxiangmuService;



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,BangfuxiangmuEntity bangfuxiangmu,
		HttpServletRequest request){
        EntityWrapper<BangfuxiangmuEntity> ew = new EntityWrapper<BangfuxiangmuEntity>();

		PageUtils page = bangfuxiangmuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfuxiangmu), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,BangfuxiangmuEntity bangfuxiangmu, 
		HttpServletRequest request){
        EntityWrapper<BangfuxiangmuEntity> ew = new EntityWrapper<BangfuxiangmuEntity>();

		PageUtils page = bangfuxiangmuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfuxiangmu), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( BangfuxiangmuEntity bangfuxiangmu){
        	EntityWrapper<BangfuxiangmuEntity> ew = new EntityWrapper<BangfuxiangmuEntity>();
       	ew.allEq(MPUtil.allEQMapPre( bangfuxiangmu, "bangfuxiangmu")); 
        return R.ok().put("data", bangfuxiangmuService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(BangfuxiangmuEntity bangfuxiangmu){
        EntityWrapper< BangfuxiangmuEntity> ew = new EntityWrapper< BangfuxiangmuEntity>();
		ew.allEq(MPUtil.allEQMapPre( bangfuxiangmu, "bangfuxiangmu")); 
		BangfuxiangmuView bangfuxiangmuView =  bangfuxiangmuService.selectView(ew);
		return R.ok("查询帮扶项目成功").put("data", bangfuxiangmuView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BangfuxiangmuEntity bangfuxiangmu = bangfuxiangmuService.selectById(id);
        return R.ok().put("data", bangfuxiangmu);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        BangfuxiangmuEntity bangfuxiangmu = bangfuxiangmuService.selectById(id);
        return R.ok().put("data", bangfuxiangmu);
    }
    


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BangfuxiangmuEntity bangfuxiangmu, HttpServletRequest request){
        if(bangfuxiangmuService.selectCount(new EntityWrapper<BangfuxiangmuEntity>().eq("xiangmubianhao", bangfuxiangmu.getXiangmubianhao()))>0) {
            return R.error("项目编号已存在");
        }
    	bangfuxiangmu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(bangfuxiangmu);
    	BangfuxiangmuEntity u = bangfuxiangmuService.selectOne(new EntityWrapper<BangfuxiangmuEntity>().eq("xiangmubianhao", bangfuxiangmu.getXiangmubianhao()));
		if(u!=null) {
			return R.error("用户已存在");
		}
		bangfuxiangmu.setId(new Date().getTime());
        bangfuxiangmuService.insert(bangfuxiangmu);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody BangfuxiangmuEntity bangfuxiangmu, HttpServletRequest request){
        if(bangfuxiangmuService.selectCount(new EntityWrapper<BangfuxiangmuEntity>().eq("xiangmubianhao", bangfuxiangmu.getXiangmubianhao()))>0) {
            return R.error("项目编号已存在");
        }
    	bangfuxiangmu.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(bangfuxiangmu);
    	BangfuxiangmuEntity u = bangfuxiangmuService.selectOne(new EntityWrapper<BangfuxiangmuEntity>().eq("xiangmubianhao", bangfuxiangmu.getXiangmubianhao()));
		if(u!=null) {
			return R.error("用户已存在");
		}
		bangfuxiangmu.setId(new Date().getTime());
        bangfuxiangmuService.insert(bangfuxiangmu);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody BangfuxiangmuEntity bangfuxiangmu, HttpServletRequest request){
        //ValidatorUtils.validateEntity(bangfuxiangmu);
        if(bangfuxiangmuService.selectCount(new EntityWrapper<BangfuxiangmuEntity>().ne("id", bangfuxiangmu.getId()).eq("xiangmubianhao", bangfuxiangmu.getXiangmubianhao()))>0) {
            return R.error("项目编号已存在");
        }
        bangfuxiangmuService.updateById(bangfuxiangmu);//全部更新
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        bangfuxiangmuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	






}
