-- Insert into users, default admin password is admin
insert into user (id, email, first_name, last_name, password, username) values (1, "admin@admin", "admin", "admin", "ISMvKXpXpadDiUoOSoAfww==", "admin")
-- User with default password customer
insert into user (id, email, first_name, last_name, password, username) values (2, "cust@cust", "Martin", "Tomasek", "kewfkyR1MEjACW0DamlPhg==", "customer")

-- Custom customers
insert into user (id, email, first_name, last_name, password, username) values (3, "jirkan@email.cz", "Jiri", "Novak", "kewfkyR1MEjACW0DamlPhg==", "jirkan")
insert into user (id, email, first_name, last_name, password, username) values (4, "jansvob@seznam.cz", "Jan", "Svoboda", "kewfkyR1MEjACW0DamlPhg==", "jansvob")
insert into user (id, email, first_name, last_name, password, username) values (5, "pitrnovo@centrum.cz", "Petr", "Novotny", "kewfkyR1MEjACW0DamlPhg==", "pitrnovo")
insert into user (id, email, first_name, last_name, password, username) values (6, "pepacerny@email.cz", "Josef", "Cerny", "kewfkyR1MEjACW0DamlPhg==", "pepacerny")
insert into user (id, email, first_name, last_name, password, username) values (7, "jardakuca@email.cz", "Jaroslav", "Kucera", "kewfkyR1MEjACW0DamlPhg==", "jardakuca")
insert into user (id, email, first_name, last_name, password, username) values (8, "nemecv@centrum.cz", "Vaclav", "Nemec", "kewfkyR1MEjACW0DamlPhg==", "nemecv")
insert into user (id, email, first_name, last_name, password, username) values (9, "lukykral@email.cz", "Lukas", "Kral", "kewfkyR1MEjACW0DamlPhg==", "lukykral")
insert into user (id, email, first_name, last_name, password, username) values (10, "kubamarek@gmail.com", "Jakub", "Marek", "kewfkyR1MEjACW0DamlPhg==", "kubamarek")
insert into user (id, email, first_name, last_name, password, username) values (11, "maruskan@email.cz", "Marie", "Novakova", "kewfkyR1MEjACW0DamlPhg==", "maruskan")
insert into user (id, email, first_name, last_name, password, username) values (12, "dvorakjana@seznam.cz", "Jana", "Dvorakova", "kewfkyR1MEjACW0DamlPhg==", "dvorakjana")
insert into user (id, email, first_name, last_name, password, username) values (13, "evaproch@email.cz", "Eva", "Prochazkova", "kewfkyR1MEjACW0DamlPhg==", "evaproch")
insert into user (id, email, first_name, last_name, password, username) values (14, "ajavesela@atlas.cz", "Alena", "Vesela", "kewfkyR1MEjACW0DamlPhg==", "ajavesela")
insert into user (id, email, first_name, last_name, password, username) values (15, "luciepokorna@email.cz", "Lucie", "Pokorna", "kewfkyR1MEjACW0DamlPhg==", "luciepokorna")
insert into user (id, email, first_name, last_name, password, username) values (16, "fialka@seznam.cz", "Jarmila", "Fialova", "kewfkyR1MEjACW0DamlPhg==", "fialka")

-- JAAS insert required, create role
insert into role (id, role) values(1,"admin");
insert into role (id, role) values(2,"customer");

-- Connect roles with users

-- Admin is customer and admin as well
insert into user_role (users_id, roles_id) values (1,1);
insert into user_role (users_id, roles_id) values (1,2);
-- User 2 is customer
insert into user_role (users_id, roles_id) values (2,2);

-- Custom customers roles
insert into user_role (users_id, roles_id) values (3,2);
insert into user_role (users_id, roles_id) values (4,2);
insert into user_role (users_id, roles_id) values (5,2);
insert into user_role (users_id, roles_id) values (6,2);
insert into user_role (users_id, roles_id) values (7,2);
insert into user_role (users_id, roles_id) values (8,2);
insert into user_role (users_id, roles_id) values (9,2);
insert into user_role (users_id, roles_id) values (10,2);
insert into user_role (users_id, roles_id) values (11,2);
insert into user_role (users_id, roles_id) values (12,2);
insert into user_role (users_id, roles_id) values (13,2);
insert into user_role (users_id, roles_id) values (14,2);
insert into user_role (users_id, roles_id) values (15,2);
insert into user_role (users_id, roles_id) values (16,2);

-- Default categories
insert into category (name) values ("Computers");
insert into category (name) values ("Notebooks");
insert into category (name) values ("Tablets");
insert into category (name) values ("Televisions");
insert into category (name) values ("Phones");
insert into category (name) values ("Accessories");

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
insert into product (title, code, brand_id, category_id, availability, price, pieces, rating , publishDate, discardDate, summary, description) values ("Apple iMac 15.6","0541057AF",2,2,"IN_STOCK",36000,5,100,"2012-11-11","2013-03-15","Apple je lamerska spolecnost","Bla bla bla, long");
insert into product (title, code, brand_id, category_id, availability, price, pieces, rating , publishDate, discardDate, summary, description) values ("Acer V5-571P-6648 Core i3 4GB 500GB 15.6","054105435QS",1,2,"ORDERED",13602,0,80,"2012-12-11","2013-01-25",'Acer V5-571P-6648 Core i3 4GB 500GB 15.6 Touchscreen USB 3.0 Laptop Windows 8','Manufacturer refurbished: An item that has been professionally restored to working order by a manufacturer or manufacturer-approved vendor. This means the product has been inspected, cleaned, and repaired to meet manufacturer specifications and is in excellent condition. This item may or may not be in the original packaging. See the seller’s listing for full details.');


