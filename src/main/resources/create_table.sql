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
name_employee VARCHAR(50) NOT NULL,
phone_employee VARCHAR(10) UNIQUE NOT NULL,
position_employee VARCHAR(20) NOT NULL,
hiredate_employee VARCHAR(15) NOT NULL
);

CREATE TABLE Product(
id_product VARCHAR(15) PRIMARY KEY,
price_product VARCHAR(15) NOT NULL,
name_product VARCHAR(30) NOT NULL,
brand_product VARCHAR(15) NOT NULL,
color_product VARCHAR(10) NOT NULL,
stock_quantity_product VARCHAR(10) NOT NULL,
VIN_product VARCHAR(20) UNIQUE NOT NULL,
prodution_year_product VARCHAR(5) NOT NULL
);

CREATE TABLE Invoice(
id_invoice VARCHAR(15) PRIMARY KEY,
date_invoice VARCHAR(20) NOT NULL,
total_amount_invoice VARCHAR(15) NOT NULL
);

CREATE TABLE Invoice_Detail(
price_detail VARCHAR(15) NOT NULL
);

CREATE TABLE Warranty(
id_warranty VARCHAR(15) PRIMARY KEY,
start_date_warranty VARCHAR(20) NOT NULL,
end_date_warranty VARCHAR(20) NOT NULL,
status_warranty VARCHAR(20) NOT NULL
);
