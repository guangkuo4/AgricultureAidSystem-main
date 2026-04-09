package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfuxuqiuEntity;
import com.entity.view.BangfuxuqiuView;
import com.entity.vo.BangfuxuqiuVO;
import java.util.List;

/**
 * 帮扶需求
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfuxuqiuDao extends BaseMapper<BangfuxuqiuEntity> {
    List<BangfuxuqiuVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
    
    BangfuxuqiuVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
    
    List<BangfuxuqiuView> selectListView(Page<BangfuxuqiuView> page, com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
    
    List<BangfuxuqiuView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
    
    BangfuxuqiuView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxuqiuEntity> wrapper);
}
