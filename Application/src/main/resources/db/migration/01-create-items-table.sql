create table items
(
    id        int(11) auto_increment primary key,
    name      varchar(255) not null,
    price     double       not null,
    available boolean      not null
);
