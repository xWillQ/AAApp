# План 9
1. Создать `index.html` в `webapp` (0 мин.)
2. В `<head>` указать скрипты: (0 мин.)
   * `<script src="https://unpkg.com/react@16/umd/react.development.js" crossorigin></script>`
   * `<script src="https://unpkg.com/react-dom@16/umd/react-dom.development.js" crossorigin></script>`
   * `<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>`
3. Создать класс `Table` используя `React` ([гайд](https://medium.com/@subalerts/create-dynamic-table-from-json-in-react-js-1a4a7b1146ef)) (60 мин.)
   * Элемент должен принимать данные из json, список ключей и название таблицы
4. Добавить элемент `<div id="table_container">` в `index.html` (0 мин.)
5. Создать функцию `updateData(table, query)` (10 мин.)
   1. Создать массив `keys` и заполнить его ключами необходимыми для запрошенной таблицы
   2. Используя `jQuery.getJSON` запросить json с адреса `ajax/$table` с параметрами из `query`
   3. В callback функции отрендерить `Table` внутри элемента с `id="table_container"` с параметрами `data`, `table`, `keys` и `query`
6. В конце скрипта добавить вызов `updateData("user")` (0 мин.)
7. Создать функцию `handler(table, id)` (2 мин.)
   1. Если `table == "user"` вызывает `updateData("authority", "userId=" + id)`
   2. Если `table == "authority"` вызывает `updateData("activity", "authorityId=" + id)`
8. В классе `Table` при создании рядов указать `onClick` функцию (2 мин.)
   * Вызывает `handler` с названием текущей таблицы и `id` из выбранного ряда
9. Создать класс `Form` используя `React` (30 мин.)
   * Рендерит форму и кнопку для отправки
   * В `this.state.fields` хранит все поля из командной строки проинициализированные пустыми строками
10. Добавить элемент `<div id="form_container">` в `index.html` (0 мин.)
11. В конце скрипта отрендерить `Form` используя `ReactDOM.render(React.createElement(Form), document.getElementById("form_container"))` (0 мин.)
12. Создать метод `Form.changeHandler(event)` (1 мин.)
    1. Записывает значения изменившегося input поля в соответствующее поле класса: `this.state.fields[event.target.name] = event.target.value`
13. В рендере класса `Form` элементам `<input>` добавить параметр `onChange=this.changeHandler` (2 мин.)
14. Создать метод `Form.send()` (30 мин.)
    1. Отправить `POST` запрос с json с полями формы на `ajax/activity`
    2. Получить ответ со статусом
    3. Если статус != 0 вывести его через `alert`
    4. Иначе отрендерить таблицу заново используя метод `updateData()` и параметры из предыдущего запроса таблицы
15. Создать метод `ActivityServlet.doPost()` (40 мин.)
    1. Получить json из запроса и десериализовать его
    2. Собрать массив в формате массива с аргументами командной строки (`["-login", "xxx", "-pass", "xxx"]`)
    3. Вызвать `App.run()` с массивом аргументов
    4. Получить код возврата и записать его в `response`
16. В рендере класса `Form` кнопке для отправки добавить параметр `onClick=this.send` (1 мин.)

# Оценка времени
| Пункт плана | Оценка времени | Фактическое время |
| ----------- | -------------- | ----------------- |
| 1.          | 0 мин.         |                   |
| 2.          | 0 мин.         |                   |
| 3.          | 60 мин.        |                   |
| 4.          | 0 мин.         |                   |
| 5.          | 10 мин.        |                   |
| 6.          | 0 мин.         |                   |
| 7.          | 2 мин.         |                   |
| 8.          | 2 мин.         |                   |
| 9.          | 30 мин.        |                   |
| 10.         | 0 мин.         |                   |
| 11.         | 0 мин.         |                   |
| 12.         | 1 мин.         |                   |
| 13.         | 2 мин.         |                   |
| 14.         | 30 мин.        |                   |
| 15.         | 40 мин.        |                   |
| 16.         | 1 мин.         |                   |
