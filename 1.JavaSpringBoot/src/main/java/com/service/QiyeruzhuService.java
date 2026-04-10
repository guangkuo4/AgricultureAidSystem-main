package com.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.entity.QiyeruzhuEntity;
import com.entity.view.QiyeruzhuView;
import com.entity.vo.QiyeruzhuVO;
import com.utils.PageUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 企业入驻申请
 */
public interface QiyeruzhuService extends IService<QiyeruzhuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<QiyeruzhuVO> selectListVO(Wrapper<QiyeruzhuEntity> wrapper);

    QiyeruzhuVO selectVO(@Param("ew") Wrapper<QiyeruzhuEntity> wrapper);

    List<QiyeruzhuView> selectListView(Wrapper<QiyeruzhuEntity> wrapper);

    QiyeruzhuView selectView(@Param("ew") Wrapper<QiyeruzhuEntity> wrapper);

    PageUtils queryPage(Map<String, Object> params, Wrapper<QiyeruzhuEntity> wrapper);

    /**
     * 审核入驻申请
     * @param params 包含 id, sfsh, shhf
     * @return 审核结果
     */
    com.utils.R audit(Map<String, Object> params);

    /**
     * 发送审核通知消息
     * @param userId 用户ID
     * @param shenqingzhanghao 申请账号
     * @param id 申请记录ID
     * @param sfsh 审核结果
     * @param shhf 审核备注
     */
    void sendAuditMessage(Long userId, String shenqingzhanghao, Long id, String sfsh, String shhf);
}

