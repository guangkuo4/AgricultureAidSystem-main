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


import com.dao.BangfuxiangmuDao;
import com.entity.BangfuxiangmuEntity;
import com.service.BangfuxiangmuService;
import com.entity.vo.BangfuxiangmuVO;
import com.entity.view.BangfuxiangmuView;

@Service("bangfuxiangmuService")
public class BangfuxiangmuServiceImpl extends ServiceImpl<BangfuxiangmuDao, BangfuxiangmuEntity> implements BangfuxiangmuService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BangfuxiangmuEntity> page = this.selectPage(
                new Query<BangfuxiangmuEntity>(params).getPage(),
                new EntityWrapper<BangfuxiangmuEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<BangfuxiangmuEntity> wrapper) {
		  Page<BangfuxiangmuView> page =new Query<BangfuxiangmuView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	     	PageUtils pageUtil = new PageUtils(page);
	     	return pageUtil;
    }

    
    @Override
	public List<BangfuxiangmuVO> selectListVO(Wrapper<BangfuxiangmuEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public BangfuxiangmuVO selectVO(Wrapper<BangfuxiangmuEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<BangfuxiangmuView> selectListView(Wrapper<BangfuxiangmuEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public BangfuxiangmuView selectView(Wrapper<BangfuxiangmuEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
