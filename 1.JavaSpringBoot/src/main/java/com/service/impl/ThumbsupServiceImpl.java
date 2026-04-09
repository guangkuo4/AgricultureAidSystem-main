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


import com.dao.ThumbsupDao;
import com.entity.ThumbsupEntity;
import com.service.ThumbsupService;
import com.entity.vo.ThumbsupVO;
import com.entity.view.ThumbsupView;

@Service("thumbsupService")
public class ThumbsupServiceImpl extends ServiceImpl<ThumbsupDao, ThumbsupEntity> implements ThumbsupService {
	
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ThumbsupEntity> page = this.selectPage(
                new Query<ThumbsupEntity>(params).getPage(),
                new EntityWrapper<ThumbsupEntity>()
        );
        return new PageUtils(page);
    }
    
    @Override
	public PageUtils queryPage(Map<String, Object> params, Wrapper<ThumbsupEntity> wrapper) {
		  Page<ThumbsupView> page =new Query<ThumbsupView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,wrapper));
      	PageUtils pageUtil = new PageUtils(page);
      	return pageUtil;
    }

    
      @Override
	public List<ThumbsupVO> selectListVO(Wrapper<ThumbsupEntity> wrapper) {
		return baseMapper.selectListVO(wrapper);
	}
	
	@Override
	public ThumbsupVO selectVO(Wrapper<ThumbsupEntity> wrapper) {
		return baseMapper.selectVO(wrapper);
	}
	
	@Override
	public List<ThumbsupView> selectListView(Wrapper<ThumbsupEntity> wrapper) {
		return baseMapper.selectListView(wrapper);
	}

	@Override
	public ThumbsupView selectView(Wrapper<ThumbsupEntity> wrapper) {
		return baseMapper.selectView(wrapper);
	}

	@Override
	public boolean hasLiked(Long userid, Long refid, String tablename) {
		Integer count = baseMapper.countByUserAndRef(userid, refid, tablename);
		return count != null && count > 0;
	}

	@Override
	public boolean toggleLike(Long userid, Long refid, String tablename) {
		// 检查是否已点赞
		boolean hasLiked = this.hasLiked(userid, refid, tablename);
		
		if (hasLiked) {
			// 已点赞，取消点赞
			EntityWrapper<ThumbsupEntity> wrapper = new EntityWrapper<>();
			wrapper.eq("userid", userid)
			       .eq("refid", refid)
			       .eq("tablename", tablename);
			this.delete(wrapper);
			return false;
		} else {
			// 未点赞，添加点赞
			ThumbsupEntity thumbsup = new ThumbsupEntity();
			thumbsup.setUserid(userid);
			thumbsup.setRefid(refid);
			thumbsup.setTablename(tablename);
			this.insert(thumbsup);
			return true;
		}
	}

	@Override
	public int getLikeCount(Long refid, String tablename) {
		Integer count = baseMapper.countByRef(refid, tablename);
		return count != null ? count : 0;
	}

}
