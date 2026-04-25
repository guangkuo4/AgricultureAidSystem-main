package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.NonghuEntity;
import com.entity.QiyeruzhuEntity;
import com.entity.YonghuEntity;
import com.entity.view.QiyeruzhuView;
import com.service.NonghuService;
import com.service.QiyeruzhuService;
import com.service.YonghuService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 企业入驻申请
 * 后端接口
 */
@RestController
@RequestMapping("/qiyeruzhu")
public class QiyeruzhuController {
    @Autowired
    private QiyeruzhuService qiyeruzhuService;
    @Autowired
    private NonghuService nonghuService;
    @Autowired
    private YonghuService yonghuService;

    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, QiyeruzhuEntity qiyeruzhu, HttpServletRequest request) {
        EntityWrapper<QiyeruzhuEntity> ew = new EntityWrapper<QiyeruzhuEntity>();
        PageUtils page = qiyeruzhuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, qiyeruzhu), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 前端列表（用于"我的申请"）
     */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, QiyeruzhuEntity qiyeruzhu, HttpServletRequest request) {
        EntityWrapper<QiyeruzhuEntity> ew = new EntityWrapper<QiyeruzhuEntity>();
        PageUtils page = qiyeruzhuService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, qiyeruzhu), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(QiyeruzhuEntity qiyeruzhu) {
        EntityWrapper<QiyeruzhuEntity> ew = new EntityWrapper<QiyeruzhuEntity>();
        ew.allEq(MPUtil.allEQMapPre(qiyeruzhu, "qiyeruzhu"));
        QiyeruzhuView view = qiyeruzhuService.selectView(ew);
        return R.ok("查询企业入驻申请成功").put("data", view);
    }

    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        QiyeruzhuEntity qiyeruzhu = qiyeruzhuService.selectById(id);
        return R.ok().put("data", qiyeruzhu);
    }

    /**
     * 前端详情
     */
    @IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        QiyeruzhuEntity qiyeruzhu = qiyeruzhuService.selectById(id);
        return R.ok().put("data", qiyeruzhu);
    }

    /**
     * 提交入驻申请（前台统一入口）
     */
    @IgnoreAuth
    @RequestMapping("/apply")
    public R apply(@RequestBody QiyeruzhuEntity qiyeruzhu) {
        if (qiyeruzhu == null) {
            return R.error("参数错误");
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(qiyeruzhu.getQiyemingcheng())
                || org.apache.commons.lang3.StringUtils.isBlank(qiyeruzhu.getLianxiren())
                || org.apache.commons.lang3.StringUtils.isBlank(qiyeruzhu.getLianxidianhua())
                || org.apache.commons.lang3.StringUtils.isBlank(qiyeruzhu.getShenqingzhanghao())
                || org.apache.commons.lang3.StringUtils.isBlank(qiyeruzhu.getShenqingmima())) {
            return R.error("请填写完整信息");
        }

        // 账号不可与现有农商重复
        if (nonghuService.selectCount(new EntityWrapper<NonghuEntity>().eq("nonghuzhanghao", qiyeruzhu.getShenqingzhanghao())) > 0
                || yonghuService.selectCount(new EntityWrapper<YonghuEntity>().eq("yonghuzhanghao", qiyeruzhu.getShenqingzhanghao())) > 0) {
            return R.error("该账号已存在，请更换");
        }
        // 同一账号不可重复提交
        if (qiyeruzhuService.selectCount(new EntityWrapper<QiyeruzhuEntity>().eq("shenqingzhanghao", qiyeruzhu.getShenqingzhanghao())) > 0) {
            return R.error("该账号已提交过入驻申请");
        }

        if (qiyeruzhu.getUserid() == null) {
            return R.error("无法获取当前用户信息，请重新登录后提交");
        }

        qiyeruzhu.setId(new java.util.Date().getTime());
        qiyeruzhu.setSfsh("待审核");
        qiyeruzhu.setShhf("");
        qiyeruzhuService.insert(qiyeruzhu);
        return R.ok("提交成功，等待管理员审核");
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody QiyeruzhuEntity qiyeruzhu, HttpServletRequest request) {
        if (qiyeruzhuService.selectCount(new EntityWrapper<QiyeruzhuEntity>().eq("shenqingzhanghao", qiyeruzhu.getShenqingzhanghao())) > 0) {
            return R.error("申请账号已存在");
        }
        qiyeruzhu.setId(new java.util.Date().getTime());
        if (org.apache.commons.lang3.StringUtils.isBlank(qiyeruzhu.getSfsh())) {
            qiyeruzhu.setSfsh("待审核");
        }
        qiyeruzhuService.insert(qiyeruzhu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody QiyeruzhuEntity qiyeruzhu, HttpServletRequest request) {
        qiyeruzhuService.updateById(qiyeruzhu);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        qiyeruzhuService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 审核入驻申请（事务在 Service 层处理）
     */
    @RequestMapping("/audit")
    public R audit(@RequestBody Map<String, Object> params) {
        R result = qiyeruzhuService.audit(params);
        if (result.get("code") != null && result.get("code").equals(0)) {
            // 直接查申请记录，确保 userId 取到
            Object idObj = params.get("id");
            if (idObj != null) {
                QiyeruzhuEntity entity = qiyeruzhuService.selectById(Long.valueOf(idObj.toString()));
                if (entity != null) {
                    Long targetUserId = entity.getUserid();
                    String shenqingzhanghao = entity.getShenqingzhanghao();
                    String sfsh = params.get("sfsh") != null ? params.get("sfsh").toString() : "";
                    String shhf = params.get("shhf") != null ? params.get("shhf").toString() : "";
                    Long relatedId = Long.valueOf(idObj.toString());
                    qiyeruzhuService.sendAuditMessage(targetUserId, shenqingzhanghao, relatedId, sfsh, shhf);
                }
            }
        }
        return result;
    }
}
