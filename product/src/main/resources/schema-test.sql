create table if not exists credit (
  id bigint primary key not null generated always as identity,
  credit_name varchar(1000) not null
);

create table if not exists product (
 id bigint primary key not null generated always as identity,
 name varchar(1000) not null,
 product_value integer not null,
 credit_id bigint not null unique,
 constraint fk_product foreign key(credit_id) references credit(id)
);