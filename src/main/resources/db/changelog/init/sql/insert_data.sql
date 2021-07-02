INSERT INTO role (rol_id, rol_name, rol_created, rol_modified)
VALUES (1, 'USER', current_timestamp, current_timestamp);

INSERT INTO role (rol_id, rol_name, rol_created, rol_modified)
VALUES (2, 'ADMIN', current_timestamp, current_timestamp);

INSERT INTO country (country_code, country_full_name)
VALUES ('UA', 'Ukraine');

INSERT INTO country (country_code, country_full_name)
VALUES ('BY', 'Belarus');

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (1, 'UA', 'Kyiv', 'Khreshchatyk, 38', current_timestamp, current_timestamp);

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (2, 'UA', 'Lviv', 'Sichovyh Strilciv, 54', current_timestamp, current_timestamp);

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (3, 'BY', 'Minsk', 'Independence Avenue, 1', current_timestamp, current_timestamp);

-- INSERT INTO location (loc_id, loc_country, loc_city, loc_created, loc_modified)
-- VALUES (4, 'Ukraine', 'Rivne', current_timestamp, current_timestamp);
--
-- INSERT INTO location (loc_id, loc_country, loc_city, loc_created, loc_modified)
-- VALUES (5, 'Ukraine', 'Chernigov', current_timestamp, current_timestamp);
--
-- INSERT INTO location (loc_id, loc_country, loc_city, loc_created, loc_modified)
-- VALUES (6, 'Belarus', 'Minsk', current_timestamp, current_timestamp);
--
-- INSERT INTO location (loc_id, loc_country, loc_city, loc_created, loc_modified)
-- VALUES (7, 'Belarus', 'Grodno', current_timestamp, current_timestamp);
--
-- INSERT INTO location (loc_id, loc_country, loc_city, loc_created, loc_modified)
-- VALUES (8, 'Belarus', 'Gomel', current_timestamp, current_timestamp);
--
-- INSERT INTO location (loc_id, loc_country, loc_city, loc_created, loc_modified)
-- VALUES (9, 'Belarus', 'Berestia', current_timestamp, current_timestamp);
--
-- INSERT INTO location (loc_id, loc_country, loc_city, loc_created, loc_modified)
-- VALUES (10, 'Belarus', 'Mogilev', current_timestamp, current_timestamp);

INSERT INTO category (cat_id, cat_title, cat_image_url, cat_created, cat_modified)
VALUES (1, 'Sports and fitness', 'http://localhost/images/sport_image.jpeg', current_timestamp, current_timestamp);

INSERT INTO category (cat_id, cat_title, cat_image_url, cat_created, cat_modified)
VALUES (2, 'Food and entertainment', 'http://localhost/images/food_and_ent_category_image.jpeg', current_timestamp, current_timestamp);

INSERT INTO category (cat_id, cat_title, cat_image_url, cat_created, cat_modified)
VALUES (3, 'Tourism and leisure', 'http://localhost/images/tourism_and_leisure.jpeg', current_timestamp, current_timestamp);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_created, usr_modified, loc_id, rol_id)
VALUES (1, 'Admin', 'Admin', 'admin@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', current_timestamp, current_timestamp, 1, 2);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_created, usr_modified, loc_id, rol_id)
VALUES (2, 'User', 'User', 'test_user@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', current_timestamp, current_timestamp, 1, 1);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_created, usr_modified, loc_id, rol_id)
VALUES (3, 'Ivan', 'Ivanov', 'ivan_ivanov@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', current_timestamp, current_timestamp, 3, 2);
