# java-filmorate

Filmorate project.

# java-filmorate

![Диаграмма таблицы проекта](/ER_Diagram.png)

##### _Получение всех пользователей из таблицы:_

"SELECT id, email, login, name, birthday\n" +
"FROM USER_FILMORATE"

##### _Запрос на получение списка всех фильмов:_

SELECT id, name, description, release_date, duration, mpa ,rate , LIKES_AMOUNT \n" +
"FROM film

