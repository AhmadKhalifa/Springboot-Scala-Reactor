create table orders
(
    id         int(11) auto_increment primary key,
    user_id    int(11) not null,
    payment_id int(11) not null,
    constraint fk_user_id foreign key (user_id) references users (id),
    constraint fk_payment_id foreign key (payment_id) references payments (id)
);
