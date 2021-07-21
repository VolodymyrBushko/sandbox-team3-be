INSERT INTO country (country_code, country_full_name)
VALUES ('UA', 'Ukraine'),
       ('BY', 'Belorus');

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (10, 'UA', 'Kyiv', 'Khreshchatyk, 25', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (20, 'UA', 'Lviv', 'Sichovyh Strilciv, 52', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO vendor (vn_id, vn_title, vn_description, vn_image_url, vn_email, vn_created, vn_modified)
VALUES (10, 'Sport Life', 'Sport Life - a chain of casual fitness centers', 'https://res.cloudinary.com/hudrds7km/image/upload/v1626823788/ltcgv0hmuszxheoa6i1p.png',
        'sprort_life@com.ua', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (20, 'Domino`s Pizza', 'Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960',
        'https://res.cloudinary.com/hudrds7km/image/upload/v1626823788/ltcgv0hmuszxheoa6i1p.png', 'dominos.com', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO category (cat_id, cat_created, cat_modified, cat_title)
VALUES (10, '2021-06-06 17:22:21', '2021-06-06 17:22:21', 'Sports and fitness'),
       (20, '2021-06-06 17:22:21', '2021-06-06 17:22:21', 'Food and entertainment');

INSERT INTO tag (tag_id, tag_created, tag_modified, tag_name, cat_id)
VALUES (1, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'sport', 10),
       (2, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'wear', 10),
       (3, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'drink', 20),
       (4, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'food', 20);

INSERT INTO discount (dis_id, dis_title, dis_short_description, dis_description, dis_image_url, dis_percentage,
                      dis_flat_amount, dis_promocode,
                      dis_start_date, dis_expiration_date, dis_created, dis_modified, cat_id, vn_id, dis_viewed, dis_version)
VALUES (1, '38% discount', 'an unlimited annual subscription',
        '38% discount on the purchase of an unlimited annual subscription to the fitness club "Sport Life"',
        'https://res.cloudinary.com/hudrds7km/image/upload/v1626823788/ltcgv0hmuszxheoa6i1p.png', null, 100, null,
        '2021-06-06 17:22:21', '2021-12-06 17:22:21', '2021-06-06 17:22:21', '2021-06-06 17:22:21', 10, 10, null),
       (2, '50% discount', '50% discount on all pizza menus',
        '50% discount on all pizza menus from the pizzeria "Domino`s Pizza"',
        'https://res.cloudinary.com/hudrds7km/image/upload/v1626823788/ltcgv0hmuszxheoa6i1p.png', null, 150, 'promocode11',
        '2022-06-06 17:22:21', '2022-12-06 17:22:21', '2021-06-06 17:22:21', '2022-06-06 17:22:21', 20, 20, null),
       (3, 'HappyDrink', '70% discount on all drinks menus',
        '70% discount on all drinks menus from the caffe "Drink House"',
        'https://res.cloudinary.com/hudrds7km/image/upload/v1626823788/ltcgv0hmuszxheoa6i1p.png', null, 150, null,
        '2023-06-06 17:22:21', '2023-12-06 17:22:21', '2021-06-06 17:22:21', '2023-06-06 17:22:21', 20, 20, null);

INSERT INTO tag_discount (dis_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 4),
       (3, 3);

INSERT INTO role (rol_id, rol_created, rol_modified, rol_name)
VALUES (1, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'USER');

INSERT INTO "user" (usr_id, usr_modified, usr_email, usr_first_name, usr_last_name, usr_created, usr_password,
                    usr_image_url, rol_id, loc_id)
VALUES (1, '2021-06-06 17:22:21', 'ivan_ivanov@gmail.com', 'Ivan', 'Ivanov', '2021-06-06 17:22:21',
        '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', 'user1.jsp', 1, 10);

INSERT INTO user_vendor_subscribe (usr_id, vn_id)
VALUES (1, 10);
