CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` datetime DEFAULT NULL COMMENT '创建时间',
  `userid` bigint DEFAULT NULL COMMENT '用户id',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `status` varchar(50) DEFAULT NULL COMMENT '状态',
  `related_id` bigint DEFAULT NULL COMMENT '关联id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息通知';