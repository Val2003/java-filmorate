insert into GENRE(id, name)
values (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик');
insert into MPA(id, name)
values (1, 'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');

INSERT INTO USER_FILMORATE (email, login, name, birthday)
VALUES ('doritos@gmail.com', 'dorito', 'Mark', DATE '2021-12-12'),
       ('pizza@dodo,com', 'chiken', 'Theodore', DATE '2020-12-12'),
       ('zavtra@net.net', 'nofuture', 'Dead', DATE '2019-12-12');

INSERT INTO FRIENDSHIP (friend1_id, friend2_id)
VALUES (1, 2),
       (1, 3),
       (2, 3);

INSERT INTO FILM (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA, LIKES_AMOUNT)
VALUES ('Terminator', 'Test description', DATE '1984-10-26', 90, 1, 0),
       ('Terminator2', 'Test description2', DATE '1984-10-26', 90, 1, 0),
       ('Terminator23', 'Test description23', DATE '1984-10-26', 90, 1, 0);

INSERT INTO LIKES (FILM_ID, USER_ID)
VALUES (1, 2),
       (1, 3),
       (2, 3);