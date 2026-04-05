# Entity Package

Package `entity` chua cac lop mo hinh du lieu trung tam cua he thong quan ly mua ban xe. Moi doi tuong trong package nay dai dien cho mot thuc the nghiep vu va duoc dung xuyen suot trong `dao`, `service` va `gui`.

## Vai tro chinh

- Dinh nghia cau truc du lieu cho tung doi tuong nghiep vu.
- Luu tru thuoc tinh, constructor, getter va setter.
- Dong vai tro cau noi giua tang truy cap du lieu va tang xu ly nghiep vu.

## Cac lop chinh

- `Account`: Thong tin tai khoan dang nhap, vai tro va lien ket nhan vien.
- `Customer`: Thong tin khach hang nhu ma, ten, so dien thoai, dia chi, email.
- `Employee`: Thong tin nhan vien, chuc vu, ngay vao lam va trang thai.
- `Product`: Mo ta thong tin chung cua xe/san pham.
- `Vehicle`: Ke thua `Product` va bo sung truong `VIN`.
- `Invoice`: Thong tin hoa don, nhan vien, khach hang, thoi gian va tong tien.
- `Invoice_Detail`: Chi tiet hoa don theo tung xe/VIN.
- `Payment`: Thong tin thanh toan cua hoa don.
- `Warranty`: Thong tin bao hanh, thoi gian bat dau-ket thuc va xe duoc bao hanh.

## Luu y thiet ke

- Day la cac lop model don gian, khong nen chua logic giao dien.
- Neu bo sung thuoc tinh moi trong entity, can dong bo lai `dao`, `service` va cac man hinh lien quan.
