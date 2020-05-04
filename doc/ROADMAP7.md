# План 7
1. Добавить поле `id` в таблицу `users` в БД, сделать его первичным ключом
2. Добавить поле `id` в класс `User`

3. Добавить метод `getUser(id)` в `DBWrapper`
   * Возвращает `List<User>`
   * Если `id != 0` список содержит пользователя с указанным `id`
   * Иначе список содержит всех пользователей

4. В таблице `permissions` заменить поле `login` на поле `userId`, сделать его внешним ключом и связать с таблицей `users`

5. Добавить метод `getPermission(id)` в `DBWrapper`
   * Возвращает `List<Permission>`
   * Если `id != 0` список содержит разрешение с указанным `id`
   * Иначе список содержит все разрешения
6. Добавить метод `getPermissionByUser(userId)` в `DBWrapper`
   * Возвращает `List<Permission>` с разрешениями пользователя с указанным `userId`

7. В класс `Activity` добавить поле `id`

8.  Добавить метод `getActivity(id)` в `DBWrapper`
   * Возвращает `List<Activity>`
   * Если `id != 0` список содержит действия с указанным `id`
   * Иначе список содержит все действия
11. Добавить метод `getActivityByPermission(permissionId)` в `DBWrapper`
   * Возвращает `List<Activity>` с действиями с указанным разрешением

11. Создать `GSONProvider`
    * Возвращает `GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()`
12. Заинжектить `GSONProvider` в классы `UserServlet`, `AuthorityServlet` и `ActivityServlet`
13. Заинжектить `DBWrapper` в классы `UserServlet`, `AuthorityServlet` и `ActivityServlet`

14. Реализовать метод `UserServlet.doGet()`
    1. Создать переменную `userList`
    2. Если в запросе присутствует `id`: `userList = DBWrapper.getUser(id)`
    3. Иначе `userList = DBWrapper.getUser(0)`
    4. Сериализовать `userList` и вернуть полученный json
       * Для получения сериализатора использовать `GSONProvider`

15. Реализовать метод `AuthorityServlet.doGet()`
    1. Создать переменную `permissionList`
    2. Если в запросе присутствует `id`: `permissionList = DBWrapper.getPermission(id)`
    3. Если в запросе присутствует `userId`: `permissionList = DBWrapper.getPermissionByUser(userId)`
    4. Иначе `permissionList = DBWrapper.getPermission(0)`
    5. Сериализовать `permissionList` и вернуть полученный json
       * Для получения сериализатора использовать `GSONProvider`

16. Реализовать метод `ActivityServlet.doGet()`
    1. Создать переменную `activityList`
    2. Если в запросе присутствует `id`: `activityList = DBWrapper.getActivity(id)`
    3. Если в запросе присутствует `userId`: `activityList = DBWrapper.ActivityByPermission(authorityId)`
    4. Иначе `activityList = DBWrapper.getActivity(0)`
    5. Сериализовать `activityList` и вернуть полученный json
       * Для получения сериализатора использовать `GSONProvider`

17. В классе `User` добавить аннотацию `@Expose` для всех полей кроме `salt` и `hash`
18. В классе `Permission` добавить аннотацию `@Expose` для всех полей кроме `user`
19. В классе `Activity` добавить аннотацию `@Expose` для всех полей кроме `user`, `res` и `role`
