CREATE SCHEMA IF NOT EXISTS book_schema;

create table if not exists book_schema.books
(
    book_id     bigserial
        primary key,
    author      varchar(255),
    book_name   varchar(255),
    category_id bigint
        constraint uk_lna470nsjh6u843tbpi35mnba
            unique
        constraint fkleqa3hhc0uhfvurq6mil47xk0
            references book_schema.categories,
    info        varchar(255)
);

create table if not exists book_schema.categories
(
    category_id   bigserial
        primary key,
    category_name varchar(255)
);


INSERT INTO categories(category_id, category_name) VALUES (1, 'cat1');
INSERT INTO categories(category_id, category_name) VALUES (2, 'cat1');
INSERT INTO categories(category_id, category_name) VALUES (3, 'cat2');
INSERT INTO categories(category_id, category_name) VALUES (4, 'cat2');
INSERT INTO categories(category_id, category_name) VALUES (5, 'cat2');

INSERT INTO books(book_id, author, book_name, category_id, info) VALUES (1, 'author1', 'bookname1', 1, 'some info 1');
INSERT INTO books(book_id, author, book_name, category_id, info) VALUES (2, 'author2', 'bookname2', 2, 'some info 2');
INSERT INTO books(book_id, author, book_name, category_id, info) VALUES (3, 'author3', 'bookname3', 3, 'some info 3');
INSERT INTO books(book_id, author, book_name, category_id, info) VALUES (4, 'author3', 'bookname4', 4, 'some info 3');
INSERT INTO books(book_id, author, book_name, category_id, info) VALUES (5, 'author3', 'bookname5', 5, 'some info 3');
