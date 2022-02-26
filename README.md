### Дипломный проект по курсу «Тестировщик ПО»

___

##### О проекте:

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.  

Приложение предлагает купить тур по определённой цене с помощью двух способов:  

* Обычная оплата по дебетовой карте  
  
* Уникальная технология: выдача кредита по данным банковской карты.  
  

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

* сервису платежей;
  
* кредитному сервису
___
### Документация

* [План тестирования](https://github.com/Chzhanchik/QADiplom/blob/master/documents/Plan.md "План тестирования")
  
* [Отчет по итогам тестирования](https://github.com/Chzhanchik/QADiplom/blob/master/documents/Report.md "Отчет по итогам тестирования")
  
* [Комплексный отчет по итогам процесса автоматизации](https://github.com/Chzhanchik/QADiplom/blob/master/documents/Summary.md "Комплексный отчет по итогам процесса автоматизации")


___

##### **Инструкция по запуску тестов:**

Предварительная подготовка к тестированию:
1. Установить IntelliJ Idea. Скачать и ознакомиться с документацией можно на официальном сайте https://www.jetbrains.com/ru-ru/idea/.
   
2. Установить Docker Desktop. Скачать и ознакомиться с документацией можно на официальном сайте https://www.docker.com/get-started.

_Для запуска тестов необходимо:_

1. Скачать проект с удаленного репозитория на свой локальный, с помощью команды `git clone https://github.com/ulyana190909/QA-Diploma_Project`
   
2. Открыть проект на установленной заранее IntelliJ Idea
   
3. Развернуть контейнеры с помощью команды `docker-compose up` в терминале
   
4. В соседней вкладке запустить само приложение либо командой   
   
   `java -Dserver.port=8090 -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar` - для БД Postgresql

   либо  
   
   `java -Dserver.port=8090 -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar` - для БД MysSQL  
   
5. Убедиться в том, что приложение запустилось, в логах будет строка:  
   
   `INFO 10688--- [  main] ru.netology.shop.ShopApplication  : Starting ShopApplication v0.0.1-SNAPSHOT on LAPTOP-NF0QCEOC with PID 10688`
   
6. Запустить тесты командой  
   
   `gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app` -  для БД MysSQL  
   
   либо  
   
   `gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app` - для БД Postgresql  
   
7. Открыть отчет о прохождении тестов командой `gradlew allureServe`
   
___

##### **Подготовка отчета Allure**

При необходимости создания отчета тестирования, запустить тесты следующим образом:   

`gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app аllureReport` -  для БД MysSQL

либо

`gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app аllureReport` - для БД Postgresql  

`allureReport` - используется при первой генерации отчета.  


При повторной генерации отчета запускать тесты командой:  

`gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app allureServe` -  для БД MysSQL 

либо

`gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/app allureServe` - для БД Postgresql  

Отчет будет открываться после прохождения тестов автоматически в браузере.  

Если потребуется преждевременно завершить прохождение тестов, набрать команду Ctrl+C, далее Y.