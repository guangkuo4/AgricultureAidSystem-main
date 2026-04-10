package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.MessageEntity;
import com.entity.view.MessageView;
import com.entity.vo.MessageVO;
import com.utils.PageUtils;
import java.util.List;
import java.util.Map;

/**
 * 消息通知
 */
public interface MessageService extends IService<MessageEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Wrapper<MessageEntity> wrapper);

    List<MessageVO> selectListVO(Wrapper<MessageEntity> wrapper);

    MessageVO selectVO(Wrapper<MessageEntity> wrapper);

    List<MessageView> selectListView(Wrapper<MessageEntity> wrapper);

    MessageView selectView(Wrapper<MessageEntity> wrapper);
}
