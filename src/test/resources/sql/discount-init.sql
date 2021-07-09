INSERT INTO country
VALUES ('UA', 'Ukraine'),
       ('BY', 'Belorus');

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (10, 'UA', 'Kyiv', 'Khreshchatyk, 25', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (20, 'UA', 'Lviv', 'Sichovyh Strilciv, 52', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO vendor
VALUES (10, '2021-06-06 17:22:21', 'Sport Life - a chain of casual fitness centers', 'sprort_life@com.ua',
        'http://localhost/images/img.png', '2021-06-06 17:22:21', 'Sport Life', 10),
       (20, '2021-06-06 17:22:21', 'Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960',
        'dominos.com', 'http://localhost/images/img.png', '2021-06-06 17:22:21', 'Domino`s Pizza', 20);

INSERT INTO category
VALUES (10, '2021-06-06 17:22:21', '2021-06-06 17:22:21', 'Sports and fitness'),
       (20, '2021-06-06 17:22:21', '2021-06-06 17:22:21', 'Food and entertainment');

INSERT INTO tag
VALUES (1, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'sport', 10),
       (2, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'wear', 10),
       (3, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'drink', 20),
       (4, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'food', 20);

INSERT INTO discount
VALUES (1, '2021-06-06 17:22:21',
        '38% discount on the purchase of an unlimited annual subscription to the fitness club "Sport Life"',
        '2021-12-06 17:22:21', 100, 'sport_life_discount_image_1.jsp',
        '2021-06-06 17:22:21', null, 50, 'an unlimited annual subscription',
        '2021-06-06 17:22:21', '38% discount', 10, 10),
       (2, '2021-06-06 17:22:21',
        '50% discount on all pizza menus from the pizzeria "Domino`s Pizza"',
        '2022-12-06 17:22:21', 150, 'domino`s_pizza_discount_image_1.jsp',
        '2022-06-06 17:22:21', null, 150, '50% discount on all pizza menus',
        '2022-06-06 17:22:21', '50% discount', 20, 20),
       (3, '2021-06-06 17:22:21',
        '70% discount on all drinks menus from the caffe "Drink House"',
        '2023-12-06 17:22:21', 150, 'drinks.jsp',
        '2023-06-06 17:22:21', null, 150, '70% discount on all drinks menus',
        '2023-06-06 17:22:21', 'HappyDrink', 20, 20);

INSERT INTO tag_discount (dis_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 4),
       (3, 3);
