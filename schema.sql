drop table if exists users;
create table users (
    usr_id serial primary key,
    usr_name varchar(255) not null,
    usr_email varchar(125) not null,
    usr_master_password varchar(125) not null,
    usr_admin_id int default null,
    usr_login_tries int default 0,
    constraint FK_USERS_ADMIN foreign key (usr_admin_id) references users(usr_id),
    constraint UQ_USERS_EMAIL unique (usr_email)
);

-- masterPassword = 123456
insert into users(usr_name, usr_email, usr_master_password, usr_admin_id, usr_login_tries)
    values
        ('Samuel', 'samuel@email.com', '$2a$10$bdszONkWGzLw733tIwwIeOgjL4uoEi1z37IMszOotRZHPoSs7hCr.', null, 0),
        ('Gabriel', 'gabriel@email.com', '$2a$10$bdszONkWGzLw733tIwwIeOgjL4uoEi1z37IMszOotRZHPoSs7hCr.', 1, 0);


drop table if exists passwords;
create table passwords (
    pwd_id serial primary key,
    pwd_name varchar(63) not null,
    pwd_password varchar(63) not null,
    usr_id int not null,
    CONSTRAINT fk_passwords_user FOREIGN KEY(usr_id) REFERENCES users(usr_id) ON DELETE CASCADE
);

drop table if exists roles;
create table roles(
    rol_id serial primary key,
    rol_name text,
    rol_description text
);

drop table if exists users_roles;
create table users_roles(
    rol_id int,
    usr_id int,
    primary key(rol_id, usr_id),
    CONSTRAINT fk_roles_user FOREIGN KEY(usr_id) REFERENCES users(usr_id) ON DELETE CASCADE,
    CONSTRAINT fk_roles_role FOREIGN KEY(rol_id) REFERENCES roles(rol_id) ON DELETE CASCADE
);

insert into roles values (1, 'ROLE_ADMIN', 'Platform administrator'), (2, 'ROLE_USER', 'Platform user');
insert into users_roles values (1, 1);