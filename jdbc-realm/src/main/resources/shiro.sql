create database if not exists shiro;
create table if not exists users (
	id int primary key auto_increment,
	username varchar(64),
	password varchar(64)
);
insert into users (id, username, password) values(1, 'user', 'user');