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
 * 消息通知 后端接口
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = messageService.queryPage(params);
        return R.ok().put("data", page);
    }

    /**
     * 前端列表：根据 userid 查询自己的消息
     * userid 来自 localStorage，为字符串，需转换为 Long 才能正确匹配数据库
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        try {
            EntityWrapper<MessageEntity> wrapper = new EntityWrapper<>();
            wrapper.orderBy("addtime", false);

            Object userid = params.get("userid");
            if (userid != null && !"".equals(userid.toString().trim())) {
                try {
                    Long useridLong = Long.valueOf(userid.toString().trim());
                    wrapper.eq("userid", useridLong);
                } catch (NumberFormatException e) {
                    // 无效 userid，忽略
                }
            }

            List<MessageEntity> list = messageService.selectList(wrapper);

            Map<String, Object> result = new java.util.HashMap<>();
            result.put("list", list);
            result.put("totalCount", list != null ? list.size() : 0);
            result.put("currPage", 1);
            result.put("pageSize", 100);

            return R.ok().put("page", result);
        } catch (Exception e) {
            return R.error("查询失败");
        }
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MessageEntity message = messageService.selectById(id);
        if (message != null && "未读".equals(message.getStatus())) {
            message.setStatus("已读");
            messageService.updateById(message);
        }
        return R.ok().put("data", message);
    }

    @RequestMapping("/save")
    public R save(@RequestBody MessageEntity message) {
        try {
            message.setAddtime(new Date());
            if (message.getStatus() == null) {
                message.setStatus("未读");
            }
            messageService.insert(message);
            return R.ok();
        } catch (Exception e) {
            return R.error("保存失败");
        }
    }

    @RequestMapping("/update")
    public R update(@RequestBody Map<String, Object> body) {
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

    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        messageService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 未读消息数量：同样需要将字符串 userid 转为 Long
     */
    @RequestMapping("/unreadCount")
    public R unreadCount(@RequestParam Map<String, Object> params) {
        Object userid = params.get("userid");
        int count = 0;
        if (userid != null && !"".equals(userid.toString().trim())) {
            try {
                Long useridLong = Long.valueOf(userid.toString().trim());
                EntityWrapper<MessageEntity> wrapper = new EntityWrapper<>();
                wrapper.eq("userid", useridLong);
                wrapper.eq("status", "未读");
                count = messageService.selectCount(wrapper);
            } catch (NumberFormatException e) {
                // 无效 userid，忽略
            }
        }
        return R.ok().put("count", count);
    }
}

