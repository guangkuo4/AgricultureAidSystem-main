package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfushishiEntity;
import com.entity.view.BangfushishiView;
import com.entity.vo.BangfushishiVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帮扶实施
 */
public interface BangfushishiDao extends BaseMapper<BangfushishiEntity> {

    List<BangfushishiVO> selectListVO(@Param("ew") Wrapper<BangfushishiEntity> wrapper);

    BangfushishiVO selectVO(@Param("ew") Wrapper<BangfushishiEntity> wrapper);

    List<BangfushishiView> selectListView(Page<BangfushishiView> page, @Param("ew") Wrapper<BangfushishiEntity> wrapper);

    List<BangfushishiView> selectListView(@Param("ew") Wrapper<BangfushishiEntity> wrapper);

    BangfushishiView selectView(@Param("ew") Wrapper<BangfushishiEntity> wrapper);
}
