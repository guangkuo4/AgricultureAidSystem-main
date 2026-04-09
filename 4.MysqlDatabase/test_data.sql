-- 帮扶资源池测试数据
INSERT INTO `bangfuziyuan` (`ziyuanbianhao`, `bangfuleixing`, `bangfufangming`, `bangfufangleixing`, `shanchanglingyu`, `suozaidiqu`, `xiangyingshixiao`, `bangfuneirong`, `lianxiren`, `lianxidianhua`, `sfsh`) VALUES
(CONCAT('ZY', UNIX_TIMESTAMP(NOW())), '技术帮扶', '河南省农业科学院', '帮扶单位', '小麦种植技术、病虫害防治', '河南郑州', '48小时内', '提供小麦种植全程技术指导，包括播种、施肥、病虫害防治等环节的专业技术支持。拥有20名高级农艺师，可实地指导或远程咨询。', '张教授', '13800138001', '已通过'),
(CONCAT('ZY', UNIX_TIMESTAMP(NOW())+1), '资金帮扶', '中国扶贫基金会', '帮扶单位', '农业产业发展资金支持', '北京', '一周内', '为符合条件的农户提供产业发展启动资金，单笔最高50万元，无息贷款期限3年，用于购买农机设备、建设温室大棚等。', '李主任', '13900139002', '已通过'),
(CONCAT('ZY', UNIX_TIMESTAMP(NOW())+2), '产品采购', '绿色农产品有限公司', '农业企业', '有机蔬菜、水果采购', '山东寿光', '24小时内', '长期采购各类有机蔬菜、水果，价格高于市场价10%，现款结算。可提供种植技术指导，签订保底收购协议。', '王经理', '13700137003', '已通过'),
(CONCAT('ZY', UNIX_TIMESTAMP(NOW())+3), '农机帮扶', '现代农业机械合作社', '农业企业', '农机作业服务', '河北保定', '48小时内', '提供耕、种、收全程机械化服务，拥有拖拉机、收割机、播种机等设备50余台。价格优惠，贫困户享受额外20%折扣。', '赵社长', '13600136004', '已通过'),
(CONCAT('ZY', UNIX_TIMESTAMP(NOW())+4), '技术帮扶', '李农艺师工作室', '农技专家', '果树栽培与管理', '陕西西安', '24小时内', '专业从事苹果、猕猴桃等果树栽培管理20年，可提供修剪、嫁接、施肥、病虫害防治等全程技术指导。', '李农艺师', '13500135005', '已通过');

-- 帮扶需求测试数据
INSERT INTO `bangfuxuqiu` (`xuqiubianhao`, `xuqiuleixing`, `xuqiumiaoshu`, `xuqiujinji`, `qiwangshijian`, `jutidizhi`, `shenqingrenzhanghao`, `shenqingrenxingming`, `shenqingridi`, `sfsh`, `userid`) VALUES
(CONCAT('XQ', UNIX_TIMESTAMP(NOW())), '技术帮扶', '我家种植了10亩小麦，今年发现麦苗发黄，怀疑是病虫害，需要专业农艺师来现场指导如何防治。', '紧急', DATE_ADD(CURDATE(), INTERVAL 7 DAY), '河南省郑州市中牟县XX镇XX村', 'test001', '王农户', CURDATE(), '已通过', 1),
(CONCAT('XQ', UNIX_TIMESTAMP(NOW())+1), '资金帮扶', '想扩大养殖规模，需要资金购买饲料和幼畜，希望能申请无息贷款。', '普通', DATE_ADD(CURDATE(), INTERVAL 30 DAY), '山东省潍坊市XX县XX镇', 'test002', '李养殖户', CURDATE(), '待审核', 2),
(CONCAT('XQ', UNIX_TIMESTAMP(NOW())+2), '产品采购帮扶', '家里种了5亩有机蔬菜，包括西红柿、黄瓜、茄子等，寻找长期收购商。', '普通', DATE_ADD(CURDATE(), INTERVAL 15 DAY), '河北省保定市XX县XX村', 'test003', '张菜农', CURDATE(), '已通过', 3),
(CONCAT('XQ', UNIX_TIMESTAMP(NOW())+3), '农机帮扶', '收割季节快到了，需要收割机帮忙收割20亩小麦，希望价格优惠。', '紧急', DATE_ADD(CURDATE(), INTERVAL 5 DAY), '河南省新乡市XX县XX镇', 'test004', '赵农户', CURDATE(), '已通过', 4);

-- 帮扶实施测试数据
INSERT INTO `bangfushishi` (`shishibianhao`, `xuqiubianhao`, `ziyuanbianhao`, `bangfuleixing`, `bangfuxingshi`, `duijieshijian`, `bangfuneirong`, `bangfujindu`, `bangfuzhuangtai`, `bangfufangzhanghao`, `xuqiufangzhanghao`) VALUES
(CONCAT('SS', UNIX_TIMESTAMP(NOW())), 'XQ001', 'ZY001', '技术帮扶', '现场指导', NOW(), '已安排农艺师到现场查看小麦生长情况，诊断出是缺氮和蚜虫危害，已制定施肥和防治方案。', '已完成前期诊断，正在进行施肥和防治', '进行中', 'nongkeyuan001', 'test001'),
(CONCAT('SS', UNIX_TIMESTAMP(NOW())+1), 'XQ003', 'ZY003', '产品采购帮扶', '订单合作', NOW(), '已与农户签订收购协议，每周上门收购一次，现款结算。', '已签订协议，开始供货', '进行中', 'greenfood001', 'test003');

-- 帮扶评价测试数据
INSERT INTO `bangfupingjia` (`pingjiabianhao`, `shishibianhao`, `fuwutaidu`, `bangfuxiaoguo`, `xiangyingsudu`, `zonghepingfen`, `pingjias`, `shifouguidang`, `userid`) VALUES
(CONCAT('PJ', UNIX_TIMESTAMP(NOW())), 'SS001', 5, 5, 4, 5, '农艺师非常专业，很快就诊断出了问题，给出的解决方案也很实用。现在小麦长势很好，非常感谢！', '是', 1),
(CONCAT('PJ', UNIX_TIMESTAMP(NOW())+1), 'SS002', 5, 4, 5, 4, '收购商很守信用，价格公道，现款结算，解决了我们卖菜难的问题。', '否', 3);
