USE CAR_DATABASE;


-- XEM DANH SÁCH HÓA ĐƠN
SELECT * FROM Invoice;

-- XEM CHI TIẾT HÓA ĐƠN
SELECT
i.id_invoice,
c.name_customer AS customer_name,
e.name_employee AS employee_name,
i.date_invoice,
p.name_product,
v.VIN,
d.quantity,
p.price_product,
(d.quantity * p.price_product) AS total_price
FROM Invoice i
JOIN Customer c ON i.id_customer = c.id_customer
JOIN Employee e ON i.id_employee = e.id_employee
JOIN Invoice_Detail d ON i.id_invoice = d.id_invoice
JOIN Vehicle v ON d.VIN = v.VIN
JOIN Product p ON v.id_product = p.id_product
WHERE i.id_invoice = ?;

-- TẠO HÓA ĐƠN

INSERT INTO Invoice(
id_invoice,
date_invoice,
total_amount_invoice,
payment_method,
id_customer,
id_employee
)
VALUES (?, ?, ?, ?, ?, ?);



-- THÊM CHI TIẾT HÓA ĐƠN
INSERT INTO Invoice_Detail(
id_invoice,
VIN,
quantity
)
VALUES (?, ?, ?);



-- TÌM HÓA ĐƠN THEO KHÁCH HÀNG

SELECT *
FROM Invoice
WHERE id_customer = ?;


-- TÌM HÓA ĐƠN THEO NHÂN VIÊN
SELECT * FROM Invoice
WHERE id_employee = ?;


-- TÍNH TỔNG TIỀN HÓA ĐƠN

SELECT
i.id_invoice,
SUM(d.quantity * p.price_product) AS total_amount
FROM Invoice i
JOIN Invoice_Detail d ON i.id_invoice = d.id_invoice
JOIN Vehicle v ON d.VIN = v.VIN
JOIN Product p ON v.id_product = p.id_product
WHERE i.id_invoice = ?
GROUP BY i.id_invoice;


