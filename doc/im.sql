CREATE TABLE `im_user`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '业务主键',
    `open_id`       varchar(32) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '微信 openid',
    `username`      varchar(20) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '帐号',
    `password`      varchar(20) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '密码',
    `nickname`      varchar(20) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '昵称',
    `avatar`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
    `gender`        int(11) DEFAULT NULL COMMENT '性别: 1男性, 2女性',
    `ip_info`       json                                    DEFAULT NULL COMMENT 'ip信息',
    `active_status` int(11) DEFAULT '2' COMMENT '活跃状态: 1在线, 2离线',
    `status`        int(11) DEFAULT '1' COMMENT '用户状态: 1正常, 2拉黑',
    `last_opt_time` datetime(3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '最后上下线时间',
    `create_time`   datetime(3) DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
    `update_time`   datetime(3) DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '修改时间',
    `deleted`       tinyint(1) unsigned DEFAULT '0' COMMENT '是否删除: 1Y, 0N',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_user_id` (`user_id`) USING BTREE,
    UNIQUE KEY `uniq_open_id` (`open_id`) USING BTREE,
    UNIQUE KEY `uniq_username` (`username`) USING BTREE,
    UNIQUE KEY `uniq_nickname` (`nickname`) USING BTREE,
    KEY             `idx_create_time` (`create_time`) USING BTREE,
    KEY             `idx_update_time` (`update_time`) USING BTREE,
    KEY             `idx_active_status_last_opt_time` (`active_status`,`last_opt_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='im 用户信息表';

CREATE TABLE `im_chat_message`
(
    `id`                   bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `message_id`           bigint(20) UNSIGNED NOT NULL COMMENT '业务主键',

    `from_user_id`         bigint(20) NOT NULL COMMENT '发送用户主键',
    `target_type`          int(11) NOT NULL COMMENT '目标对象类型: 1单聊 2群聊',
    `target_user_id`       bigint(20) DEFAULT NULL COMMENT '目标用户主键(type为1时有值)',
    `from_target_user_key` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发送与目标用户的主键拼接Key(根据user_id排序后通过中横线 - 连接)，用于快速搜索私聊消息(type为1时有值)',
    `chat_group_id`        bigint(20) DEFAULT NULL COMMENT '群聊主键(type为2时有值)',

    `content`              varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息内容',
    `reply_msg_id`         bigint(20) DEFAULT NULL COMMENT '回复的消息主键',
    `gap_count`            int(11) DEFAULT NULL COMMENT '与回复的消息间隔多少条',
    `type`                 int(11) DEFAULT 1 COMMENT '消息类型: 1正常文本, 2撤回消息',
    `create_time`          datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
    `update_time`          datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_message_id` (`message_id`) USING BTREE,
    INDEX                  `idx_from_user_id`(`from_user_id`) USING BTREE,
    INDEX                  `idx_target_user_id`(`target_user_id`) USING BTREE,
    INDEX                  `idx_from_target_user_key`(`from_target_user_key`) USING BTREE,
    INDEX                  `idx_chat_group_id`(`chat_group_id`) USING BTREE,
    INDEX                  `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'IM消息表' ROW_FORMAT = Dynamic;

CREATE TABLE `im_user_contact`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `concat_id`      bigint(20) unsigned NOT NULL COMMENT '业务主键',
    `user_id`        bigint(20) NOT NULL COMMENT '用户',
    `concat_user_id` bigint(20) NOT NULL COMMENT '联系人主键',
    `deleted`        tinyint(1) unsigned DEFAULT '0' COMMENT '是否删除: 1Y, 0N',
    `create_time`    datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
    `update_time`    datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_concat_id` (`concat_id`) USING BTREE,
    KEY              `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='IM用户联系人表';

CREATE TABLE `im_chat_group`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `group_id`    bigint(20) unsigned NOT NULL COMMENT '业务主键',
    `name`        varchar(16) COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '群名称',
    `avatar`      varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '群头像',
    `ext_json`    json DEFAULT NULL COMMENT '额外信息（根据不同类型房间有不同存储的东西）',
    `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
    `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_group_id` (`group_id`) USING BTREE,
    KEY           `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='IM群聊表';

CREATE TABLE `im_chat_group_member`
(
    `id`              bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `group_member_id` bigint(20) unsigned NOT NULL COMMENT '业务主键',
    `group_id`        bigint(20) unsigned NOT NULL COMMENT '群聊主键',
    `user_id`         bigint(20) NOT NULL COMMENT '成员主键',
    `role`            int(11) NOT NULL COMMENT '成员角色 1群主 2管理员 3普通成员',
    `create_time`     datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
    `update_time`     datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_group_member_id` (`group_member_id`) USING BTREE,
    KEY               `idx_group_id` (`group_id`) USING BTREE,
    KEY               `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='IM群成员表';

CREATE TABLE `im_chat_session`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `session_id`     bigint(20) unsigned NOT NULL COMMENT '业务主键',

    `user_id`        bigint(20) NOT NULL COMMENT '用户主键',
    `type`           int(11) NOT NULL COMMENT '房间类型 1单聊 2群聊',
    `target_user_id` bigint(20) DEFAULT NULL COMMENT '目标用户主键(type为1时有值)',
    `chat_group_id`  bigint(20) DEFAULT NULL COMMENT '群聊主键(type为2时有值)',

    `last_msg_id`    bigint(20) DEFAULT NULL COMMENT '会话中的最新消息主键(即最新消息)',
    `create_time`    datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) COMMENT '创建时间',
    `update_time`    datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3) COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_session_id` (`session_id`) USING BTREE,
    KEY              `idx_user_id` (`user_id`) USING BTREE,
    KEY              `idx_target_user_id` (`target_user_id`) USING BTREE,
    KEY              `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='IM聊天会话列表';