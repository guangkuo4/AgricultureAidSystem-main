package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.DuijieshenqingEntity;
import com.entity.view.DuijieshenqingView;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 对接申请
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface DuijieshenqingService extends IService<DuijieshenqingEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(Map<String, Object> params, com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
    
    DuijieshenqingView selectView(com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
    
    java.util.List<DuijieshenqingView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
    
    java.util.List<com.entity.vo.DuijieshenqingVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
    
    com.entity.vo.DuijieshenqingVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
}
