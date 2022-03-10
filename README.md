# Реактивный недомагазин

## Важно

* Запущена `mongodb` на порту `27017`.
* Сервер работает на порту `8080`.

## Запустить скрипт с примером
```console
./example.sh
```

## Добавить пользователя `Ivan` с предпочитаемой валютой `RUB`:

```console
/new-user?user=Ivan&currency=RUB
```

## Добавить товар `Milk` со стоимостью `$200`:
```console
/new-item?name=Milk&currency=USD&cost=2
```

## Посмотреть список товаров от лица пользователя `Ivan`:
```console
/list-items?user=Ivan
```
