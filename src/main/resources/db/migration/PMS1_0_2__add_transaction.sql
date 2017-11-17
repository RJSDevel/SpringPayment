CREATE TABLE `accounts` (
  `id`         INTEGER PRIMARY KEY               AUTO_INCREMENT,
  `user_id`    INTEGER        NOT NULL,
  `name`       VARCHAR(56),
  `score`      NUMERIC(19, 2) NOT NULL           DEFAULT 0,
  `hold`       NUMERIC(19, 2) NOT NULL           DEFAULT 0,
  `is_active`  TINYINT(1)     NOT NULL           DEFAULT 1,
  `is_blocked` TINYINT(1)     NOT NULL           DEFAULT 0,
  `currency`   INTEGER        NOT NULL,
  FOREIGN KEY (`user_id`) REFERENCES oauth_users (`id`)
    ON DELETE CASCADE,
  FOREIGN KEY (`currency`) REFERENCES currency (`id`)
    ON DELETE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `transactions` (
  `guid`            VARCHAR(36) PRIMARY KEY UNIQUE                         NOT NULL,
  `parent`          VARCHAR(36)                                            NULL,
  `child`           VARCHAR(36)                                            NULL,
  `type`            TINYINT(1)                                             NOT NULL,
  `operation`       TINYINT(1)                                             NOT NULL,
  `status`          TINYINT(1)                                             NOT NULL,
  `previous_status` TINYINT(1),
  `created`         TIMESTAMP                                              NOT NULL                 DEFAULT CURRENT_TIMESTAMP,
  `updated`         TIMESTAMP                                              NOT NULL                 DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `currency`        INTEGER                                                NOT NULL,
  `amount`          NUMERIC(19, 2)                                         NOT NULL                 DEFAULT 0,
  `order_amount`    NUMERIC(19, 2)                                         NOT NULL                 DEFAULT 0,
  `tip_amount`      NUMERIC(19, 2)                                         NOT NULL                 DEFAULT 0,
  `cashback_amount` NUMERIC(19, 2)                                         NOT NULL                 DEFAULT 0,
  `comment`         VARCHAR(256)                                           NULL,
  `source`          INTEGER                                                NOT NULL,
  `destination`     INTEGER                                                NOT NULL,
  FOREIGN KEY (`source`) REFERENCES accounts (`id`),
  FOREIGN KEY (`destination`) REFERENCES accounts (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;