USE CAR_DATABASE;
USE CAR_DATABASE;


-- XEM DANH SÁCH HÓA ĐƠN
SELECT * FROM Invoice;

-- XEM CHI TIẾT HÓA ĐƠN
SELECT
    i.InvoiceID,
    c.Name AS CustomerName,
    e.Name AS EmployeeName,
    i.Date,
    p.ProductName,
    id.Quantity,
    id.Price
FROM Invoice i
         JOIN Customer c ON i.CustomerID = c.CustomerID
         JOIN Employee e ON i.EmployeeID = e.EmployeeID
         JOIN InvoiceDetail id ON i.InvoiceID = id.InvoiceID
         JOIN Product p ON id.ProductID = p.ProductID;


-- TẠO HÓA ĐƠN
INSERT INTO Invoice(CustomerID, EmployeeID, Date)
VALUES (1, 1, GETDATE());


-- THÊM CHI TIẾT HÓA ĐƠN
INSERT INTO InvoiceDetail(InvoiceID, ProductID, Quantity, Price)
VALUES (1, 1, 2, 500000000);


-- TÌM HÓA ĐƠN THEO KHÁCH HÀNG
SELECT * FROM Invoice
WHERE CustomerID = 1;


-- TÌM HÓA ĐƠN THEO NHÂN VIÊN
SELECT * FROM Invoice
WHERE EmployeeID = 1;

-- TÍNH TỔNG TIỀN HÓA ĐƠN
SELECT
    InvoiceID,
    SUM(Quantity * Price) AS TotalAmount
FROM InvoiceDetail
GROUP BY InvoiceID;

