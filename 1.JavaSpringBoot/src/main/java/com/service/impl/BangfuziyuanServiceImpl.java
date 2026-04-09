package com.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.utils.PageUtils;
import com.utils.Query;


import com.dao.BangfuziyuanDao;
import com.entity.BangfuziyuanEntity;
import com.service.BangfuziyuanService;
import com.entity.vo.BangfuziyuanVO;
import com.entity.view.BangfuziyuanView;

@Service("bangfuziyuanService")
public class BangfuziyuanServiceImpl extends ServiceImpl<BangfuziyuanDao, BangfuziyuanEntity> implements BangfuziyuanService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BangfuziyuanEntity> page = this.selectPage(
                new Query<BangfuziyuanEntity>(params).getPage(),
                new EntityWrapper<BangfuziyuanEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<BangfuziyuanEntity> wrapper) {
		  Page<BangfuziyuanView> page =new Query<BangfuziyuanView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	     	PageUtils pageUtil = new PageUtils(page);
	     	return pageUtil;
    }

    
    @Override
	public List<BangfuziyuanVO> selectListVO(Wrapper<BangfuziyuanEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public BangfuziyuanVO selectVO(Wrapper<BangfuziyuanEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<BangfuziyuanView> selectListView(Wrapper<BangfuziyuanEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public BangfuziyuanView selectView(Wrapper<BangfuziyuanEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
