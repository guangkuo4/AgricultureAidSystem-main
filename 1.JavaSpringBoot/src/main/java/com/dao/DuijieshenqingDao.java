package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.DuijieshenqingEntity;
import com.entity.view.DuijieshenqingView;
import com.entity.vo.DuijieshenqingVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 对接申请
 */
public interface DuijieshenqingDao extends BaseMapper<DuijieshenqingEntity> {

    List<DuijieshenqingVO> selectListVO(@Param("ew") Wrapper<DuijieshenqingEntity> wrapper);

    DuijieshenqingVO selectVO(@Param("ew") Wrapper<DuijieshenqingEntity> wrapper);

    List<DuijieshenqingView> selectListView(Page<DuijieshenqingView> page, @Param("ew") Wrapper<DuijieshenqingEntity> wrapper);

    List<DuijieshenqingView> selectListView(@Param("ew") Wrapper<DuijieshenqingEntity> wrapper);

    DuijieshenqingView selectView(@Param("ew") Wrapper<DuijieshenqingEntity> wrapper);
}
