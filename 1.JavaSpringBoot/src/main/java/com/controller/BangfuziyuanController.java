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

import com.entity.BangfuziyuanEntity;
import com.entity.view.BangfuziyuanView;

import com.service.BangfuziyuanService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 帮扶资源池
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@RestController
@RequestMapping("/bangfuziyuan")
public class BangfuziyuanController {
    @Autowired
    private BangfuziyuanService bangfuziyuanService;



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,BangfuziyuanEntity bangfuziyuan,
		HttpServletRequest request){
        EntityWrapper<BangfuziyuanEntity> ew = new EntityWrapper<BangfuziyuanEntity>();

		PageUtils page = bangfuziyuanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfuziyuan), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,BangfuziyuanEntity bangfuziyuan, 
		HttpServletRequest request){
        EntityWrapper<BangfuziyuanEntity> ew = new EntityWrapper<BangfuziyuanEntity>();

		PageUtils page = bangfuziyuanService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, bangfuziyuan), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( BangfuziyuanEntity bangfuziyuan){
        	EntityWrapper<BangfuziyuanEntity> ew = new EntityWrapper<BangfuziyuanEntity>();
       	ew.allEq(MPUtil.allEQMapPre( bangfuziyuan, "bangfuziyuan")); 
        return R.ok().put("data", bangfuziyuanService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(BangfuziyuanEntity bangfuziyuan){
        EntityWrapper< BangfuziyuanEntity> ew = new EntityWrapper< BangfuziyuanEntity>();
		ew.allEq(MPUtil.allEQMapPre( bangfuziyuan, "bangfuziyuan")); 
		BangfuziyuanView bangfuziyuanView =  bangfuziyuanService.selectView(ew);
		return R.ok("查询帮扶资源池成功").put("data", bangfuziyuanView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        BangfuziyuanEntity bangfuziyuan = bangfuziyuanService.selectById(id);
        return R.ok().put("data", bangfuziyuan);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        BangfuziyuanEntity bangfuziyuan = bangfuziyuanService.selectById(id);
        return R.ok().put("data", bangfuziyuan);
    }
    


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BangfuziyuanEntity bangfuziyuan, HttpServletRequest request){
        if(bangfuziyuanService.selectCount(new EntityWrapper<BangfuziyuanEntity>().eq("ziyuanbianhao", bangfuziyuan.getZiyuanbianhao()))>0) {
            return R.error("资源编号已存在");
        }
    	bangfuziyuan.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(bangfuziyuan);
    	BangfuziyuanEntity u = bangfuziyuanService.selectOne(new EntityWrapper<BangfuziyuanEntity>().eq("ziyuanbianhao", bangfuziyuan.getZiyuanbianhao()));
		if(u!=null) {
			return R.error("资源编号已存在");
		}
		bangfuziyuan.setId(new Date().getTime());
        bangfuziyuanService.insert(bangfuziyuan);
        return R.ok();
    }
    
    /**
     * 前端保存（游客可提交，便于演示；生产可去掉 IgnoreAuth 并强制 Token）
     */
	@IgnoreAuth
    @RequestMapping("/add")
    public R add(@RequestBody BangfuziyuanEntity bangfuziyuan, HttpServletRequest request){
    	if(StringUtils.isBlank(bangfuziyuan.getZiyuanbianhao())) {
    		return R.error("资源编号不能为空");
    	}
    	if(bangfuziyuanService.selectCount(new EntityWrapper<BangfuziyuanEntity>().eq("ziyuanbianhao", bangfuziyuan.getZiyuanbianhao()))>0) {
    		return R.error("资源编号已存在，请重试");
    	}
		bangfuziyuan.setId(new Date().getTime());
		if(bangfuziyuan.getSfsh()==null) {
			bangfuziyuan.setSfsh("待审核");
		}
        bangfuziyuanService.insert(bangfuziyuan);
        return R.ok();
    }

    /**
     * 修改（用户端「对接管理」审核；演示环境放开鉴权，生产请改为管理员角色校验）
     */
	@IgnoreAuth
    @RequestMapping("/update")
    public R update(@RequestBody BangfuziyuanEntity bangfuziyuan, HttpServletRequest request){
        //ValidatorUtils.validateEntity(bangfuziyuan);
        bangfuziyuanService.updateById(bangfuziyuan);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        bangfuziyuanService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<BangfuziyuanEntity> wrapper = new EntityWrapper<BangfuziyuanEntity>();
		int count = bangfuziyuanService.selectCount(MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(wrapper, new BangfuziyuanEntity()), map), map));
		
		return R.ok().put("count", count);
	}





}
