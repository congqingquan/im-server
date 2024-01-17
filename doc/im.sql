CREATE TABLE `im_user`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id`       bigint(20) unsigned NOT NULL COMMENT '业务主键',
    `open_id`       varchar(32) COLLATE utf8mb4_unicode_ci  DEFAULT NULL COMMENT '微信 openid',
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
    UNIQUE KEY `uniq_nickname` (`nickname`) USING BTREE,
    KEY             `idx_create_time` (`create_time`) USING BTREE,
    KEY             `idx_update_time` (`update_time`) USING BTREE,
    KEY             `idx_active_status_last_opt_time` (`active_status`,`last_opt_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='im 用户信息表';