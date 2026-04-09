package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfushishiEntity;
import com.entity.view.BangfushishiView;
import com.entity.vo.BangfushishiVO;
import java.util.List;

/**
 * 帮扶实施
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfushishiDao extends BaseMapper<BangfushishiEntity> {
    List<BangfushishiVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
    
    BangfushishiVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
    
    List<BangfushishiView> selectListView(Page<BangfushishiView> page, com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
    
    List<BangfushishiView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
    
    BangfushishiView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfushishiEntity> wrapper);
}
