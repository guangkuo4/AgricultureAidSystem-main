package com.entity.view;

import com.baomidou.mybatisplus.annotations.TableName;
import com.entity.QiyeruzhuEntity;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

/**
 * 企业入驻申请
 * 后端返回视图实体辅助类
 */
@TableName("qiyeruzhu")
public class QiyeruzhuView extends QiyeruzhuEntity<Object> implements Serializable {
    private static final long serialVersionUID = 1L;

    public QiyeruzhuView() {
    }

    public QiyeruzhuView(QiyeruzhuEntity<?> entity) {
        try {
            BeanUtils.copyProperties(this, entity);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

