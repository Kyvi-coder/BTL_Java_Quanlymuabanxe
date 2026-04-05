# Service Package

Package `service` la tang xu ly nghiep vu nam giua `gui` va `dao`. Cac lop trong package nay nhan du lieu tu giao dien, kiem tra hoac to chuc luong xu ly, sau do goi `dao` de thao tac voi co so du lieu.

## Vai tro chinh

- Dong goi logic nghiep vu de giao dien de su dung.
- Kiem tra va chuan hoa dau vao truoc khi luu xuong CSDL.
- Tong hop du lieu tu nhieu DAO cho cac chuc nang nhu dang nhap, ban hang, bao hanh va thong ke.

## Cac lop chinh

- `LoginService`: Xac thuc dang nhap va lay nhan vien tu tai khoan.
- `AccountService`: Xu ly nghiep vu lien quan den tai khoan va thong tin nhan vien dang nhap.
- `CustomerService`: Lay danh sach, tim kiem, cap nhat va xoa khach hang.
- `EmployeeService`: Tim kiem, them moi va cap nhat nhan vien.
- `InventoryService`: Lay danh sach xe, tim kiem, sap xep va them xe vao kho.
- `SaleService`: Tao don ban hang day du va tra ve ket qua xu ly.
- `RevenueService`: Tinh tong doanh thu va tong so xe da ban theo thang/nam.
- `WarrantyService`: Lay danh sach bao hanh, tra cuu theo VIN, them moi va xoa bao hanh het han.

## Quan he voi package khac

- Nhan yeu cau tu package `gui`.
- Goi package `dao` de thao tac du lieu.
- Su dung cac model trong package `entity` de trao doi du lieu giua cac tang.
