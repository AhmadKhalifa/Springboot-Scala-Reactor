create table payments
(
    id      int(11) auto_increment primary key,
    user_id int(11) not null,
    state   varchar(255) default 'INITIALIZED',
    amount  double  not null,
    constraint fk_user_id foreign key (user_id) references users (id)
);
