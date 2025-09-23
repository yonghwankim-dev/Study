create table if not exists locks
(
    `type`          varchar(255),
    id              varchar(255),
    lockid          varchar(255),
    expiration_time datetime,
    primary key (`type`, id)
) character set utf8;

drop index locks_idx on locks;
create unique index locks_idx ON locks (lockid);
