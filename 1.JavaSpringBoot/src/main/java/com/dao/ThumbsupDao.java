package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.ThumbsupEntity;
import com.entity.view.ThumbsupView;
import com.entity.vo.ThumbsupVO;
import java.util.List;

/**
 * 点赞
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface ThumbsupDao extends BaseMapper<ThumbsupEntity> {
    List<ThumbsupVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    ThumbsupVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    List<ThumbsupView> selectListView(Page<ThumbsupView> page, com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    List<ThumbsupView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    ThumbsupView selectView(com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    // 检查用户是否已点赞
    Integer countByUserAndRef(Long userid, Long refid, String tablename);
    
    // 获取点赞数量
    Integer countByRef(Long refid, String tablename);
}
