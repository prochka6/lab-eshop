-- Insert into users, default admin password is admin
insert into user (id, email, first_name, last_name, password, username) values (1, "admin@admin", "admin", "admin", "ISMvKXpXpadDiUoOSoAfww==", "admin")
-- User with default password cust
insert into user (id, email, first_name, last_name, password, username) values (2, "cust@cust", "Martin", "Tomasek", "Oq01BqoR8F8mXqgwS4FSsw==", "cust")




-- JAAS insert required, create role

insert into role (id, role) values(1,"admin");
insert into role (id, role) values(2,"customer");

-- Connect roles with users

-- Admin is customer and admin as well
insert into user_role (users_id, roles_id) values (1,1);
insert into user_role (users_id, roles_id) values (1,2);
-- User 2 is customer
insert into user_role (users_id, roles_id) values (2,2);