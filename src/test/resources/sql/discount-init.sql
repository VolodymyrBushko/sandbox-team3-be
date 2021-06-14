INSERT INTO location
VALUES (10, 'Kyiv', 'Ukraine', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (20, 'Lviv', 'Ukraine', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO vendor
VALUES (10, '2021-06-06 17:22:21', 'Sport Life - a chain of casual fitness centers', 'sprort_life@com.ua',
        'http://localhost/images/img.png', '2021-06-06 17:22:21', 'Sport Life', 10),
       (20, '2021-06-06 17:22:21', 'Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960',
        'dominos.com', 'http://localhost/images/img.png', '2021-06-06 17:22:21', 'Domino`s Pizza', 20);

INSERT INTO category
VALUES (10, '2021-06-06 17:22:21', 'http://localhost/images/img.png', '2021-06-06 17:22:21', 'Sports and fitness'),
       (20, '2021-06-06 17:22:21', 'http://localhost/images/img.png', '2021-06-06 17:22:21', 'Food and entertainment');

INSERT INTO discount
VALUES (1, '2021-06-06 17:22:21',
        '38% discount on the purchase of an unlimited annual subscription to the fitness club "Sport Life',
        '2021-12-06 17:22:21', 100, 'sport_life_discount_image_1.jsp',
        '2021-06-06 17:22:21', 1, 38, 62, 50, 'an unlimited annual subscription',
        '2021-06-06 17:22:21', '38% discount', 10, 10),
       (2, '2021-06-06 17:22:21',
        '50% discount on all pizza menus from the pizzeria "Domino`s Pizza"',
        '2021-12-06 17:22:21', 150, 'domino`s_pizza_discount_image_1.jsp',
        '2021-06-06 17:22:21', 1, 50, 150, 150, '50% discount on all pizza menus',
        '2021-06-06 17:22:21', '50% discount', 20, 20);
