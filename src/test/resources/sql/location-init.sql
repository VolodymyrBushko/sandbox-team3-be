INSERT INTO country (country_code, country_full_name)
VALUES ('UA', 'Ukraine'),
       ('BY', 'Belorus');

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (1, 'UA', 'Kyiv', 'Khreshchatyk, 25', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (2, 'UA', 'Lviv', 'Sichovyh Strilciv, 52', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (3, 'UA', 'Kyiv', 'Prospekt Nauky, 41', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (4, 'UA', 'Lviv', 'Gorodockogo, 9', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (5, 'UA', 'Kharkiv', 'Peremogy, 11', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO vendor (vn_id, vn_title, vn_description, vn_image_url, vn_email, vn_created, vn_modified)
VALUES (1, 'Sport Life', 'Sport Life - a chain of casual fitness centers', 'sport_life_image_1.jsp',
        'sprort_life@com.ua', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (2, 'Domino`s Pizza', 'Domino`s Pizza - an American multinational pizza restaurant chain founded in 1960',
        'dominos.com_image_1.jsp',
        'dominos.com', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (3, 'TUI', 'TUI AG - travel and tourism company', 'tui_image_1.jsp', 'tuigroup.com',
        '2021-06-06 17:22:21', '2021-06-06 17:22:21');

