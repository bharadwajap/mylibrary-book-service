DROP TABLE IF EXISTS books;
 
CREATE TABLE books (
  isbn VARCHAR(250) PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL,
  volume INT(10) NOT NULL
);
 
INSERT INTO books (isbn, title, author, volume) VALUES
  ('27534857','Headfirst Java', 'Kathy Sierra', 1),
  ('835734895','Five Point Someone', 'Chetan Bhagat', 1),
  ('458934580','Finance Holdings', 'Alakija', 2);