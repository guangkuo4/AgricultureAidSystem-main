package com.service;

import com.baomidou.mybatisplus.service.IService;
import com.entity.BangfupingjiaEntity;
import com.entity.view.BangfupingjiaView;
import com.utils.PageUtils;
import java.util.Map;

/**
 * 帮扶评价
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfupingjiaService extends IService<BangfupingjiaEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryPage(Map<String, Object> params, com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
    
    BangfupingjiaView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
    
    java.util.List<BangfupingjiaView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
    
    java.util.List<com.entity.vo.BangfupingjiaVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
    
    com.entity.vo.BangfupingjiaVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
}
