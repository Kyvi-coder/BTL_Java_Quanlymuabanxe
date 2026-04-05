# DAO Package

 dao chịu trách nhiệm truy cập dữ liệu va làm việc trực tiếp với database. Lớp service sẽ thực hiện các chức năng CRUD, searching thông qua dao.

- kết nối database thông qua `DBConnection`.
- Thực hiện truy vấn từng nghiệp vụ như tài khoản, khách hàng, nhân viên, hóa đơn, sản phẩm và bảo hành.
- chuyển đổi dữ liệu truy vấn thành các đối tượng trong packpage`entity`.

# Các lớp chính

- `DBConnection`: Tạo kết nối database.
- `accountDAO`: kiểm tra đăng nhập bằng tài khoản và mật khẩu của từng nhân viên.
- `customerDAO`: lấy danh sách, tìm kiếm, thêm và cập nhật khách hàng.
- `employeeDAO`: lấy danh sách, lọc theo vai trò/ trạng thái, thêm và cập nhật nhân viên.
- `invoiceDAO`: đọc hóa đơn, kiểm tra xe đã bán chưa và tạo đơn hàng đầy đủ.
- `productDAO`: quản lý thông tin xe, VIN, thêm xe.
- `warrantyDAO`: quản lý dữ liệu bảo hành, tra cứu theo VIN và xóa bảo hành hết hạn.

