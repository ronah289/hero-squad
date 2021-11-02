CREATE DATABASE hero_squad;
\c hero_squad;
CREATE TABLE hero (name TEXT,id SERIAL PRIMARY KEY, age INTEGER, powers TEXT,weakness TEXT);
CREATE TABLE squad (maxSize INTEGER,name TEXT,cause TEXT,id INTEGER);
CREATE DATABASE hero_squad_test WITH TEMPLATE hero_squad;