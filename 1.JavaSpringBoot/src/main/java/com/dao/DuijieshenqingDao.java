package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.DuijieshenqingEntity;
import com.entity.view.DuijieshenqingView;
import com.entity.vo.DuijieshenqingVO;
import java.util.List;

/**
 * 对接申请
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface DuijieshenqingDao extends BaseMapper<DuijieshenqingEntity> {
    List<DuijieshenqingVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
    
    DuijieshenqingVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
    
    List<DuijieshenqingView> selectListView(Page<DuijieshenqingView> page, com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
    
    List<DuijieshenqingView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
    
    DuijieshenqingView selectView(com.baomidou.mybatisplus.mapper.Wrapper<DuijieshenqingEntity> wrapper);
}
