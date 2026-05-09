ALTER TABLE duijieshenqing ADD COLUMN jigoumingcheng VARCHAR(255) COMMENT '机构/个人名称';
ALTER TABLE duijieshenqing ADD COLUMN lianxidianhua VARCHAR(50) COMMENT '联系电话';
ALTER TABLE duijieshenqing ADD COLUMN fujian VARCHAR(500) COMMENT '附件';
ALTER TABLE duijieshenqing ADD COLUMN fuwuchengnuo TINYINT(1) DEFAULT 0 COMMENT '服务承诺';