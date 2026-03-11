USE CAR_DATABASE;

INSERT INTO Customer VALUES
("TTHTH01","Tran Thanh Hai","0372134563","Thanh Hoa","thanhhai123@gmail.com"),
("TVTND02","Tran Van Trung","0123454326","Nam Dinh","thuongnormal@gmail.com"),
("LTLANB03","Ly Thi Lan Anh","0865792341","Ninh Binh","lananhne@gmail.com");

SELECT * FROM Customer;

INSERT INTO Employee VALUES 
("NVSTVT01","Tran Van Trung","0369125478","Sale","2019-12-23"),
('NVSPVH02','Pham Van Hung','0922222222','Sales','2023-05-15'),
('NVMLVN03','Le Van Nam','0911111111','Manager','2023-01-10');

SELECT * FROM Employee;

INSERT INTO Account() VALUES
("ACTVT01","abctrung","trung","Sale","NVSTVT01"),
("ACPVH02","salehung","hung","Sale","NVSPVH02"),
("ACLVN03","managernam","nam","Manager","NVMLVN03");

SELECT * FROM Account;

INSERT INTO Product VALUES 
('P001', 'Toyota Vios', 'Toyota', 'White', 520000000, 5, 'VIN000000001', 2023),
('P002', 'Honda City', 'Honda', 'Black', 540000000, 3, 'VIN000000002', 2022),
('P003', 'Hyundai Accent', 'Hyundai', 'Red', 450000000, 4, 'VIN000000003', 2023),
('P004', 'Kia K3', 'Kia', 'Grey', 560000000, 2, 'VIN000000004', 2024),
('P005', 'Mazda 3', 'Mazda', 'White', 620000000, 3, 'VIN000000005', 2023),
('P006', 'Toyota Corolla Cross', 'Toyota', 'Black', 820000000, 2, 'VIN000000006', 2024),
('P007', 'Honda CRV', 'Honda', 'White', 1050000000, 1, 'VIN000000007', 2024),
('P008', 'Hyundai Tucson', 'Hyundai', 'Blue', 890000000, 2, 'VIN000000008', 2023),
('P009', 'Kia Seltos', 'Kia', 'Red', 690000000, 4, 'VIN000000009', 2023),
('P010', 'Mazda CX5', 'Mazda', 'Grey', 920000000, 2, 'VIN000000010', 2024);

SELECT * FROM Product;

INSERT INTO Invoice VALUES
("I001","2026-03-11 12:30:45",620000000,"LTLANB03","NVMLVN03");

SELECT * FROM Invoice;

INSERT INTO Invoice_Detail VALUES
(620000000,1,"I001","P005");

SELECT * FROM Invoice_Detail;
