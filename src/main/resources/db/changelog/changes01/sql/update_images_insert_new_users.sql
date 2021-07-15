UPDATE "user"
SET usr_image_url = 'https://raw.githubusercontent.com/Bozhokmaria/images/main/admin.jpg'
WHERE usr_id = '1';

UPDATE "user"
SET usr_image_url = 'https://raw.githubusercontent.com/Bozhokmaria/images/main/ivan.jpg'
WHERE usr_id = '2';

UPDATE "user"
SET usr_image_url = 'https://raw.githubusercontent.com/Bozhokmaria/images/main/user.jpg'
WHERE usr_id = '3';

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_image_url, usr_created, usr_modified, loc_id, rol_id)
VALUES (4, 'Andrey', 'Andrey', 'exadelteam3@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2',
        'https://raw.githubusercontent.com/Bozhokmaria/images/main/user.jpg', current_timestamp, current_timestamp, 1, 2);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_image_url, usr_created, usr_modified, loc_id, rol_id)
VALUES (5, 'Maksym', 'Maxx', 'test_max@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2',
        'https://raw.githubusercontent.com/Bozhokmaria/images/main/admin.jpg',current_timestamp, current_timestamp, 1, 1);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_image_url, usr_created, usr_modified, loc_id, rol_id)
VALUES (6, 'Inna', 'Ivanova', 'inna_ivanova@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2',
        'https://raw.githubusercontent.com/Bozhokmaria/images/main/ivan.jpg', current_timestamp, current_timestamp, 3, 2);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_image_url, usr_created, usr_modified, loc_id, rol_id)
VALUES (7, 'Ivanna', 'Iv', 'ivanna_iv@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2',
        'https://raw.githubusercontent.com/Bozhokmaria/images/main/ivan.jpg', current_timestamp, current_timestamp, 3, 2);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_image_url, usr_created, usr_modified, loc_id, rol_id)
VALUES (8, 'John', 'Ivanov', 'john_ivanov@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2',
        'https://raw.githubusercontent.com/Bozhokmaria/images/main/admin.jpg', current_timestamp, current_timestamp, 3, 2);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_image_url, usr_created, usr_modified, loc_id, rol_id)
VALUES (9, 'John', 'Smith', 'hreshchuk@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2',
        'https://raw.githubusercontent.com/Bozhokmaria/images/main/user.jpg', current_timestamp, current_timestamp, 2, 2);

INSERT INTO "user" (usr_id, usr_first_name, usr_last_name, usr_email, usr_password, usr_image_url, usr_created, usr_modified, loc_id, rol_id)
VALUES (10, 'Maria', 'Ivanova', 'bozhokmaria@gmail.com', '$2y$12$QkgAOhRydRHVZz07qhfT0eKFgWMLWp4WLjrr2ZLJNnA3yMt44lWq2',
        'https://raw.githubusercontent.com/Bozhokmaria/images/main/user.jpg', current_timestamp, current_timestamp, 1, 1);
