package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.BangfuziyuanEntity;
import com.entity.view.BangfuziyuanView;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 帮扶资源池
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfuziyuanService extends IService<BangfuziyuanEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(Map<String, Object> params, com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
    
    BangfuziyuanView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
    
    java.util.List<BangfuziyuanView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
    
    java.util.List<com.entity.vo.BangfuziyuanVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
    
    com.entity.vo.BangfuziyuanVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
}
