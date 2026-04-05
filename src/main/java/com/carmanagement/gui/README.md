# GUI Package

Package `gui` chua cac cua so (`JFrame`) va thanh phan giao dien (`JPanel`) cua ung dung Swing. Day la tang tuong tac truc tiep voi nguoi dung, hien thi du lieu va gui yeu cau xu ly den package `service`.

## Vai tro chinh

- Hien thi man hinh dang nhap, trang chu va cac chuc nang quan ly.
- Xu ly su kien tu nut bam, bang du lieu, o tim kiem va form nhap lieu.
- Goi cac service de tai du lieu, tim kiem, them, sua va cap nhat giao dien.

## Cac lop chinh

- `LoginFrame`: Man hinh dang nhap vao he thong.
- `HomeFrame`, `MainFrame`, `MainEmployee`, `DashboardFrame`, `CarManagementFrame`: Cac cua so chinh dieu huong nguoi dung theo vai tro va module.
- `DashboardPanel`: Hien thi tong quan he thong.
- `CustomerPanel`: Quan ly danh sach va tim kiem khach hang.
- `EmployeePanel`: Quan ly nhan vien, tim kiem va cap nhat thong tin.
- `InventoryPanel`: Quan ly xe trong kho, tim kiem va lam moi du lieu.
- `SalesPanel`: Xu ly ban hang va tao don cho nhan vien hien tai.
- `RevenuePanel`: Hien thi thong ke doanh thu.
- `WarrantyPanel`: Tra cuu va quan ly bao hanh theo VIN.
- `BackgroundPanel`: Thanh phan phu tro ve hien thi nen giao dien.

## Nguyen tac su dung

- GUI chi nen xu ly hien thi va dieu huong thao tac nguoi dung.
- Logic nghiep vu can dua xuong package `service`.
- Khong truy cap truc tiep CSDL tu package nay.
