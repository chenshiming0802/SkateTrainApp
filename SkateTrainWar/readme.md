#### 目录说明
- core 存储核心的文件,架构层面的代码,项目无法修改
- inc 项目的公共代码


#### 测试地址
http://127.0.0.1/DailyTrainWar/app/select_course_sub/


CREATE TABLE `td_xxxx` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rec_create_time` datetime DEFAULT NULL,
  `rec_update_time` datetime DEFAULT NULL,
  `is_deleted` varchar(3) COLLATE utf8_unicode_ci DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci