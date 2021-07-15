INSERT INTO role (rol_id, rol_created, rol_modified, rol_name)
VALUES (1, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'USER'),
       (2, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'ADMIN');

INSERT INTO country (country_code, country_full_name)
VALUES ('UA', 'Ukraine'),
       ('BY', 'Belorus');

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (1, 'UA', 'Kyiv', 'Khreshchatyk, 25', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (2, 'UA', 'Lviv', 'Sichovyh Strilciv, 52', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO "user" (usr_id, usr_modified, usr_email, usr_first_name, usr_last_name, usr_created, usr_password, usr_image_url, rol_id, loc_id)
VALUES (1, '2021-06-06 17:22:21', 'ivan_ivanov@gmail.com', 'Ivan', 'Ivanov', '2021-06-06 17:22:21', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2','user1.jsp', 1, 1),
       (2, '2021-06-06 17:22:21', 'petro_petrenko@gmail.com', 'Petro', 'Petrenko', '2021-06-06 17:22:21', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', 'user2.jsp', 2, 2);