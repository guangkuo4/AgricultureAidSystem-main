package com.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息通知
 *
 * @author
 * @email
 * @date 2026-04-11 15:00:00
 */
public class MessageVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 创建时间
	 */
	private Date addtime;
	/**
	 * 用户id
	 */
	private Long userid;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 类型（系统通知/审核通知/对接通知）
	 */
	private String type;
	/**
	 * 状态（未读/已读）
	 */
	private String status;
	/**
	 * 关联id（如审核记录id、对接记录id）
	 */
	private Long relatedId;

}
