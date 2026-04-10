package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfuxuqiuEntity;
import com.entity.view.BangfuxuqiuView;
import com.entity.vo.BangfuxuqiuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帮扶需求
 */
public interface BangfuxuqiuDao extends BaseMapper<BangfuxuqiuEntity> {

    List<BangfuxuqiuVO> selectListVO(@Param("ew") Wrapper<BangfuxuqiuEntity> wrapper);

    BangfuxuqiuVO selectVO(@Param("ew") Wrapper<BangfuxuqiuEntity> wrapper);

    List<BangfuxuqiuView> selectListView(Page<BangfuxuqiuView> page, @Param("ew") Wrapper<BangfuxuqiuEntity> wrapper);

    List<BangfuxuqiuView> selectListView(@Param("ew") Wrapper<BangfuxuqiuEntity> wrapper);

    BangfuxuqiuView selectView(@Param("ew") Wrapper<BangfuxuqiuEntity> wrapper);
}
