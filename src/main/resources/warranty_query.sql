USE CAR_DATABASE;

-- DANH SÁCH XE CÒN BẢO HÀNH
SELECT * FROM Warranty
WHERE end_date >= CURDATE();

-- DANH SÁCH XE HẾT BẢO HÀNH
SELECT * FROM Warranty
WHERE end_date < CURDATE();

-- XEM BẢO HÀNH THEO XE
SELECT
w.id_warranty,
p.name_product,
v.VIN,
w.start_date,
w.end_date
FROM Warranty w
JOIN Vehicle v ON w.VIN = v.VIN
JOIN Product p ON v.id_product = p.id_product
WHERE v.VIN = ?;

-- XEM BẢO HÀNH THEO KHÁCH HÀNG
SELECT
c.name_customer,
p.name_product,
v.VIN,
w.start_date,
w.end_date
FROM Warranty w
JOIN Vehicle v ON w.VIN = v.VIN
JOIN Product p ON v.id_product = p.id_product
JOIN Invoice_Detail d ON v.VIN = d.VIN
JOIN Invoice i ON d.id_invoice = i.id_invoice
JOIN Customer c ON i.id_customer = c.id_customer
WHERE c.id_customer = ?;

-- THÊM BẢO HÀNH
INSERT INTO Warranty(
id_warranty,
VIN,
start_date,
end_date
)
VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 2 YEAR));

