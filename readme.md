# Recipe For Disaster

シンプルなレシピのCRUDをサポートするアプリ。

## Supported REST methods:
GET baseurl/ -> 全件検索
GET baseurl/{id} -> 一件検索
POST baseurl body={title(string),making_time(string),serves(string),ingredients(string),cost(string)} -> レシピ追加
PATCH baseurl/{id} body={title(string),making_time(string),serves(string),ingredients(string),cost(string)} -> レシピ丸ごとリプレース
DELETE baseurl/{id} -> 削除する

## Libraries used:
Spring
Spring Data + Jpa
Mockito
JUnit
Lombok

## Database:
h2database (test)
postgresql (local/production)

## Issues:

正直エラーが発生する時、HTTP基準で様々なエラーコードを出すべきが、今回の仕様によって、全部200OKにしている。
レシピ見つかれない時 = 404
レシピの情報足りない時 = 400
