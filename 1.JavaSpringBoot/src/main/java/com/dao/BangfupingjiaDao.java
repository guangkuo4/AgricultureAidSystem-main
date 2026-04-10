package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfupingjiaEntity;
import com.entity.view.BangfupingjiaView;
import com.entity.vo.BangfupingjiaVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帮扶评价
 */
public interface BangfupingjiaDao extends BaseMapper<BangfupingjiaEntity> {

    List<BangfupingjiaVO> selectListVO(@Param("ew") Wrapper<BangfupingjiaEntity> wrapper);

    BangfupingjiaVO selectVO(@Param("ew") Wrapper<BangfupingjiaEntity> wrapper);

    List<BangfupingjiaView> selectListView(Page<BangfupingjiaView> page, @Param("ew") Wrapper<BangfupingjiaEntity> wrapper);

    List<BangfupingjiaView> selectListView(@Param("ew") Wrapper<BangfupingjiaEntity> wrapper);

    BangfupingjiaView selectView(@Param("ew") Wrapper<BangfupingjiaEntity> wrapper);
}
