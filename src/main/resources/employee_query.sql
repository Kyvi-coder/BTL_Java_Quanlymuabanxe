USE CAR_DATABASE;

-- xuất danh sách nhân viên

SELECT * FROM Employee;


-- xuất danh sách nhân viên theo từng chức vụ

   -- Quản lý 
SELECT * FROM Employee
WHERE position_employee = "manager";

   -- Nhân viên

SELECT * FROM Employee
WHERE position_employee = "staff";

-- thêm nhân viên

INSERT INTO Employee(name_employee, phone_employee, position_employee, hiredate_employee, status_employee) VALUES(?,?,?,?,?);


-- xóa nhân viên

UPDATE Employee
SET status_employee =?
WHERE id_employee = ?; 

-- thay đổi thông tin nhân viên

UPDATE Employee
SET name_employee = ?, phone_employee = ?, position_employee = ?, hiredate_employee = ?
WHERE id_employee = ?;

-- tìm  nhân viên sđt, tên, quê 

  -- tìm theo số điện thoại
  SELECT * FROM Employee
  WHERE phone_employee = ?;
  
  -- tìm theo tên 
  
  SELECT * FROM Employee 
  WHERE name_employee = ? ;
  
  -- nhân viên đang làm
  SELECT *
FROM Employee
WHERE status_employee = 'active';

-- nhân viên đã nghỉ

SELECT *
FROM Employee
WHERE status_employee = 'inactive';