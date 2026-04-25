package com.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.QiyeruzhuDao;
import com.entity.NonghuEntity;
import com.entity.QiyeruzhuEntity;
import com.entity.YonghuEntity;
import com.entity.view.QiyeruzhuView;
import com.entity.vo.QiyeruzhuVO;
import com.service.MessageService;
import com.service.NonghuService;
import com.service.QiyeruzhuService;
import com.service.YonghuService;
import com.utils.PageUtils;
import com.utils.Query;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("qiyeruzhuService")
public class QiyeruzhuServiceImpl extends ServiceImpl<QiyeruzhuDao, QiyeruzhuEntity> implements QiyeruzhuService {

    @Autowired
    private NonghuService nonghuService;

    @Autowired
    private YonghuService yonghuService;

    @Autowired
    private MessageService messageService;

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

    /**
     * 审核入驻申请（事务入口在 Service 层，避免与 Controller 嵌套事务冲突）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R audit(Map<String, Object> params) {
        System.out.println("========== 审核流程开始 ==========");
        System.out.println("收到审核请求: " + params);
        if (params == null || params.get("id") == null || params.get("sfsh") == null) {
            return R.error("参数错误");
        }
        Long id = Long.valueOf(params.get("id").toString());
        String sfsh = params.get("sfsh").toString();
        String shhf = params.get("shhf") == null ? "" : params.get("shhf").toString();

        QiyeruzhuEntity entity = selectById(id);
        if (entity == null) {
            return R.error("记录不存在");
        }
        System.out.println("查询到的申请记录: " + entity);
        System.out.println("申请记录的 userid: " + entity.getUserid());

        // 统一状态值
        if (!"通过".equals(sfsh) && !"驳回".equals(sfsh) && !"待审核".equals(sfsh)) {
            return R.error("审核状态不合法");
        }

        entity.setSfsh(sfsh);
        entity.setShhf(shhf);
        entity.setShenhetime(new Date());
        updateById(entity);

        if ("通过".equals(sfsh)) {
            if (nonghuService.selectCount(new EntityWrapper<NonghuEntity>().eq("nonghuzhanghao", entity.getShenqingzhanghao())) > 0) {
                return R.error("农商账号已存在，无法重复开通");
            }
            NonghuEntity nonghu = new NonghuEntity();
            nonghu.setId(new Date().getTime());
            nonghu.setNonghuzhanghao(entity.getShenqingzhanghao());
            nonghu.setMima(entity.getShenqingmima());
            nonghu.setNonghuxingming(entity.getQiyemingcheng());
            nonghu.setNonghudianhua(entity.getLianxidianhua());
            nonghu.setMoney(0.0);
            nonghuService.insert(nonghu);
        }

        // 保存审核成功需要的信息，用于后续发送消息
        Map<String, Object> auditData = new java.util.HashMap<>();
        auditData.put("userId", entity.getUserid());
        auditData.put("shenqingzhanghao", entity.getShenqingzhanghao());
        auditData.put("id", id);
        auditData.put("sfsh", sfsh);
        auditData.put("shhf", shhf);

        return R.ok("审核成功").put("data", auditData);
    }

    /**
     * 发送审核通知消息（独立事务，不影响审核流程）
     */
    public void sendAuditMessage(Long userId, String shenqingzhanghao, Long id, String sfsh, String shhf) {
        try {
            com.entity.MessageEntity message = new com.entity.MessageEntity();
            message.setId(new Date().getTime());
            message.setAddtime(new Date());

            Long msgUserid = userId;
            // 优先用传入的userId；为空时通过申请账号反查用户ID
            if (msgUserid == null && shenqingzhanghao != null) {
                YonghuEntity yonghu = yonghuService.selectOne(
                        new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", shenqingzhanghao));
                if (yonghu != null) {
                    msgUserid = yonghu.getId();
                }
            }
            if (msgUserid == null) {
                System.out.println("无法确定消息接收者，跳过消息发送");
                return;
            }

            message.setUserid(msgUserid);
            message.setTitle("入驻申请审核结果");
            StringBuilder content = new StringBuilder();
            content.append("您的入驻申请已").append(sfsh);
            if (shhf != null && !shhf.trim().isEmpty()) {
                content.append("，审核备注：").append(shhf);
            }
            message.setContent(content.toString());
            message.setType("审核通知");
            message.setStatus("未读");
            message.setRelatedId(id);

            messageService.insert(message);
            System.out.println("消息通知发送成功，userId: " + msgUserid);
        } catch (Exception e) {
            System.out.println("发送消息通知失败（不影响审核）: " + e.getMessage());
        }
    }
}
