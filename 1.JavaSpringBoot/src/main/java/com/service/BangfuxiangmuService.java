package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.BangfuxiangmuEntity;
import com.entity.view.BangfuxiangmuView;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 帮扶项目
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfuxiangmuService extends IService<BangfuxiangmuEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(Map<String, Object> params, com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
    
    BangfuxiangmuView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
    
    java.util.List<BangfuxiangmuView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
    
    java.util.List<com.entity.vo.BangfuxiangmuVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
    
    com.entity.vo.BangfuxiangmuVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
}
