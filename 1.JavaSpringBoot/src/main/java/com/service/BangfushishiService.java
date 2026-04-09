package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.BangfushishiEntity;
import com.entity.view.BangfushishiView;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 帮扶实施
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfushishiService extends IService<BangfushishiEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(Map<String, Object> params, com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
    
    BangfushishiView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
    
    java.util.List<BangfushishiView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
    
    java.util.List<com.entity.vo.BangfushishiVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
    
    com.entity.vo.BangfushishiVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
}
