#!/bin/bash
set -eux

curl 'http://localhost:8080/clear'

curl 'http://localhost:8080/new-user?user=Ivan&currency=RUB'

curl 'http://localhost:8080/new-user?user=Alexander&currency=EUR'

curl 'http://localhost:8080/new-user?user=Maria&currency=USD'

curl 'http://localhost:8080/new-item?name=Milk&currency=USD&cost=2'

curl 'http://localhost:8080/new-item?name=Butter&currency=RUB&cost=150'

curl 'http://localhost:8080/new-item?name=Knife&currency=EUR&cost=20'

curl 'http://localhost:8080/list-items?user=Ivan'

curl 'http://localhost:8080/list-items?user=Alexander'

curl 'http://localhost:8080/list-items?user=Maria'
