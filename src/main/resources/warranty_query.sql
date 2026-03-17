USE CAR_DATABASE;
    USE CAR_DATABASE;

-- DANH SÁCH XE CÒN BẢO HÀNH
SELECT * FROM Warranty
WHERE EndDate >= GETDATE();

-- DANH SÁCH XE HẾT BẢO HÀNH
SELECT * FROM Warranty
WHERE EndDate < GETDATE();

-- XEM BẢO HÀNH THEO XE
SELECT
    w.WarrantyID,
    p.ProductName,
    w.StartDate,
    w.EndDate
FROM Warranty w
         JOIN Product p ON w.ProductID = p.ProductID
WHERE p.ProductID = 1;

-- XEM BẢO HÀNH THEO KHÁCH HÀNG
SELECT
    c.Name AS CustomerName,
    p.ProductName,
    w.StartDate,
    w.EndDate
FROM Warranty w
         JOIN Product p ON w.ProductID = p.ProductID
         JOIN InvoiceDetail id ON p.ProductID = id.ProductID
         JOIN Invoice i ON id.InvoiceID = i.InvoiceID
         JOIN Customer c ON i.CustomerID = c.CustomerID
WHERE c.CustomerID = 1;

-- THÊM BẢO HÀNH
INSERT INTO Warranty(ProductID, StartDate, EndDate)
VALUES (1, GETDATE(), DATEADD(YEAR, 2, GETDATE()));

