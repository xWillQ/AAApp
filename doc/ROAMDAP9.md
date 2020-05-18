# План 9
1. Создать `index.html` в `webapp`
2. В `<head>` указать скрипты:
   * `<script src="https://unpkg.com/react@16/umd/react.development.js" crossorigin></script>`
   * `<script src="https://unpkg.com/react-dom@16/umd/react-dom.development.js" crossorigin></script>`
   * `<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>`
3. Создать класс `Table` используя `React` ([гайд](https://medium.com/@subalerts/create-dynamic-table-from-json-in-react-js-1a4a7b1146ef))
   * Элемент должен принимать данные из json, список ключей и название таблицы
4. Добавить элемент `<div id="table_container">` в `index.html`
5. Создать функцию `updateData(table, query)`
   1. Создать массив `keys` и заполнить его ключами необходимыми для запрошенной таблицы
   2. Используя `jQuery.getJSON` запросить json с адреса `ajax/$table` с параметрами из `query`
   3. В callback функции отрендерить `Table` внутри элемента с `id="table_container"` с параметрами `data`, `table`, `keys` и `query`
6. В конце скрипта добавить вызов `updateData("user")`
7. Создать функцию `handler(table, id)`
   1. Если `table == "user"` вызывает `updateData("authority", "userId=" + id)`
   2. Если `table == "authority"` вызывает `updateData("activity", "authorityId=" + id)`
8. В классе `Table` при создании рядов указать `onClick` функцию
   * Вызывает `handler` с названием текущей таблицы и `id` из выбранного ряда
9. Создать класс `Form` используя `React`
   * Рендерит форму и кнопку для отправки
   * В `this.state.fields` хранит все поля из командной строки проинициализированные пустыми строками
10. Добавить элемент `<div id="form_container">` в `index.html`
11. В конце скрипта отрендерить `Form` используя `ReactDOM.render(React.createElement(Form), document.getElementById("form_container"))`
12. Создать метод `Form.changeHandler(event)`
    1. Записывает значения изменившегося input поля в соответствующее поле класса: `this.state.fields[event.target.name] = event.target.value`
13. В рендере класса `Form` элементам `<input>` добавить параметр `onChange=this.changeHandler`
14. Создать метод `Form.send()`
    1. Отправить `POST` запрос с json с полями формы на `ajax/activity`
    2. Получить ответ со статусом
    3. Если статус != 0 вывести его через `alert`
    4. Иначе отрендерить таблицу заново используя метод `updateData()` и параметры из предыдущего запроса таблицы
15. Создать метод `ActivityServlet.doPost()`
    1. Получить json из запроса и десериализовать его
    2. Собрать массив в формате массива с аргументами командной строки (`["-login", "xxx", "-pass", "xxx"]`)
    3. Вызвать `App.run()` с массивом аргументов
    4. Получить код возврата и записать его в `response`
16. В рендере класса `Form` кнопке для отправки добавить параметр `onClick=this.send`
