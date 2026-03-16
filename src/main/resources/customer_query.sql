USE CAR_DATABASE;

-- xuat danh sach customer
SELECT * FROM Customer;

-- them customer
INSERT INTO Customer(id_customer, name_customer, phone_customer, adress_customer, email_customer) VALUES (?,?,?,?,?);


-- danh sách customer còn warranty
SELECT DISTINCT  -- chiếu các cột chính cho chức năng
c.name_customer,
p.name_product,
v.VIN,
w.end_date
FROM Customer c 
-- kết lần lượt các bảng  customer -> invoice -> invoice_detail -> vehicle -> warranty, product 
-- điều kiện là trùng các giá trị quan hệ
JOIN Invoice i ON c.id_customer = i.id_customer -- lấy hóa đơn khách hàng
JOIN Invoice_Detail d ON i.id_invoice = d.id_invoice -- lấy các xe có trong hóa đơn
JOIN Vehicle v ON d.VIN = v.VIN -- lấy mã VIN của xe
JOIN Product p ON v.id_product = p.id_product -- lấy tên xe
JOIN Warranty w ON v.VIN = w.VIN -- lấy thời gian bảo hành của xe
WHERE w.end_date > CURDATE(); -- điều kiện

-- tìm khách hàng theo số điện thoại

SELECT * FROM Customer
WHERE phone_customer = ?;

-- tìm khách hàng theo tên

SELECT * FROM Customer
WHERE name_customer LIKE ?;

-- thay đổi thông tin khách hàng
UPDATE Customer 
SET name_customer = ?, phone_customer = ?, adress_customer = ?
WHERE id_customer = ?;
-- xem lịch sử mua hàng

SELECT 
c.name_customer,
i.id_invoice,
i.date_invoice,
i.total_amount_invoice,
i.payment
FROM Customer c
JOIN Invoice i ON c.id_customer = i.id_customer
WHERE c.id_customer =?;
