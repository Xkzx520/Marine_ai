-- 请在 CMD 中用 utf8mb4 导入，见项目说明或下方命令
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

DROP TABLE IF EXISTS `t_current_knowledge`;
DROP TABLE IF EXISTS `t_current_params`;
DROP TABLE IF EXISTS `t_current_scene`;
DROP TABLE IF EXISTS `t_biology`;
DROP TABLE IF EXISTS `t_course`;
DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_course` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '课程ID',
    `course_name` varchar(128) NOT NULL COMMENT '课程名称',
    `intro` text COMMENT '课程介绍',
    `cover_url` varchar(255) DEFAULT NULL COMMENT '封面图地址',
    `course_type` tinyint NOT NULL COMMENT '1=AI生物识别 2=洋流交互式模拟',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程信息表';

CREATE TABLE `t_biology` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '生物ID',
    `bio_name` varchar(64) NOT NULL COMMENT '生物名称',
    `en_name` varchar(64) DEFAULT NULL COMMENT '英文名称',
    `intro` text COMMENT '科普介绍',
    `habits` varchar(512) DEFAULT NULL COMMENT '生活习性',
    `distribution` varchar(512) DEFAULT NULL COMMENT '分布区域',
    `protection_level` varchar(64) DEFAULT NULL COMMENT '保护等级',
    `ai_feature_tip` varchar(512) DEFAULT NULL COMMENT 'AI特征提示',
    `img_url` varchar(255) DEFAULT NULL COMMENT '图片地址',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_bio_name` (`bio_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='海洋生物百科表';

CREATE TABLE `t_current_scene` (
    `scene_id` bigint NOT NULL AUTO_INCREMENT COMMENT '场景ID',
    `scene_name` varchar(64) NOT NULL COMMENT '场景名称',
    `wind_power` int DEFAULT 0 COMMENT '风力大小',
    `coriolis_enabled` tinyint(1) DEFAULT 1 COMMENT '是否开启地转偏向力',
    `continent_layout` int DEFAULT 0 COMMENT '大陆布局编号',
    `particle_count` int DEFAULT 300 COMMENT '粒子数量',
    `speed_coefficient` decimal(5,2) DEFAULT 1.00 COMMENT '速度系数',
    `description` text COMMENT '场景说明',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`scene_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='洋流场景配置表';

CREATE TABLE `t_current_params` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '参数ID',
    `viscosity` decimal(5,2) DEFAULT 0.98 COMMENT '粘性系数',
    `diffusion` decimal(5,2) DEFAULT 0.05 COMMENT '扩散系数',
    `coriolis_intensity` decimal(5,2) DEFAULT 0.30 COMMENT '科氏力强度',
    `max_wind_power` int DEFAULT 10 COMMENT '最大风力',
    `particle_max_count` int DEFAULT 500 COMMENT '最大粒子数',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='洋流物理参数表';

CREATE TABLE `t_current_knowledge` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '知识ID',
    `title` varchar(128) NOT NULL COMMENT '知识点标题',
    `content` text NOT NULL COMMENT '知识点内容',
    `sort` int DEFAULT 0 COMMENT '排序',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='洋流科普知识表';

CREATE TABLE `t_user` (
    `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` varchar(32) NOT NULL COMMENT '账号',
    `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
    `role` varchar(16) DEFAULT 'student' COMMENT '角色',
    `password` varchar(128) DEFAULT NULL COMMENT '密码摘要',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

INSERT INTO t_course (course_name, intro, course_type) VALUES
('海洋小卫士-AI生物识别', '基于YOLO实现海洋生物拍照识别，学习海洋生物知识', 1),
('洋流探险家-交互式模拟', '通过粒子系统模拟洋流运动，理解风场与地转偏向力', 2);

INSERT INTO t_biology (bio_name, en_name, intro, habits, distribution, protection_level, ai_feature_tip) VALUES
('小丑鱼', 'Clownfish', '热带珊瑚礁鱼类，与海葵形成共生关系', '群居，依赖海葵获得保护', '印度洋-太平洋珊瑚礁', '无危', '橙白条纹与高对比度轮廓便于 CNN 提取'),
('海龟', 'Sea Turtle', '古老的海洋爬行动物，具有长距离洄游习性', '长距离洄游，以海草、水母等为食', '全球热带与亚热带海域', '多种濒危', '背甲纹理与鳍肢形态是重要识别特征'),
('海豚', 'Dolphin', '高度社会化的海洋哺乳动物，智商发达', '群居狩猎，回声定位', '全球温带与热带海域', '部分种类受保护', '流线型体型与背鳍轮廓'),
('海星', 'Starfish', '肉食性棘皮动物，具备强大再生能力', '缓慢爬行，捕食贝类', '全球浅海与潮间带', '多数无危', '五角辐射对称外形'),
('鱼类', 'Fish', '海洋中种类最多的脊椎动物群体。', '以鳃呼吸，靠鳍游泳', '全球海域', '因物种而异', '鱼鳍与流线型躯干纹理'),
('水母', 'Jellyfish', '腔肠动物，伞状身体靠伞缘收缩游动。', '浮游捕食', '温带至热带近海', '多数无危', '半透明伞状体边缘'),
('鲨鱼', 'Shark', '软骨鱼类顶级捕食者。', '主动捕食，嗅觉灵敏', '全球温带与热带海域', '多种濒危', '背鳍与头部三角轮廓'),
('金枪鱼', 'Tuna', '高速游泳的大型鱼类。', '远洋洄游群居', '太平洋、大西洋、印度洋', '部分种类过度捕捞', '纺锤形体型与体表反光');

INSERT INTO t_current_scene (scene_name, wind_power, coriolis_enabled, continent_layout, description) VALUES
('赤道洋流', 6, 1, 1, '信风驱动形成的赤道暖流系统'),
('北大西洋环流', 5, 1, 2, '西风带主导的中纬度环流');

INSERT INTO t_current_params (viscosity, diffusion, coriolis_intensity, max_wind_power, particle_max_count)
VALUES (0.98, 0.05, 0.30, 10, 500);

INSERT INTO t_current_knowledge (title, content) VALUES
('风海流', '由风力驱动海面形成的洋流，是最主要的洋流类型'),
('科里奥利力', '地球自转产生的偏转力，决定洋流运动方向'),
('洋流作用', '调节全球热量分布、影响气候与海洋生态环境');

INSERT INTO t_user (username, nickname, role, password) VALUES
('test', '测试学生', 'student', '7y6H8JORr6iJqriaFV3lOghhpTCBTxFRJTL/RD0x4IA=');
