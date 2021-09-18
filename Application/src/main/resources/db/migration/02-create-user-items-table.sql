create table user_items
(
    id       int(11) auto_increment primary key,
    user_id  int(11) not null,
    item_id  int(11) not null,
    quantity int     not null default 1,
    constraint fk_user_id foreign key (user_id) references users (id),
    constraint fk_item_id foreign key (item_id) references items (id)
);
