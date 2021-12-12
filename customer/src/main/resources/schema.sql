create table if not exists customer (
 id bigint primary key not null generated always as identity,
 first_name varchar(1000) not null,
 surname varchar(1000) not null,
 pesel varchar(11) not null,
 credit_id bigint not null unique,
 constraint fk_customer foreign key(credit_id) references credit(id)	
);