USE CAR_DATABASE;

-- xem danh sach

SELECT * FROM Product;


-- them xe thêm xe mới thì dùng product và vehicle , thêm hãng xe cũ thì thêm mỗi vehicle
INSERT INTO Product(id_product, name_product, brand_product, color_product, price_product, production_year_product, status_product) VALUES(?,?,?,?,?,?,?);
INSERT INTO Vehicle(VIN, id_product) VALUES(?,?);

-- tim xe theo  brand, mã VIN, id
  -- brand
  SELECT * FROM Product
  WHERE brand_product LIKE ? ;
  
  -- id
  SELECT * FROM Product
  WHERE id_product = ? ;
  
  -- mã VIN
  SELECT * FROM Product
  JOIN Vehicle v ON id_product = v.id_product
  WHERE v.VIN = ?;
  
-- xoa xe

DELETE FROM Vehicle
WHERE VIN = ? ;

-- đếm xe theo id

SELECT COUNT(*) 
FROM Vehicle
WHERE id_product = ?;

-- xóa loại xe khi không còn chiếc nào(xóa cả id)
  
DELETE FROM Product
WHERE id_product = ?;
  
-- sửa thông tin xe Product
UPDATE Product 
SET name_product = ?, brand_product = ?, color_product = ? , price_product = ?, production_year_product = ?, status_product = ?
WHERE id_product = ?;

-- sửa mã VIN
UPDATE Vehicle 
SET VIN = ?
WHERE VIN = ?;

-- xe chưa bán 
SELECT 
p.*,
v.VIN
FROM Product p
JOIN Vehicle v ON p.id_product = v.id_product
WHERE v.VIN NOT IN (
    SELECT VIN
    FROM Invoice_Detail
);

-- check VIN trùng
SELECT 1 FROM Vehicle WHERE VIN = ?;

SELECT 1 FROM Invoice_Detail WHERE VIN = ?;

SELECT 1 FROM Invoice_Detail WHERE VIN = ?;
