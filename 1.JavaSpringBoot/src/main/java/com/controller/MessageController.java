package com.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.MessageEntity;
import com.service.MessageService;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 消息通知
 * 后端接口
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = messageService.queryPage(params);
        return R.ok().put("data", page);
    }

    /**
     * 前端列表（简化版，直接查询所有）
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        System.out.println("收到消息列表请求: " + params);
        try {
            EntityWrapper<MessageEntity> wrapper = new EntityWrapper<>();
            wrapper.orderBy("addtime", false); // 按时间倒序排列
            
            Object userid = params.get("userid");
            if (userid != null && !"".equals(userid.toString())) {
                wrapper.eq("userid", userid);
                System.out.println("过滤 userid: " + userid);
            }
            
            List<MessageEntity> list = messageService.selectList(wrapper);
            System.out.println("查询到消息数量: " + (list != null ? list.size() : 0));
            
            // 简单包装一下返回结果
            Map<String, Object> result = new java.util.HashMap<>();
            result.put("list", list);
            result.put("totalCount", list != null ? list.size() : 0);
            result.put("currPage", 1);
            result.put("pageSize", 100);
            
            return R.ok().put("page", result);
        } catch (Exception e) {
            System.out.println("查询消息列表失败: " + e.getMessage());
            e.printStackTrace();
            return R.error("查询失败");
        }
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MessageEntity message = messageService.selectById(id);
        // 标记为已读
        if (message != null && "未读".equals(message.getStatus())) {
            message.setStatus("已读");
            messageService.updateById(message);
        }
        return R.ok().put("data", message);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MessageEntity message) {
        System.out.println("保存消息: " + message);
        try {
            message.setAddtime(new Date());
            if (message.getStatus() == null) {
                message.setStatus("未读");
            }
            messageService.insert(message);
            System.out.println("消息保存成功，id: " + message.getId());
            return R.ok();
        } catch (Exception e) {
            System.out.println("保存消息失败: " + e.getMessage());
            e.printStackTrace();
            return R.error("保存失败");
        }
    }

    /**
     * 修改（支持单条和批量标记已读）
     */
    @RequestMapping("/update")
    public R update(@RequestBody Map<String, Object> body) {
        // 支持批量标记已读：如果前端传了 ids 数组，则批量更新
        if (body.containsKey("ids") && body.get("ids") != null) {
            Object idsObj = body.get("ids");
            String status = body.get("status") != null ? body.get("status").toString() : "已读";
            if (idsObj instanceof List) {
                List<?> idsList = (List<?>) idsObj;
                for (Object idObj : idsList) {
                    Long mid = Long.valueOf(idObj.toString());
                    MessageEntity m = new MessageEntity();
                    m.setId(mid);
                    m.setStatus(status);
                    messageService.updateById(m);
                }
            }
        } else if (body.containsKey("id") && body.get("id") != null) {
            // 单条更新
            MessageEntity m = new MessageEntity();
            m.setId(Long.valueOf(body.get("id").toString()));
            if (body.containsKey("status")) {
                m.setStatus(body.get("status").toString());
            }
            if (body.containsKey("title")) {
                m.setTitle(body.get("title").toString());
            }
            if (body.containsKey("content")) {
                m.setContent(body.get("content").toString());
            }
            messageService.updateById(m);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        messageService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 批量标记已读
     */
    @RequestMapping("/markAllRead")
    public R markAllRead(@RequestBody Map<String, Object> params) {
        Object userid = params.get("userid");
        if (userid != null) {
            EntityWrapper<MessageEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("userid", userid);
            wrapper.eq("status", "未读");
            List<MessageEntity> list = messageService.selectList(wrapper);
            for (MessageEntity message : list) {
                message.setStatus("已读");
                messageService.updateById(message);
            }
        }
        return R.ok();
    }

    /**
     * 未读数量
     */
    @RequestMapping("/unreadCount")
    public R unreadCount(@RequestParam Map<String, Object> params) {
        Object userid = params.get("userid");
        int count = 0;
        if (userid != null) {
            EntityWrapper<MessageEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("userid", userid);
            wrapper.eq("status", "未读");
            count = messageService.selectCount(wrapper);
        }
        return R.ok().put("count", count);
    }
}
