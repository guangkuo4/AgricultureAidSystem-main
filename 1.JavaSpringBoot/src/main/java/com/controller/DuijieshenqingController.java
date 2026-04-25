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

import com.entity.DuijieshenqingEntity;
import com.entity.view.DuijieshenqingView;

import com.service.DuijieshenqingService;
import com.service.TokenService;
import com.service.MessageService;
import com.service.YonghuService;
import com.entity.MessageEntity;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 对接申请
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
@RestController
@RequestMapping("/duijieshenqing")
public class DuijieshenqingController {
    @Autowired
    private DuijieshenqingService duijieshenqingService;
    @Autowired
    private MessageService messageService;



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,DuijieshenqingEntity duijieshenqing,
		HttpServletRequest request){
        EntityWrapper<DuijieshenqingEntity> ew = new EntityWrapper<DuijieshenqingEntity>();

		PageUtils page = duijieshenqingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, duijieshenqing), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,DuijieshenqingEntity duijieshenqing, 
		HttpServletRequest request){
        EntityWrapper<DuijieshenqingEntity> ew = new EntityWrapper<DuijieshenqingEntity>();

		PageUtils page = duijieshenqingService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, duijieshenqing), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( DuijieshenqingEntity duijieshenqing){
        	EntityWrapper<DuijieshenqingEntity> ew = new EntityWrapper<DuijieshenqingEntity>();
       	ew.allEq(MPUtil.allEQMapPre( duijieshenqing, "duijieshenqing")); 
        return R.ok().put("data", duijieshenqingService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(DuijieshenqingEntity duijieshenqing){
        EntityWrapper< DuijieshenqingEntity> ew = new EntityWrapper< DuijieshenqingEntity>();
		ew.allEq(MPUtil.allEQMapPre( duijieshenqing, "duijieshenqing")); 
		DuijieshenqingView duijieshenqingView =  duijieshenqingService.selectView(ew);
		return R.ok("查询对接申请成功").put("data", duijieshenqingView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        DuijieshenqingEntity duijieshenqing = duijieshenqingService.selectById(id);
        return R.ok().put("data", duijieshenqing);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        DuijieshenqingEntity duijieshenqing = duijieshenqingService.selectById(id);
        return R.ok().put("data", duijieshenqing);
    }
    


    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DuijieshenqingEntity duijieshenqing, HttpServletRequest request){
        if(duijieshenqingService.selectCount(new EntityWrapper<DuijieshenqingEntity>().eq("shenqingbianhao", duijieshenqing.getShenqingbianhao()))>0) {
            return R.error("申请编号已存在");
        }
    	duijieshenqing.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(duijieshenqing);
    	DuijieshenqingEntity u = duijieshenqingService.selectOne(new EntityWrapper<DuijieshenqingEntity>().eq("shenqingbianhao", duijieshenqing.getShenqingbianhao()));
		if(u!=null) {
			return R.error("用户已存在");
		}
		duijieshenqing.setId(new Date().getTime());
        duijieshenqingService.insert(duijieshenqing);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody DuijieshenqingEntity duijieshenqing, HttpServletRequest request){
        if(duijieshenqingService.selectCount(new EntityWrapper<DuijieshenqingEntity>().eq("shenqingbianhao", duijieshenqing.getShenqingbianhao()))>0) {
            return R.error("申请编号已存在");
        }
    	duijieshenqing.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(duijieshenqing);
    	DuijieshenqingEntity u = duijieshenqingService.selectOne(new EntityWrapper<DuijieshenqingEntity>().eq("shenqingbianhao", duijieshenqing.getShenqingbianhao()));
		if(u!=null) {
			return R.error("用户已存在");
		}
		duijieshenqing.setId(new Date().getTime());
        duijieshenqingService.insert(duijieshenqing);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody DuijieshenqingEntity duijieshenqing, HttpServletRequest request){
        //ValidatorUtils.validateEntity(duijieshenqing);
        if(duijieshenqingService.selectCount(new EntityWrapper<DuijieshenqingEntity>().ne("id", duijieshenqing.getId()).eq("shenqingbianhao", duijieshenqing.getShenqingbianhao()))>0) {
            return R.error("申请编号已存在");
        }
        
        // 先获取原数据，检查是否是审核操作
        DuijieshenqingEntity oldEntity = duijieshenqingService.selectById(duijieshenqing.getId());
        duijieshenqingService.updateById(duijieshenqing);//全部更新
        
        // 检查是否是审核操作（sfsh 字段有变化）
        if (oldEntity != null && !oldEntity.getSfsh().equals(duijieshenqing.getSfsh())) {
            // 发送审核结果消息
            MessageEntity message = new MessageEntity();
            message.setUserid(duijieshenqing.getUserid());
            message.setTitle("对接申请审核结果");
            String content = "您的对接申请（" + duijieshenqing.getShenqingbianhao() + "）已完成审核。";
            if ("是".equals(duijieshenqing.getSfsh())) {
                content += "审核结果：通过";
            } else {
                content += "审核结果：驳回";
            }
            if (StringUtils.isNotBlank(duijieshenqing.getShhf())) {
                content += "，审核意见：" + duijieshenqing.getShhf();
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
    public R delete(@RequestBody Long[] ids){
        duijieshenqingService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	






}
