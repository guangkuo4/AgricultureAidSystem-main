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

import com.entity.ThumbsupEntity;
import com.entity.view.ThumbsupView;

import com.service.ThumbsupService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 点赞
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@RestController
@RequestMapping("/thumbsup")
public class ThumbsupController {
    @Autowired
    private ThumbsupService thumbsupService;



    
	@Autowired
	private TokenService tokenService;
	
	/**
     * 检查用户是否已点赞
     */
	@IgnoreAuth
    @RequestMapping("/hasLiked")
    public R hasLiked(@RequestParam Long userid, @RequestParam Long refid, @RequestParam String tablename){
        boolean hasLiked = thumbsupService.hasLiked(userid, refid, tablename);
        return R.ok().put("hasLiked", hasLiked);
    }

    /**
     * 点赞/取消点赞
     */
	@IgnoreAuth
    @RequestMapping("/toggleLike")
    public R toggleLike(@RequestParam Long userid, @RequestParam Long refid, @RequestParam String tablename){
        boolean isLiked = thumbsupService.toggleLike(userid, refid, tablename);
        int likeCount = thumbsupService.getLikeCount(refid, tablename);
        return R.ok().put("isLiked", isLiked).put("likeCount", likeCount);
    }

    /**
     * 获取点赞数量
     */
	@IgnoreAuth
    @RequestMapping("/getLikeCount")
    public R getLikeCount(@RequestParam Long refid, @RequestParam String tablename){
        int likeCount = thumbsupService.getLikeCount(refid, tablename);
        return R.ok().put("likeCount", likeCount);
    }

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,ThumbsupEntity thumbsup,
		HttpServletRequest request){
        EntityWrapper<ThumbsupEntity> ew = new EntityWrapper<ThumbsupEntity>();

		PageUtils page = thumbsupService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, thumbsup), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,ThumbsupEntity thumbsup, 
		HttpServletRequest request){
        EntityWrapper<ThumbsupEntity> ew = new EntityWrapper<ThumbsupEntity>();

		PageUtils page = thumbsupService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, thumbsup), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( ThumbsupEntity thumbsup){
        	EntityWrapper<ThumbsupEntity> ew = new EntityWrapper<ThumbsupEntity>();
       	ew.allEq(MPUtil.allEQMapPre( thumbsup, "thumbsup")); 
        return R.ok().put("data", thumbsupService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(ThumbsupEntity thumbsup){
        EntityWrapper< ThumbsupEntity> ew = new EntityWrapper< ThumbsupEntity>();
		ew.allEq(MPUtil.allEQMapPre( thumbsup, "thumbsup")); 
		ThumbsupView thumbsupView =  thumbsupService.selectView(ew);
		return R.ok("查询点赞成功").put("data", thumbsupView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        ThumbsupEntity thumbsup = thumbsupService.selectById(id);
        return R.ok().put("data", thumbsup);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        ThumbsupEntity thumbsup = thumbsupService.selectById(id);
        return R.ok().put("data", thumbsup);
    }
    


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ThumbsupEntity thumbsup, HttpServletRequest request){
    	thumbsup.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(thumbsup);
        thumbsupService.insert(thumbsup);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody ThumbsupEntity thumbsup, HttpServletRequest request){
    	thumbsup.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(thumbsup);
        thumbsupService.insert(thumbsup);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody ThumbsupEntity thumbsup, HttpServletRequest request){
        //ValidatorUtils.validateEntity(thumbsup);
        thumbsupService.updateById(thumbsup);//全部更新
        return R.ok();
    }





    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        thumbsupService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	






}
