CREATE TABLE `oauth_users` (
  `id`                    INTEGER AUTO_INCREMENT,
  `password`              VARCHAR(256),
  `username`              VARCHAR(56) UNIQUE ,
  `accountNonExpired`     BOOL                DEFAULT TRUE,
  `accountNonLocked`      BOOL                DEFAULT TRUE,
  `credentialsNonExpired` BOOL                DEFAULT TRUE,
  `enabled`               BOOL                DEFAULT TRUE,
  PRIMARY KEY (`id`, `username`)
);

CREATE TABLE `oauth_users_groups` (
  `id`        INTEGER PRIMARY KEY AUTO_INCREMENT,
  `name`      VARCHAR(56) UNIQUE NOT NULL,
  `authority` VARCHAR(56)        NOT NULL
);

CREATE TABLE `oauth_users_authorities` (
  `user_id`  INTEGER NOT NULL,
  `group_id` INTEGER NOT NULL,
  PRIMARY KEY (`user_id`, `group_id`),
  FOREIGN KEY (`user_id`) REFERENCES oauth_users (`id`),
  FOREIGN KEY (`group_id`) REFERENCES oauth_users_groups (`id`)
);

CREATE TABLE `oauth_access_token` (
  `token_id`          VARCHAR(256) DEFAULT NULL,
  `token`             BLOB,
  `authentication_id` VARCHAR(256) DEFAULT NULL,
  `user_name`         VARCHAR(256) DEFAULT NULL,
  `client_id`         VARCHAR(256) DEFAULT NULL,
  `authentication`    BLOB,
  `refresh_token`     VARCHAR(256) DEFAULT NULL
);

CREATE TABLE `oauth_client_details` (
  `client_id`               VARCHAR(256) NOT NULL PRIMARY KEY,
  `resource_ids`            VARCHAR(256)  DEFAULT NULL,
  `client_secret`           VARCHAR(256)  DEFAULT NULL,
  `scope`                   VARCHAR(256)  DEFAULT NULL,
  `authorized_grant_types`  VARCHAR(256)  DEFAULT NULL,
  `web_server_redirect_uri` VARCHAR(256)  DEFAULT NULL,
  `authorities`             VARCHAR(256)  DEFAULT NULL,
  `access_token_validity`   INT(11)       DEFAULT NULL,
  `refresh_token_validity`  INT(11)       DEFAULT NULL,
  `additional_information`  VARCHAR(4096) DEFAULT NULL,
  `autoapprove`             VARCHAR(256)  DEFAULT NULL
);

CREATE TABLE `oauth_code` (
  `code`           VARCHAR(256) DEFAULT NULL,
  `authentication` BLOB
);

CREATE TABLE `oauth_refresh_token` (
  `token_id`       VARCHAR(256) DEFAULT NULL,
  `token`          BLOB,
  `authentication` BLOB
);