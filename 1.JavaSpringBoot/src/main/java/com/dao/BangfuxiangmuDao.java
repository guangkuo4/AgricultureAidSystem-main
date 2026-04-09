package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfuxiangmuEntity;
import com.entity.view.BangfuxiangmuView;
import com.entity.vo.BangfuxiangmuVO;
import java.util.List;

/**
 * 帮扶项目
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfuxiangmuDao extends BaseMapper<BangfuxiangmuEntity> {
    List<BangfuxiangmuVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
    
    BangfuxiangmuVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
    
    List<BangfuxiangmuView> selectListView(Page<BangfuxiangmuView> page, com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
    
    List<BangfuxiangmuView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
    
    BangfuxiangmuView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuxiangmuEntity> wrapper);
}
