CREATE TABLE `accounts` (
  `id`         INTEGER PRIMARY KEY AUTO_INCREMENT,
  `user_id`    INTEGER NOT NULL,
  `name`       VARCHAR(56),
  `score`      BIGINT  NOT NULL    DEFAULT 0,
  `holded`     BIGINT  NOT NULL    DEFAULT 0,
  `is_blocked` TINYINT(1)          DEFAULT 0,
  FOREIGN KEY (`user_id`) REFERENCES oauth_users (`id`)
    ON DELETE CASCADE
);

CREATE TABLE `transactions_amounts` (
  `id`              INTEGER PRIMARY KEY AUTO_INCREMENT,
  `amount`          BIGINT              DEFAULT 0,
  `order_amount`    BIGINT              DEFAULT 0,
  `tip_amount`      BIGINT              DEFAULT 0,
  `cashback_amount` BIGINT              DEFAULT 0
);

CREATE TABLE `transactions` (
  `guid`        VARCHAR(36) PRIMARY KEY UNIQUE                 NOT NULL,
  `parent`      INTEGER                                        NULL,
  `child`       INTEGER                                        NULL,
  `amounts`     INTEGER                                        NOT NULL,
  `type`        TINYINT(1)                                     NOT NULL,
  `operation`   TINYINT(1)                                     NOT NULL,
  `status`      TINYINT(1)                                     NOT NULL,
  `comment`     VARCHAR(255)                                   NULL,
  `created`     TIMESTAMP                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated`     TIMESTAMP                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `source`      INTEGER                                        NOT NULL,
  `destination` INTEGER                                        NOT NULL,
  FOREIGN KEY (`amounts`) REFERENCES transactions_amounts (`id`),
  FOREIGN KEY (`source`) REFERENCES accounts (`id`),
  FOREIGN KEY (`destination`) REFERENCES accounts (`id`)
);
