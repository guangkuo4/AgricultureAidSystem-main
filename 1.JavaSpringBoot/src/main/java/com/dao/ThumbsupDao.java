package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.ThumbsupEntity;
import com.entity.view.ThumbsupView;
import com.entity.vo.ThumbsupVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 点赞
 */
public interface ThumbsupDao extends BaseMapper<ThumbsupEntity> {

    List<ThumbsupVO> selectListVO(@Param("ew") Wrapper<ThumbsupEntity> wrapper);

    ThumbsupVO selectVO(@Param("ew") Wrapper<ThumbsupEntity> wrapper);

    List<ThumbsupView> selectListView(Page<ThumbsupView> page, @Param("ew") Wrapper<ThumbsupEntity> wrapper);

    List<ThumbsupView> selectListView(@Param("ew") Wrapper<ThumbsupEntity> wrapper);

    ThumbsupView selectView(@Param("ew") Wrapper<ThumbsupEntity> wrapper);

    // 检查用户是否已点赞
    Integer countByUserAndRef(Long userid, Long refid, String tablename);

    // 获取点赞数量
    Integer countByRef(Long refid, String tablename);
}
