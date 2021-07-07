INSERT INTO role
VALUES (1, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'USER'),
       (2, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'ADMIN');

INSERT INTO location (loc_id, country_code, loc_city, loc_address_line, loc_created, loc_modified)
VALUES (1, 'UA','Kyiv', '2021-06-06 17:22:21', '2021-06-06 17:22:21'),
       (2, 'UA','Lviv', '2021-06-06 17:22:21', '2021-06-06 17:22:21');

INSERT INTO "user"
VALUES (1, '2021-06-06 17:22:21', 'ivan_ivanov@gmail.com', 'Ivan', 'Ivanov', '2021-06-06 17:22:21', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', 1, 1),
       (2, '2021-06-06 17:22:21', 'petro_petrenko@gmail.com', 'Petro', 'Petrenko', '2021-06-06 17:22:21', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2', 2, 2);