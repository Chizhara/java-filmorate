MERGE INTO mpa_rates(mpa_rate_id, mpa_rate_name)
VALUES (1, 'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');

MERGE INTO genres(genre_id, genre_name)
VALUES (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик');

INSERT INTO users(user_name, login, email, birthday)
   VALUES ('Nick', 'login', 'some@mail.ru', '1999-09-09'),
          ('Robin', 'password', 'another@mail.ru', '2000-09-09'),
          ('Jo', 'pass', 'nnwww@mail.ru', '2001-09-09');

INSERT INTO films(film_name, description, release_date, duration_in_minutes, mpa_rate_id)
   VALUES ('nisi eiusmod', 'adipisicing', '1967-03-25', 100, 1),
          ('eiusmod', 'icing', '1968-04-26', 101, 2);

INSERT INTO film_genres(film_id, genre_id)
    VALUES(1,1);

INSERT INTO likes(film_id, user_id)
    VALUES(1,1);

INSERT INTO friend_links(initiator_user_id, recipient_user_id, confirm)
    VALUES(2,1,true);