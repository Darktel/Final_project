# Final_project


Регистрация	Успешная регистрация с уникальным email. Попытка зарегистрироваться повторно
api - POST https://qa-desk.stand.praktikum-services.ru/api/signup
{
  "email": "111111113311@QQQ.RU",
  "password": "123123123",
  "submitPassword": "123123123"
}

Авторизация	Авторизация ранее зарегистрированного пользователя
api - POST https://qa-desk.stand.praktikum-services.ru/api/signin

{
  "email": "111111113311@qqq.ru",
  "password": "111111113311@QQQ.RU"
}


Создание объявления	Успешное создание объявления в любой категории
api - POST https://qa-desk.stand.praktikum-services.ru/api/create-listing
{id: 14185, name: "Продам нано гараж", category: "Технологии", condition: "Новый", city: "Новосибирск",…}

{"id":14186,"name":"\"Продам нано гараж\"","category":"Технологии","condition":"Б/У","city":"Новосибирск","description":"\"Покупай лучший нано гараж во всей  области.\"","price":473456,"img1":"https://qa-foodgram.s3.yandex.net/ÐÐµÐ·Ð¸Ð¼ÐµÐ½Ð¸.png","owner":22459,"updatedAt":"2025-11-28T20:16:50.112Z","createdAt":"2025-11-28T20:16:50.112Z","img2":null,"img3":null,"isFavorite":null}


Редактирование	Успешное редактирование своего объявления
api - PATCH https://qa-desk.stand.praktikum-services.ru/api/update-offer/14186

name "Продам нано гараж"
category Технологии
condition Б/У
city Новосибирск
description "Покупай лучший нано гараж во всей  области."2
price 473456
img1 https://qa-foodgram.s3.yandex.net/ÐÐµÐ·Ð¸Ð¼ÐµÐ½Ð¸.png
img2 null
img3 null

Удаление	Успешное удаление своего объявления
api - 



Выявленные ошибки. 
1. подача обьявления доступна не авторизованным пользователям. 
2. сервер возвращает ошибку 504, в случае если в поле цены было введено не числовое значение.
3. В случае если у разных объявлений одинаковые названия загружаемого изображения. То происходит подмена изображения. У ранее опубликованных объявлений.
4. Не корректно работает поиск по наименованию. Он не находит объявления. 