create table users
(
    id       int(11) auto_increment primary key,
    username varchar(255) unique not null,
    password varchar(255)        not null,
    role     varchar(255)        not null default 'CUSTOMER'
);
