INSERT INTO country
VALUES ('UA', 'Ukraine'),
       ('BY', 'Belorus');

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (1, 'UA', 'Kyiv', 'Khreshchatyk, 25', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (2, 'UA', 'Lviv', 'Sichovyh Strilciv, 52', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (3, 'UA', 'Kyiv', 'Prospekt Nauky, 41', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (4, 'UA', 'Lviv', 'Gorodockogo, 9', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (5, 'UA', 'Kharkiv', 'Peremogy, 11', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO vendor
VALUES (1, '2021-06-06 17:22:21', 'Sport Life - a chain of casual fitness centers', 'sprort_life@com.ua',
        'sport_life_image_1.jsp', '2021-06-06 17:22:21', 'Sport Life'),
       (2, '2021-06-06 17:22:21', 'Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960',
        'dominos.com', 'dominos.com_image_1.jsp', '2021-06-06 17:22:21', 'Domino`s Pizza'),
       (3, '2021-06-06 17:22:21', 'TUI AG - travel and tourism company', 'tuigroup.com', 'tui_image_1.jsp',
        '2021-06-06 17:22:21', 'TUI');

