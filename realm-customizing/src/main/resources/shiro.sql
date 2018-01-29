create database if not exists shiro;
create table if not exists shiro_users (
	id int primary key auto_increment,
	username varchar(64),
	password varchar(64)
);
insert into shiro_users (id, username, password) values(1, 'user', 'user');

create table if not exists shiro_users_roles (
	user_id int,
	role_id int
);
insert into shiro_users_roles (user_id, role_id) values (1,1);
insert into shiro_users_roles (user_id, role_id) values (1,2);
insert into shiro_users_roles (user_id, role_id) values (2,2);

create table if not exists shiro_roles (
	id int primary key auto_increment,
	rolename varchar(20)
);
insert into shiro_roles (id, rolename) values(1, 'admin');
insert into shiro_roles (id, rolename) values(2, 'user');

create table if not exists shiro_roles_permissions (
	role_id int,
	permission_id int
);
insert into shiro_roles_permissions (role_id, permission_id) values (1, 1);
insert into shiro_roles_permissions (role_id, permission_id)values (1, 2);
insert into shiro_roles_permissions (role_id, permission_id)values (2, 2);

create table if not exists shiro_permissions (
	id int primary key auto_increment,
	permissionname varchar(50)
);
insert into shiro_permissions (id, permissionname) values (1, "module:*");
insert into shiro_permissions (id, permissionname) values (2, "user:*");