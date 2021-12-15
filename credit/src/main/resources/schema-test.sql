create table if not exists credit (
  id bigint primary key not null generated always as identity,
  credit_name varchar(1000) not null
);