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


import com.dao.DuijieshenqingDao;
import com.entity.DuijieshenqingEntity;
import com.service.DuijieshenqingService;
import com.entity.vo.DuijieshenqingVO;
import com.entity.view.DuijieshenqingView;

@Service("duijieshenqingService")
public class DuijieshenqingServiceImpl extends ServiceImpl<DuijieshenqingDao, DuijieshenqingEntity> implements DuijieshenqingService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DuijieshenqingEntity> page = this.selectPage(
                new Query<DuijieshenqingEntity>(params).getPage(),
                new EntityWrapper<DuijieshenqingEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<DuijieshenqingEntity> wrapper) {
		  Page<DuijieshenqingView> page =new Query<DuijieshenqingView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	     	PageUtils pageUtil = new PageUtils(page);
	     	return pageUtil;
    }

    
    @Override
	public List<DuijieshenqingVO> selectListVO(Wrapper<DuijieshenqingEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public DuijieshenqingVO selectVO(Wrapper<DuijieshenqingEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<DuijieshenqingView> selectListView(Wrapper<DuijieshenqingEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public DuijieshenqingView selectView(Wrapper<DuijieshenqingEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
