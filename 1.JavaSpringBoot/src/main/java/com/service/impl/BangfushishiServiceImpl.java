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


import com.dao.BangfushishiDao;
import com.entity.BangfushishiEntity;
import com.service.BangfushishiService;
import com.entity.vo.BangfushishiVO;
import com.entity.view.BangfushishiView;

@Service("bangfushishiService")
public class BangfushishiServiceImpl extends ServiceImpl<BangfushishiDao, BangfushishiEntity> implements BangfushishiService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BangfushishiEntity> page = this.selectPage(
                new Query<BangfushishiEntity>(params).getPage(),
                new EntityWrapper<BangfushishiEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<BangfushishiEntity> wrapper) {
		  Page<BangfushishiView> page =new Query<BangfushishiView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	     	PageUtils pageUtil = new PageUtils(page);
	     	return pageUtil;
    }

    
    @Override
	public List<BangfushishiVO> selectListVO(Wrapper<BangfushishiEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public BangfushishiVO selectVO(Wrapper<BangfushishiEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<BangfushishiView> selectListView(Wrapper<BangfushishiEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public BangfushishiView selectView(Wrapper<BangfushishiEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
