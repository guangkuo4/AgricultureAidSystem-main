package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfuxiangmuEntity;
import com.entity.view.BangfuxiangmuView;
import com.entity.vo.BangfuxiangmuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帮扶项目
 */
public interface BangfuxiangmuDao extends BaseMapper<BangfuxiangmuEntity> {

    List<BangfuxiangmuVO> selectListVO(@Param("ew") Wrapper<BangfuxiangmuEntity> wrapper);

    BangfuxiangmuVO selectVO(@Param("ew") Wrapper<BangfuxiangmuEntity> wrapper);

    List<BangfuxiangmuView> selectListView(Page<BangfuxiangmuView> page, @Param("ew") Wrapper<BangfuxiangmuEntity> wrapper);

    List<BangfuxiangmuView> selectListView(@Param("ew") Wrapper<BangfuxiangmuEntity> wrapper);

    BangfuxiangmuView selectView(@Param("ew") Wrapper<BangfuxiangmuEntity> wrapper);
}
