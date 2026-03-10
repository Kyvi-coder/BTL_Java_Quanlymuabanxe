USE CAR_DATABASE;

-- Customer
CREATE TABLE Customer(
id_customer VARCHAR(15) PRIMARY KEY,
name_customer VARCHAR(50) NOT NULL,
phone_customer VARCHAR(10) UNIQUE NOT NULL,
address_customer VARCHAR(100) NOT NULL,
email_customer VARCHAR(50) UNIQUE
);

-- Employee
CREATE TABLE Employee( 
id_employee VARCHAR(15) PRIMARY KEY, 
name_employee VARCHAR(50) NOT NULL, 
phone_employee VARCHAR(10) UNIQUE NOT NULL, 
position_employee VARCHAR(20) NOT NULL, 
hiredate_employee DATE NOT NULL 
);

-- Account
CREATE TABLE Account(
id_account VARCHAR(15) PRIMARY KEY,
username VARCHAR(50) UNIQUE NOT NULL,
password VARCHAR(100) NOT NULL,
role VARCHAR(20) NOT NULL,
id_employee VARCHAR(15) UNIQUE,

FOREIGN KEY (id_employee) REFERENCES Employee(id_employee)
);

-- Product
CREATE TABLE Product(
id_product VARCHAR(15) PRIMARY KEY,
price_product INT NOT NULL,
name_product VARCHAR(30) NOT NULL,
brand_product VARCHAR(15) NOT NULL,
color_product VARCHAR(10) NOT NULL,
stock_quantity_product INT NOT NULL,
VIN_product VARCHAR(20) UNIQUE NOT NULL,
production_year_product YEAR NOT NULL
);

-- Invoice
CREATE TABLE Invoice(
id_invoice VARCHAR(15) PRIMARY KEY,
date_invoice DATETIME NOT NULL,
total_amount_invoice INT NOT NULL,
id_customer VARCHAR(15),
id_employee VARCHAR(15),

FOREIGN KEY (id_customer) REFERENCES Customer(id_customer),
FOREIGN KEY (id_employee) REFERENCES Employee(id_employee)
);

-- Invoice_Detail
CREATE TABLE Invoice_Detail(
price_detail INT NOT NULL,
quantity INT NOT NULL,
id_invoice VARCHAR(15) NOT NULL,
id_product VARCHAR(15) NOT NULL,

PRIMARY KEY (id_invoice, id_product),

FOREIGN KEY (id_invoice) REFERENCES Invoice(id_invoice),
FOREIGN KEY (id_product) REFERENCES Product(id_product)
);

-- Payment
CREATE TABLE Payment(
id_payment VARCHAR(15) PRIMARY KEY,
id_invoice VARCHAR(15) NOT NULL,
payment_date DATETIME NOT NULL,
payment_method VARCHAR(20) NOT NULL,
amount DECIMAL(15,2) NOT NULL,

FOREIGN KEY (id_invoice) REFERENCES Invoice(id_invoice)
);

-- Warranty
CREATE TABLE Warranty(
id_warranty VARCHAR(15) PRIMARY KEY,
id_product VARCHAR(15),
start_date_warranty DATE NOT NULL,
end_date_warranty DATE NOT NULL,
status_warranty VARCHAR(20) NOT NULL,

FOREIGN KEY (id_product) REFERENCES Product(id_product)
);