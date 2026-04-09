package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.entity.QiyeruzhuEntity;
import com.entity.view.QiyeruzhuView;
import com.entity.vo.QiyeruzhuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 企业入驻申请
 */
public interface QiyeruzhuDao extends BaseMapper<QiyeruzhuEntity> {

    List<QiyeruzhuVO> selectListVO(@Param("ew") Wrapper<QiyeruzhuEntity> wrapper);

    QiyeruzhuVO selectVO(@Param("ew") Wrapper<QiyeruzhuEntity> wrapper);

    List<QiyeruzhuView> selectListView(@Param("ew") Wrapper<QiyeruzhuEntity> wrapper);

    List<QiyeruzhuView> selectListView(Pagination page, @Param("ew") Wrapper<QiyeruzhuEntity> wrapper);

    QiyeruzhuView selectView(@Param("ew") Wrapper<QiyeruzhuEntity> wrapper);
}

