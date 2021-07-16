INSERT INTO category (cat_id, cat_created, cat_modified, cat_title)
VALUES (10, '2021-06-06 17:22:21', '2021-06-06 17:22:21', 'Sports and fitness'),
       (20, '2021-06-06 17:22:21', '2021-06-06 17:22:21', 'Food and entertainment');

INSERT INTO tag (tag_id, tag_created, tag_modified, tag_name, cat_id)
VALUES (1, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'sport', 10),
       (2, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'wear', 10),
       (3, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'drink', 20),
       (4, '2021-06-04 17:22:21', '2021-06-04 17:22:21', 'food', 20);