package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.NonghuEntity;
import com.entity.QiyeruzhuEntity;
import com.entity.view.QiyeruzhuView;
import com.service.NonghuService;
import com.service.QiyeruzhuService;
import com.utils.MPUtil;
import com.utils.PageUtils;
import com.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
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
     * 前端列表（用于“我的申请”）
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
        if (StringUtils.isBlank(qiyeruzhu.getQiyemingcheng())
                || StringUtils.isBlank(qiyeruzhu.getLianxiren())
                || StringUtils.isBlank(qiyeruzhu.getLianxidianhua())
                || StringUtils.isBlank(qiyeruzhu.getShenqingzhanghao())
                || StringUtils.isBlank(qiyeruzhu.getShenqingmima())) {
            return R.error("请填写完整信息");
        }

        // 账号不可与现有农商重复
        if (nonghuService.selectCount(new EntityWrapper<NonghuEntity>().eq("nonghuzhanghao", qiyeruzhu.getShenqingzhanghao())) > 0) {
            return R.error("该账号已存在，请更换");
        }
        // 同一账号不可重复提交
        if (qiyeruzhuService.selectCount(new EntityWrapper<QiyeruzhuEntity>().eq("shenqingzhanghao", qiyeruzhu.getShenqingzhanghao())) > 0) {
            return R.error("该账号已提交过入驻申请");
        }

        qiyeruzhu.setId(new Date().getTime());
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
        qiyeruzhu.setId(new Date().getTime());
        if (StringUtils.isBlank(qiyeruzhu.getSfsh())) {
            qiyeruzhu.setSfsh("待审核");
        }
        qiyeruzhuService.insert(qiyeruzhu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
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
     * 审核：通过/驳回
     * - 通过：创建农商(nonghu)账号，账号=shenqingzhanghao 密码=shenqingmima
     * - 驳回：仅更新审核状态与回复
     *
     * 入参示例：{ "id": 1, "sfsh": "通过", "shhf": "审核意见" }
     */
    @RequestMapping("/audit")
    @Transactional
    public R audit(@RequestBody Map<String, Object> body) {
        if (body == null || body.get("id") == null || body.get("sfsh") == null) {
            return R.error("参数错误");
        }
        Long id = Long.valueOf(body.get("id").toString());
        String sfsh = body.get("sfsh").toString();
        String shhf = body.get("shhf") == null ? "" : body.get("shhf").toString();

        QiyeruzhuEntity entity = qiyeruzhuService.selectById(id);
        if (entity == null) {
            return R.error("记录不存在");
        }

        // 统一状态值：待审核/通过/驳回
        if (!"通过".equals(sfsh) && !"驳回".equals(sfsh) && !"待审核".equals(sfsh)) {
            return R.error("审核状态不合法");
        }

        entity.setSfsh(sfsh);
        entity.setShhf(shhf);
        entity.setShenhetime(new Date());
        qiyeruzhuService.updateById(entity);

        if ("通过".equals(sfsh)) {
            // 若已存在同名账号，避免重复创建
            if (nonghuService.selectCount(new EntityWrapper<NonghuEntity>().eq("nonghuzhanghao", entity.getShenqingzhanghao())) > 0) {
                return R.error("农商账号已存在，无法重复开通");
            }
            NonghuEntity nonghu = new NonghuEntity();
            nonghu.setId(new Date().getTime());
            nonghu.setNonghuzhanghao(entity.getShenqingzhanghao());
            nonghu.setMima(entity.getShenqingmima());
            // “农户姓名”这里用企业名更贴合展示
            nonghu.setNonghuxingming(entity.getQiyemingcheng());
            nonghu.setNonghudianhua(entity.getLianxidianhua());
            nonghu.setMoney(0.0);
            nonghuService.insert(nonghu);
        }

        return R.ok();
    }
}

