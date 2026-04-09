package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.QiyeruzhuDao;
import com.entity.QiyeruzhuEntity;
import com.entity.view.QiyeruzhuView;
import com.entity.vo.QiyeruzhuVO;
import com.service.QiyeruzhuService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("qiyeruzhuService")
public class QiyeruzhuServiceImpl extends ServiceImpl<QiyeruzhuDao, QiyeruzhuEntity> implements QiyeruzhuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<QiyeruzhuEntity> page = this.selectPage(
                new Query<QiyeruzhuEntity>(params).getPage(),
                new EntityWrapper<QiyeruzhuEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<QiyeruzhuEntity> wrapper) {
        Page<QiyeruzhuView> page = new Query<QiyeruzhuView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page, wrapper));
        return new PageUtils(page);
    }

    @Override
    public List<QiyeruzhuVO> selectListVO(Wrapper<QiyeruzhuEntity> wrapper) {
        return baseMapper.selectListVO(wrapper);
    }

    @Override
    public QiyeruzhuVO selectVO(Wrapper<QiyeruzhuEntity> wrapper) {
        return baseMapper.selectVO(wrapper);
    }

    @Override
    public List<QiyeruzhuView> selectListView(Wrapper<QiyeruzhuEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public QiyeruzhuView selectView(Wrapper<QiyeruzhuEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }
}

