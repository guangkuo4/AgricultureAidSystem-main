package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.BangfuxuqiuEntity;
import com.entity.view.BangfuxuqiuView;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 帮扶需求
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfuxuqiuService extends IService<BangfuxuqiuEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(Map<String, Object> params, com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
    
    BangfuxuqiuView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
    
    java.util.List<BangfuxuqiuView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
    
    java.util.List<com.entity.vo.BangfuxuqiuVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
    
    com.entity.vo.BangfuxuqiuVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
}
