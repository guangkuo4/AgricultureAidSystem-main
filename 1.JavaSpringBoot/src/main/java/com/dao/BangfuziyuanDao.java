package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfuziyuanEntity;
import com.entity.view.BangfuziyuanView;
import com.entity.vo.BangfuziyuanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帮扶资源池
 */
public interface BangfuziyuanDao extends BaseMapper<BangfuziyuanEntity> {

    List<BangfuziyuanVO> selectListVO(@Param("ew") Wrapper<BangfuziyuanEntity> wrapper);

    BangfuziyuanVO selectVO(@Param("ew") Wrapper<BangfuziyuanEntity> wrapper);

    List<BangfuziyuanView> selectListView(Page<BangfuziyuanView> page, @Param("ew") Wrapper<BangfuziyuanEntity> wrapper);

    List<BangfuziyuanView> selectListView(@Param("ew") Wrapper<BangfuziyuanEntity> wrapper);

    BangfuziyuanView selectView(@Param("ew") Wrapper<BangfuziyuanEntity> wrapper);
}
