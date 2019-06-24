DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals(ID, datetime, DESCRIPTION, CALORIES, USER_ID)
VALUES (100100, '2015-05-30 10:00', 'Завтрак', 500, 100000),
       (100101, '2015-05-30 13:00', 'Обед', 1000, 100000),
       (100102, '2015-05-30 20:00', 'Ужин', 500, 100000),
       (100103, '2015-05-31 10:00', 'Завтрак', 1000, 100000),
       (100104, '2015-05-31 13:00', 'Обед', 500, 100000),
       (100105, '2015-05-31 20:00', 'Ужин', 510, 100000),--
       (100110, '2015-05-30 10:00', 'Завтрак', 500, 100001),
       (100111, '2015-05-30 13:00', 'Обед', 1000, 100001),
       (100112, '2015-05-30 20:00', 'Ужин', 500, 100001),
       (100113, '2015-05-31 10:00', 'Завтрак', 1000, 100001),
       (100114, '2015-05-31 13:00', 'Обед', 500, 100001),
       (100115, '2015-05-31 20:00', 'Ужин', 510, 100001);
       
