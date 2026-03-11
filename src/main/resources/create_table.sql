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
name_product VARCHAR(30) NOT NULL,
brand_product VARCHAR(15) NOT NULL,
color_product VARCHAR(10) NOT NULL,
price_product INT NOT NULL,
production_year_product YEAR NOT NULL
);


-- Vehicle
CREATE TABLE Vehicle(
VIN VARCHAR(20) PRIMARY KEY,
id_product VARCHAR(15) NOT NULL,

FOREIGN KEY (id_product) REFERENCES Product(id_product)
);


-- Invoice
CREATE TABLE Invoice(
id_invoice VARCHAR(15) PRIMARY KEY,
date_invoice DATETIME NOT NULL,
total_amount_invoice INT NOT NULL,
payment_method VARCHAR(20),
id_customer VARCHAR(15),
id_employee VARCHAR(15),

FOREIGN KEY (id_customer) REFERENCES Customer(id_customer),
FOREIGN KEY (id_employee) REFERENCES Employee(id_employee)
);

-- Invoice_Detail
CREATE TABLE Invoice_Detail(
id_invoice VARCHAR(15),
VIN VARCHAR(20),
quantity INT NOT NULL,

PRIMARY KEY (id_invoice, VIN),

FOREIGN KEY (id_invoice) REFERENCES Invoice(id_invoice),
FOREIGN KEY (VIN) REFERENCES Vehicle(VIN)
);

-- Payment
CREATE TABLE Payment(
id_payment VARCHAR(15) PRIMARY KEY,
id_invoice VARCHAR(15) NOT NULL,
payment_date DATETIME NOT NULL,
payment_method VARCHAR(20) NOT NULL,
amount INT NOT NULL,
FOREIGN KEY (id_invoice) REFERENCES Invoice(id_invoice)
);

-- Warranty
CREATE TABLE Warranty(
id_warranty VARCHAR(15) PRIMARY KEY,
VIN VARCHAR(20) NOT NULL,
start_date DATE NOT NULL,
end_date DATE NOT NULL,

FOREIGN KEY (VIN) REFERENCES Vehicle(VIN)
);