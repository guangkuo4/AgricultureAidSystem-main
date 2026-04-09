package com.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.entity.BangfuziyuanEntity;
import com.entity.view.BangfuziyuanView;
import com.entity.vo.BangfuziyuanVO;
import java.util.List;

/**
 * 帮扶资源池
 * 
 * @author 
 * @email 
 * @date 2024-04-08 17:32:25
 */
public interface BangfuziyuanDao extends BaseMapper<BangfuziyuanEntity> {
    List<BangfuziyuanVO> selectListVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
    
    BangfuziyuanVO selectVO(com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
    
    List<BangfuziyuanView> selectListView(Page<BangfuziyuanView> page, com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
    
    List<BangfuziyuanView> selectListView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
    
    BangfuziyuanView selectView(com.baomidou.mybatisplus.mapper.Wrapper<BangfuziyuanEntity> wrapper);
}
