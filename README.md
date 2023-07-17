# java-filmorate
Template repository for Filmorate project.


![ERD](/assets/pdf/ERD.pdf)

GET users
SELECT * 
FROM users

GET films
SELECT f.id,
       f.name,
       f.description,
       f.releaseDate,
       f.duration,
       mpa.name
FROM film AS f
LEFT JOIN mpa_date_id AS mpa ON mpa.mpa_rate_id = f.mpa_rate_id

GET film genres ("id" is id of film whose genres looking for)
SELECT fg.name
FROM film_genre AS fg
INNER JOIN genre AS g ON g.genre_id = fg.genre_id 
WHERE g.film_id = id

GET films likes count
SELECT f.film_id,
       f.name,
       COUNT(l.user_id) AS likes_count
FROM film
LEFT JOIN likes AS l ON l.film_id = f.film_id
GROUP BY f.film_id, f.name
ORDER BY likes_count DESC

GET user friends ("id" is id of user whose genres looking for. If you need to upload complete data about friends, you can use this query as a subquery or use another query using UNION and split the condition in WHERE)
SELECT user_id
FROM friend_link AS fl
WHERE fl.initiator_user_id = id =  OR (fl.recipient_user_id = id AND fl.confirm = true)