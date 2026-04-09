package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.ThumbsupEntity;
import com.entity.view.ThumbsupView;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 点赞
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface ThumbsupService extends IService<ThumbsupEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(Map<String, Object> params, com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    ThumbsupView selectView(com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    java.util.List<ThumbsupView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    java.util.List<com.entity.vo.ThumbsupVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    com.entity.vo.ThumbsupVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<ThumbsupEntity> wrapper);
    
    // 检查用户是否已点赞
    boolean hasLiked(Long userid, Long refid, String tablename);
    
    // 点赞/取消点赞
    boolean toggleLike(Long userid, Long refid, String tablename);
    
    // 获取点赞数量
    int getLikeCount(Long refid, String tablename);
}
