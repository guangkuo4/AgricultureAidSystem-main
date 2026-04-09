package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfupingjiaEntity;
import com.entity.view.BangfupingjiaView;
import com.entity.vo.BangfupingjiaVO;
import java.util.List;

/**
 * 帮扶评价
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfupingjiaDao extends BaseMapper<BangfupingjiaEntity> {
    List<BangfupingjiaVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
    
    BangfupingjiaVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
    
    List<BangfupingjiaView> selectListView(Page<BangfupingjiaView> page, com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
    
    List<BangfupingjiaView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
    
    BangfupingjiaView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfupingjiaEntity> wrapper);
}
