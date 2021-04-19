DROP TABLE IF EXISTS CUSTOMER;

CREATE TABLE CUSTOMER (
id INT AUTO_INCREMENT  PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL
);


CREATE TABLE ACCOUNT (
id INT AUTO_INCREMENT  PRIMARY KEY,
name VARCHAR(50) NOT NULL,
iban VARCHAR(50) NOT NULL,
type VARCHAR(50) NOT NULL,
is_locked BOOLEAN DEFAULT FALSE,
balance INT NOT NULL,
customer_id INT NOT NULL
);

CREATE TABLE TRANSACTION_HISTORY (
id INT AUTO_INCREMENT  PRIMARY KEY,
account_id INT NOT NULL,
type VARCHAR(50) NOT NULL,
amount INT,
created_at TIMESTAMP NOT NULL
);
