package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.MessageDao;
import com.entity.MessageEntity;
import com.entity.view.MessageView;
import com.entity.vo.MessageVO;
import com.service.MessageService;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 消息通知
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<MessageEntity> wrapper = new EntityWrapper<>();
        if (params != null) {
            Object userid = params.get("userid");
            if (userid != null && !"".equals(userid.toString())) {
                wrapper.eq("userid", userid);
            }
            Object status = params.get("status");
            if (status != null && !"".equals(status.toString())) {
                wrapper.eq("status", status);
            }
            Object type = params.get("type");
            if (type != null && !"".equals(type.toString())) {
                wrapper.eq("type", type);
            }
        }
        Page<MessageEntity> page = this.selectPage(new Query<MessageEntity>(params).getPage(), wrapper);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Wrapper<MessageEntity> wrapper) {
        Page<MessageEntity> page = new Query<MessageEntity>(params).getPage();
        page.setRecords(this.selectList(wrapper));
        return new PageUtils(page);
    }

    @Override
    public List<MessageVO> selectListVO(Wrapper<MessageEntity> wrapper) {
        return baseMapper.selectListVO(wrapper);
    }

    @Override
    public MessageVO selectVO(Wrapper<MessageEntity> wrapper) {
        return baseMapper.selectVO(wrapper);
    }

    @Override
    public List<MessageView> selectListView(Wrapper<MessageEntity> wrapper) {
        return baseMapper.selectListView(wrapper);
    }

    @Override
    public MessageView selectView(Wrapper<MessageEntity> wrapper) {
        return baseMapper.selectView(wrapper);
    }
}
