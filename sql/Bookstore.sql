DROP DATABASE IF EXISTS bookstore;
create database IF NOT EXISTS bookstore;

use bookstore;

CREATE TABLE IF NOT EXISTS roles(
  id INT(5) NOT NULL PRIMARY KEY ,
  name VARCHAR(10) NOT NULL UNIQUE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');
INSERT INTO roles VALUES(2, 'librarian');


CREATE TABLE IF NOT EXISTS users (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  login varchar(20) NOT NULL DEFAULT '' UNIQUE,
  email varchar(20) NOT NULL DEFAULT '',
  password varchar(20) NOT NULL DEFAULT '',
  first_name VARCHAR(20) NOT NULL DEFAULT '',
  last_name VARCHAR(20) NOT NULL DEFAULT '',
  role_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- id = 1
INSERT INTO users VALUES(DEFAULT ,'admin', 'admin@admin.ru', 'admin', 'Ivan', 'Ivanov', 0);
-- id = 2
INSERT INTO users VALUES(DEFAULT ,'client', 'client@cl.com', 'client', 'Petr', 'Petrov', 1);
-- id = 3
INSERT INTO users VALUES(DEFAULT ,'petrov', 'петров@us.ua', 'petrov', 'Иван', 'Петров', 2);
-- id = 4
INSERT INTO users VALUES(DEFAULT ,'ivanov', 'ivan@se.ua', 'ivan', 'Vasya', 'Vasya', 1);

CREATE TABLE books (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(128) NOT NULL,
  author varchar(100) NOT NULL,
  editon VARCHAR(100) NOT NULL,
  date_editon DATE NOT NULL,
  number_copies INT(10) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO books VALUES(DEFAULT , 'Убийство в северном экспрессе', 'Агата Кристи',
                         'Фантастика','2017.10.23',1);
INSERT INTO books VALUES(DEFAULT , 'Аномальная зона', 'Андрей Кокотюха',
                         'Ужасы','2017.05.13',2);
INSERT INTO books VALUES(DEFAULT , 'Философия JAVA', 'Брюс Эккель',
                         'Программирование','1998.02.23',1);
INSERT INTO books VALUES(DEFAULT , 'JAVA Методы программирования', 'Блинов И.Н, Романчик В.С',
                         'Программирование','2013.11.25',4);
INSERT INTO books VALUES(DEFAULT , 'Страна Развлечений', 'Ствиен Кинг',
                         'Ужасы,Триллеры','2014.09.11',3);
INSERT INTO books VALUES(DEFAULT , 'Кукла', 'Даниель Коул',
                         'Детективы','2017.12.02',1);

CREATE TABLE statuses(
  id INTEGER NOT NULL PRIMARY KEY,
  name VARCHAR(15) NOT NULL UNIQUE
);

INSERT INTO statuses VALUES (0, 'opened');
INSERT INTO statuses VALUES (1, 'confirmed');
INSERT INTO statuses VALUES (2, 'paid');
INSERT INTO statuses VALUES (3, 'closed');

CREATE TABLE orders(
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  bill INTEGER NOT NULL DEFAULT 0,
  user_id INTEGER NOT NULL REFERENCES users(id),
  status_id INTEGER NOT NULL  REFERENCES statuses(id)
);

INSERT INTO orders VALUES (DEFAULT ,0, 2, 0);
INSERT INTO orders VALUES (DEFAULT ,0,4, 3);

CREATE TABLE orders_menu(
  id INTEGER NOT NULL AUTO_INCREMENT,
  order_id INTEGER NOT NULL REFERENCES orders(id),
  book_id INTEGER NOT NULL REFERENCES books(id)
);

insert INTO  orders_menu VALUEs (1, 1);
insert INTO  orders_menu VALUEs (1, 4);
insert INTO  orders_menu VALUEs (2, 3);
insert INTO  orders_menu VALUEs (2, 6);

CREATE TABLE date_fine(
  id INT(10) AUTO_INCREMENT,
  datetimes DATETIME NOT NULL,
  order_id INT NOT NULL,
  user_id INT NOT NULL REFERENCES users(id),
  fine INT DEFAULT 0,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders(id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO date_fine VALUES(DEFAULT, curdate(), 6, 2);