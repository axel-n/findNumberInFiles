# findNumber

1. найти число в файлах (состоят только из чисел, которые разделены между собой запятой)
2. результат работы необходимо записать в таблицу БД и вернуть объект Result в вызывающую систему.

Что включает:
    - spring
    - логгирование
    - бд (можно посмотреть таблицы после запуска приложения по адресу http://127.0.0.1:8080/h2-console/)
    - код для генерации тестовых файлов (где будет происходить поиск)
    - немного документации

Что еще сделать:
    - многопоточность
    - пофиксить сохранение списка файлов (где нашли число) - сейчас сохраняется в бд почему-то хеш
