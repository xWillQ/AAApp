# План  
1. Прочитать требования  
2. Создать репозиторий на гитхабе  
3. Создать Hello world, .gitignore  
4. Описать тестовые данные  
    1. Описать пользователей  
    U1: log: vasya pass: 123  
    U2: log: admin pass: admin  
    U3: log: q pass: ?!#  
    U4: log: abcdefghij pass: qwerty  
    2. Описать ресурсы  
    R1: re: A ro: READ v: vasya  
    R2: re: A.B.C ro: WRITE v: vasya  
    R3: re: A.B ro: EXECUTE v: admin  
    R4: re: A ro: READ v: admin  
    R5: re: A.B ro: WRITE v: admin  
    R6: re: A.B.C ro: READ v: admin  
    R7: re: B ro: EXECUTE v: q  
    R8: re: A.A.A ro: EXECUTE v: vasya  
5. Записываем тесты в sh файл  

    - [ ] T1.1: app.jar -> 1 + вывод справки  
    - [ ] T1.2: app.jar -h -> 1 + вывод справки  
    - [ ] T1.3: app.jar -q -> 0 + вывод справки (особый случай)  
    ---  
    - [ ] T2.1: app.jar -login vasya -pass 123 -> 0  
    - [ ] T2.2: app.jar -pass 123 -login vasya -> 0  
    - [ ] T2.3: app.jar -login VASYA -pass 123 -> 2  
    - [ ] T2.4: app.jar -login asd -pass 123 -> 3  
    - [ ] T2.5: app.jar -login admin -pass 1234 -> 4  
    - [ ] T2.6: app.jar -login admin -pass admin -> 0  
    ---  
    - [ ] T3.1: app.jar -login vasya -pass 123 -role READ -res A -> 0  
    - [ ] T3.2: app.jar -login vasya -pass 123 -role DELETE -res A -> 5  
    - [ ] T3.3: app.jar -login vasya -pass 123 -role WRITE -res A -> 6  
    - [ ] T3.4: app.jar -login vasya -pass 123 -role READ -res A.B -> 0  
    - [ ] T3.5: app.jar -login admin -pass admin -role WRITE -res A.B.C -> 0  
    - [ ] T3.6: app.jar -login vasya -pass 1234 -role DELETE -res A -> 4  
    - [ ] T3.7: app.jar -login vasya -pass 123 -role WRITE -res A.B.C -> 6  
    - [ ] T3.8: app.jar -login admin -pass admin -role READ -> 0  (удачная аутент.)  
    - [ ] T3.9: app.jar -login admin -pass admin -role EXECUTE -res A -> 6  
    - [ ] T3.10: app.jar -login admin -pass admin -role WRITE -res A.A -> 6  
    ---  
    - [ ] T4.1: app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol 1024 -> 0  
    - [ ] T4.2 app.jar -login vasya -pass 123 -role READ -res A -ds 20202-03-10 -de 2020-04-01 -vol 1024 -> 7 (неверный год)  
    - [ ] T4.3 app.jar -login vasya -pass 123 -role READ -res A -ds 2020-12-10 -de 2020-13-01 -vol 1024 -> 7 (несуществующий месяц)  
    - [ ] T4.4: app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-31 -de 2020-04-01 -vol 1024 -> 7 (день которого нет в месяце)  
    - [ ] T4.5: app.jar -login vasya -pass 123 -role READ -res A -ds 2020-02-32 -de 2020-04-01 -vol 1024 -> 7 (несуществующее число)  
    - [ ] T4.6: app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol -1024 -> 7 (неверный объем)  
    - [ ] T4.7: app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -vol alot -> 7 (некорректный объем)  
    - [ ] T4.8: app.jar -login vasya -pass 123 -role READ -res A -ds 2020-03-10 -de 2020-04-01 -> 0 (удачная авторизация)  
    - [ ] T4.9: app.jar -login admin -pass admin -role WRITE -res A.B.C -ds 2020-03-10 -de 2020-01-01 -vol 1024 -> 0 (проверка другого юзера)  
    - [ ] T4.10: app.jar -login vasya -pass 123 -role WRITE -res A.B.C -ds 2020-12-01 -de 2020-01-45 -vol 1024 -> 6 (не проходит авторизация)  
    - [ ] T4.11: app.jar -login vasya -pass 123 -role READ -res A -ds 2020-04-10 -de 2020-03-01 -vol 1024 -> 7 (начало позже конца)  
6. Обрабатываем простой сценарий  
    1. Проверяем наличие аргументов  
    2. Создаём функцию вывода справки  
            - exitProcess(1)  
    3. Проверяем надо ли выводить справку  
        Args[0] == “-h”  
    4. Во всех остальных случаях выводим справку и возвращаем 0  
7. Сценарии аутентификации  
    1. Создаем метод needAuthentication()  
        Args[0] == “-login” && args[2] == “-pass”  
    2. Создаем метод validateLogin  
        (проверяем правильность формата логина через regex)  
    3. Создаем метод loginExists  
        проверяем что логин == vasya  
    4. Создаем метод authenticate()  
        проверяем что логин == vasya, пароль == 123  
    5. Создаем DataClass User (login, pass)  
    6. Создаем коллекцию юзеров, заполняем данные  
    7. Исправляем методы на работу с коллекцией  
    8.  Создаём класс argHandler:  
        - Конструктор (args:array<String>)  
        - needHelp()  
        - isArgs()  
        - needAuthentication()  
    9. Рефакторим код на использование argHandler()  
8. Сценарии авторизаци  
    1. Создаем метод needAutorization() в классе argHandler  
    2. Создаем метод validateRole  
        (проверяем правильность роли)  
    3. Создаем метод проверки доступа к ресурсу  
        проверяем что res == A, role == READ, user == vasya  
    4. Создаем DataClass Permission(res, ro, user)  
    5. Создаем коллекцию разрешений, заполняем данные  
    6. Исправляем методы на работу с коллекцией  
9. Сценарий аккаунтинга  
    1. Создаем метод needAccounting() в argHandler  
    2. Создаем метод validateVol  
        (проверяем правильность объёма)  
    3. Создаем метод validateDate  
        (проверяем правильность даты)  
    4. Создаём метод сравнения двух дат compareDate(date1, date2)  
            (date1 == 2020-01-01 && date2 == 2020-01-02)  
    5. Создаем DataClass Resource(ds, de, vol)  
    6. Переделываем compareDate на работу с любыми датами  
    7. Создаем коллекцию для хранения ресурсов  
    8. Создаем объект с переданными данными и помещаем его в коллекцию  
10. Модифицируем класс argHandler для обработки аргументов в любом порядке  
