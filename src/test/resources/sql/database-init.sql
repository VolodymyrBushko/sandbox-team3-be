INSERT INTO role
VALUES (1, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'USER'),
       (2, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'ADMIN');

INSERT INTO location
VALUES (1, 'Kyiv', 'Ukraine', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (2, 'Lviv', 'Ukraine', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO "user"
VALUES (1, '2021-06-06 17:22:21', 'ivan_ivanov@gmail.com', 'Ivan', 'Ivanov', '2021-06-06 17:22:21', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', 1, 1),
       (2, '2021-06-06 17:22:21', 'petro_petrenko@gmail.com', 'Petro', 'Petrenko', '2021-06-06 17:22:21', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', 2, 2);

INSERT INTO category (cat_id, cat_title, cat_image_url, cat_created, cat_modified)
VALUES (1, 'category-1', 'http://localhost/images/img1.png', '2021-06-06 16:33:41.396856',
        '2021-06-06 16:33:41.396856'),
       (2, 'category-2', 'http://localhost/images/img2.png', '2021-06-07 23:06:20.749989',
        '2021-06-07 23:06:20.749989');

INSERT INTO vendor
VALUES (1, '2021-06-06 17:22:21', 'Sport Life - a chain of casual fitness centers', 'sprort_life@com.ua',
        'sport_life_image_1.jsp', '2021-06-06 17:22:21', 'Sport Life', 1),
       (2, '2021-06-06 17:22:21', 'Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960',
        'dominos.com', 'dominos.com_image_1.jsp', '2021-06-06 17:22:21', 'Domino`s Pizza', 2),
       (3, '2021-06-06 17:22:21', 'TUI AG - travel and tourism company', 'tuigroup.com', 'tui_image_1.jsp',
        '2021-06-06 17:22:21', 'TUI', 1);

INSERT INTO discount
VALUES (1, '2021-06-06 17:22:21',
        '38% discount on the purchase of an unlimited annual subscription to the fitness club "Sport Life',
        '2021-12-06 17:22:21', 100, 'sport_life_discount_image_1.jsp',
        '2021-06-06 17:22:21', 1, 38, 62, 50, 'an unlimited annual subscription',
        '2021-06-06 17:22:21', '38% discount', 1, 1),
       (2, '2021-06-06 17:22:21',
        '50% discount on all pizza menus from the pizzeria "Domino`s Pizza"',
        '2021-12-06 17:22:21', 150, 'domino`s_pizza_discount_image_1.jsp',
        '2021-06-06 17:22:21', 1, 50, 150, 150, '50% discount on all pizza menus',
        '2021-06-06 17:22:21', '50% discount', 2, 2);

