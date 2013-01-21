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

-- Default categories
insert into category (name) values ("Computers");
insert into category (name) values ("Notebooks");

-- Default brands
insert into brand (name) values ("Acer");
insert into brand (name) values ("Apple");
insert into brand (name) values ("Asus");
insert into brand (name) values ("Dell");
insert into brand (name) values ("Lenovo");
insert into brand (name) values ("MSI");
insert into brand (name) values ("Packard Bell");
insert into brand (name) values ("Samsung");
insert into brand (name) values ("Sony");

-- Some products
insert into product (title, brand_id, category_id, availability, price, summary, description) values ("Apple iMac 15.6",2,2,"IN_STOCK",36000,"Apple je lamerska spolecnost","Bla bla bla, long");
insert into product (title, brand_id, category_id, availability, price, summary, description) values ("Acer V5-571P-6648 Core i3 4GB 500GB 15.6",1,2,"IN_STOCK",13602,'Acer V5-571P-6648 Core i3 4GB 500GB 15.6 Touchscreen USB 3.0 Laptop Windows 8','Manufacturer refurbished: An item that has been professionally restored to working order by a manufacturer or manufacturer-approved vendor. This means the product has been inspected, cleaned, and repaired to meet manufacturer specifications and is in excellent condition. This item may or may not be in the original packaging. See the seller’s listing for full details.');


