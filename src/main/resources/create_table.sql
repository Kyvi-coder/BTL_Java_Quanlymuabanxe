USE CAR_DATABASE;
GO

CREATE TABLE Customer(
id_customer VARCHAR(15) PRIMARY KEY,
name_customer NVARCHAR(50) NOT NULL,
phone_customer VARCHAR(10) UNIQUE NOT NULL,
address_customer NVARCHAR(100) NOT NULL,
email_customer VARCHAR(50) UNIQUE
);

CREATE TABLE Employee(
id_employee VARCHAR(15) PRIMARY KEY,

);

CREATE TABLE Product(
id_product VARCHAR(15) PRIMARY KEY,

);

