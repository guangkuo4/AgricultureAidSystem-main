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


import com.dao.BangfuxuqiuDao;
import com.entity.BangfuxuqiuEntity;
import com.service.BangfuxuqiuService;
import com.entity.vo.BangfuxuqiuVO;
import com.entity.view.BangfuxuqiuView;

@Service("bangfuxuqiuService")
public class BangfuxuqiuServiceImpl extends ServiceImpl<BangfuxuqiuDao, BangfuxuqiuEntity> implements BangfuxuqiuService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BangfuxuqiuEntity> page = this.selectPage(
                new Query<BangfuxuqiuEntity>(params).getPage(),
                new EntityWrapper<BangfuxuqiuEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<BangfuxuqiuEntity> wrapper) {
		  Page<BangfuxuqiuView> page =new Query<BangfuxuqiuView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	     	PageUtils pageUtil = new PageUtils(page);
	     	return pageUtil;
    }

    
    @Override
	public List<BangfuxuqiuVO> selectListVO(Wrapper<BangfuxuqiuEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public BangfuxuqiuVO selectVO(Wrapper<BangfuxuqiuEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<BangfuxuqiuView> selectListView(Wrapper<BangfuxuqiuEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public BangfuxuqiuView selectView(Wrapper<BangfuxuqiuEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
