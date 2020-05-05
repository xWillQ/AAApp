# План 7
1. Создать `ConnectionProvider` (20 мин.)
   * Возвращает коннект к БД с нужными URL, логином и паролем
   * URL, логин и пароль передаются через переменные среды
   * Если переменные среды отсутствуют, берутся значения по умолчанию
2. Заинжектить `ConnectionProvider` в `DBWrapper` (5 мин.)
3. Переписать методы `DBWrapper` на работу с провайдером (40 мин.)
4. Добавить поле `id` в таблицу `users` в БД, сделать его первичным ключом (2 мин.)
5. Добавить поле `id` в класс `User` (1 мин.)
6. Добавить метод `getUser(id)` в `DBWrapper` (25 мин.)
   * Возвращает `List<User>`
   * Если `id != 0` список содержит пользователя с указанным `id`
   * Иначе список содержит всех пользователей
7. Переименовать таблицу `permissions` и объект `Permission` на `authorities` и `Authority` соответственно (20 мин.)
8. В таблице `authorities` заменить поле `login` на поле `userId`, сделать его внешним ключом и связать с таблицей `users` (2 мин.)
9. Добавить метод `getAuthority(id)` в `DBWrapper` (15 мин.)
   * Возвращает `List<Authority>`
   * Если `id != 0` список содержит разрешение с указанным `id`
   * Иначе список содержит все разрешения
10. Добавить метод `getAuthorityByUser(userId)` в `DBWrapper` (15 мин.)
   * Возвращает `List<Authority>` с разрешениями пользователя с указанным `userId`
11. В класс `Activity` добавить поле `id` (1 мин.)
12. В классе `Activity` заменить поля `user`, `res` и `role` на поле `authority` (1 мин.)
13. Добавить метод `getActivity(id)` в `DBWrapper` (10 мин.)
   * Возвращает `List<Activity>`
   * Если `id != 0` список содержит действия с указанным `id`
   * Иначе список содержит все действия
14. Добавить метод `getActivityByAuthority(authorityId)` в `DBWrapper` (10 мин.)
   * Возвращает `List<Activity>` с действиями с указанным разрешением
15. Создать `GSONProvider` (10 мин.)
    * Возвращает `GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()`
16. Заинжектить `GSONProvider` в классы `UserServlet`, `AuthorityServlet` и `ActivityServlet` (5 мин.)
17. Заинжектить `DBWrapper` в классы `UserServlet`, `AuthorityServlet` и `ActivityServlet` (90 мин.)
18. Реализовать метод `UserServlet.doGet()` (20 мин.)
    1. Создать переменную `userList`
    2. Если в запросе присутствует `id`: `userList = DBWrapper.getUser(id)`
    3. Иначе `userList = DBWrapper.getUser(0)`
    4. Сериализовать `userList` и вернуть полученный json
       * Для получения сериализатора использовать `GSONProvider`
19. Реализовать метод `AuthorityServlet.doGet()` (20 мин.)
    1. Создать переменную `authorityList`
    2. Если в запросе присутствует `id`: `authorityList = DBWrapper.getAuthority(id)`
    3. Если в запросе присутствует `userId`: `authorityList = DBWrapper.getAuthorityByUser(userId)`
    4. Иначе `authorityList = DBWrapper.getAuthority(0)`
    5. Сериализовать `authorityList` и вернуть полученный json
       * Для получения сериализатора использовать `GSONProvider`
20. Реализовать метод `ActivityServlet.doGet()` (20 мин.)
    1. Создать переменную `activityList`
    2. Если в запросе присутствует `id`: `activityList = DBWrapper.getActivity(id)`
    3. Если в запросе присутствует `userId`: `activityList = DBWrapper.ActivityByAuthority(authorityId)`
    4. Иначе `activityList = DBWrapper.getActivity(0)`
    5. Сериализовать `activityList` и вернуть полученный json
       * Для получения сериализатора использовать `GSONProvider`
21. В классе `User` добавить аннотацию `@Expose` для всех полей кроме `salt` и `hash` (5 мин.)
22. В классе `Authority` добавить аннотацию `@Expose` для всех полей кроме `user` (5 мин.)
23. В классе `Activity` добавить аннотацию `@Expose` для всех полей кроме `authority` (5 мин.)

# Оценка времени
| Пункт плана | Оценка времени | Фактическое время |
| ----------- | -------------- | ----------------- |
| 1.          | 20 мин.        |                   |
| 2.          | 5 мин.         |                   |
| 3.          | 40 мин.        |                   |
| 4.          | 2 мин.         |                   |
| 5.          | 1 мин.         |                   |
| 6.          | 25 мин.        |                   |
| 7.          | 20 мин.        |                   |
| 8.          | 2 мин.         |                   |
| 9.          | 15 мин.        |                   |
| 10.         | 15 мин.        |                   |
| 11.         | 1 мин.         |                   |
| 12.         | 1 мин.         |                   |
| 13.         | 10 мин.        |                   |
| 14.         | 10 мин.        |                   |
| 15.         | 10 мин.        |                   |
| 16.         | 5 мин.         |                   |
| 17.         | 90 мин.        |                   |
| 18.         | 20 мин.        |                   |
| 19.         | 20 мин.        |                   |
| 20.         | 20 мин.        |                   |
| 21.         | 5 мин.         |                   |
| 22.         | 5 мин.         |                   |
| 23.         | 5 мин.         |                   |