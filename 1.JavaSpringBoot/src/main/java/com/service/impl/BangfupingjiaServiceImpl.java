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


import com.dao.BangfupingjiaDao;
import com.entity.BangfupingjiaEntity;
import com.service.BangfupingjiaService;
import com.entity.vo.BangfupingjiaVO;
import com.entity.view.BangfupingjiaView;

@Service("bangfupingjiaService")
public class BangfupingjiaServiceImpl extends ServiceImpl<BangfupingjiaDao, BangfupingjiaEntity> implements BangfupingjiaService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BangfupingjiaEntity> page = this.selectPage(
                new Query<BangfupingjiaEntity>(params).getPage(),
                new EntityWrapper<BangfupingjiaEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<BangfupingjiaEntity> wrapper) {
		  Page<BangfupingjiaView> page =new Query<BangfupingjiaView>(params).getPage();
	        page.setRecords(baseMapper.selectListView(page,wrapper));
	     	PageUtils pageUtil = new PageUtils(page);
	     	return pageUtil;
    }

    
    @Override
	public List<BangfupingjiaVO> selectListVO(Wrapper<BangfupingjiaEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public BangfupingjiaVO selectVO(Wrapper<BangfupingjiaEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<BangfupingjiaView> selectListView(Wrapper<BangfupingjiaEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public BangfupingjiaView selectView(Wrapper<BangfupingjiaEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}


}
