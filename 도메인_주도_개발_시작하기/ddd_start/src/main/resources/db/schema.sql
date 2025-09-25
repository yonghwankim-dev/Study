create table if not exists locks
(
    `type`          varchar(255),
    id              varchar(255),
    lockid          varchar(255),
    expiration_time datetime,
    primary key (`type`, id)
) character set utf8;

SET @idx_exists = (SELECT COUNT(1)
                   FROM INFORMATION_SCHEMA.STATISTICS
                   WHERE table_schema = DATABASE()
                     AND table_name = 'locks'
                     AND index_name = 'locks_idx');

-- 동적 SQL을 사용하여 존재하지 않으면 생성
SET @sql_text = IF(@idx_exists = 0, 'CREATE UNIQUE INDEX locks_idx ON locks(lockid);', 'SELECT "Index already exists"');

PREPARE stmt FROM @sql_text;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

create table if not exists evententry
(
    id             int not null AUTO_INCREMENT PRIMARY KEY,
    `type`         varchar(255),
    `content_type` varchar(255),
    payload        MEDIUMTEXT,
    `timestamp`    datetime
) character set utf8mb4;
