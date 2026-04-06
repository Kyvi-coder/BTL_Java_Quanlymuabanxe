# Huong dan cai dat va khoi chay project

## 1. Tong quan

Day la project Java Swing su dung:

- Java 17
- Maven
- MySQL

Class chay chinh cua ung dung:

```text
com.carmanagement.Main
```

Khi chay len, man hinh dau tien la `LoginFrame`.

## 2. Ung dung can cai dat

De chay duoc project, ban nen cai cac phan mem sau:

### Bat buoc

1. `JDK 17`
2. `MySQL Server 8.x`

### Nen cai them

1. `IntelliJ IDEA` de mo va chay project de dang
2. `MySQL Workbench` de import file SQL va quan ly database
3. `Apache Maven 3.9+`

Luu y:

- Repo hien tai **khong co** file `mvnw` / `mvnw.cmd`, vi vay neu muon build bang terminal thi ban can cai Maven tren may.
- Neu chi chay bang IntelliJ, ban van nen cai Maven de tranh loi resolve dependency.

## 3. Cau hinh moi truong

### Kiem tra Java

Mo terminal va chay:

```powershell
java -version
javac -version
```

Project yeu cau Java 17.

### Kiem tra Maven

```powershell
mvn -version
```

Neu may bao khong tim thay lenh `mvn` thi ban chua cai Maven hoac chua them vao `PATH`.

### Kiem tra MySQL

Dam bao MySQL Server dang chay va ban dang nho:

- host: `localhost`
- port: `3306`
- username: `root`
- password MySQL cua ban

## 4. Tao database

Project dang ket noi den database:

```text
car_database
```

File ket noi hien tai:

[`src/main/java/com/carmanagement/dao/DBConnection.java`](/C:/Users/trank/IdeaProjects/Quan_ly_mua_ban_xe/src/main/java/com/carmanagement/dao/DBConnection.java)

Gia tri dang hard-code trong source:

```java
String url = "jdbc:mysql://localhost:3306/car_database";
String user = "root";
String password = "3962";
```

Neu may ban dung mat khau MySQL khac `3962`, ban phai sua lai file tren truoc khi chay.

## 5. Import du lieu SQL

Trong thu muc:

[`src/main/resources`](/C:/Users/trank/IdeaProjects/Quan_ly_mua_ban_xe/src/main/resources)

Hay chay cac file SQL theo thu tu:

1. `car_database.sql`
2. `create_table.sql`
3. `insert_data.sql`

Co the chay them file sau neu can:

4. `create_audit_log.sql`

Luu y:

- Trong `create_table.sql` da co lenh tao bang `Audit_Log`, nen `create_audit_log.sql` chi la file bo sung.
- Ten database trong SQL dang viet la `CAR_DATABASE`. MySQL thuong khong phan biet hoa thuong tren Windows, nhung ban nen giu dong nhat ten `car_database`.

### Cach import bang MySQL Workbench

1. Mo MySQL Workbench va ket noi vao local server.
2. Tao tab query moi.
3. Copy noi dung tung file SQL vao va chay lan luot theo dung thu tu.

### Cach import bang command line

```powershell
mysql -u root -p < src\main\resources\car_database.sql
mysql -u root -p car_database < src\main\resources\create_table.sql
mysql -u root -p car_database < src\main\resources\insert_data.sql
```

## 6. Tai khoan dang nhap mau

### Tai khoan test hard-code trong giao dien

Ban co the dang nhap ngay bang:

- username: `admin`
- password: `123`

Tai khoan nay duoc xu ly truc tiep trong:

[`src/main/java/com/carmanagement/gui/LoginFrame.java`](/C:/Users/trank/IdeaProjects/Quan_ly_mua_ban_xe/src/main/java/com/carmanagement/gui/LoginFrame.java)

### Tai khoan du lieu mau trong database

Sau khi chay `insert_data.sql`, ban co the dung:

- `abctrung / trung`
- `salehung / hung`
- `managernam / nam`

## 7. Cach chay project bang IntelliJ IDEA

1. Mo IntelliJ IDEA.
2. Chon `Open` va mo thu muc project `Quan_ly_mua_ban_xe`.
3. Cho IntelliJ tai dependency Maven.
4. Kiem tra SDK cua project la `JDK 17`.
5. Mo file:

[`src/main/java/com/carmanagement/Main.java`](/C:/Users/trank/IdeaProjects/Quan_ly_mua_ban_xe/src/main/java/com/carmanagement/Main.java)

6. Bam `Run Main.main()`.

Neu mo dung database va cau hinh dung password MySQL, giao dien dang nhap se hien ra.

## 8. Cach chay bang terminal

### Cach 1: compile bang Maven

```powershell
mvn clean compile
```

### Cach 2: chay bang Maven Exec Plugin

Neu may da cai Maven, co the chay:

```powershell
mvn org.codehaus.mojo:exec-maven-plugin:3.1.0:java "-Dexec.mainClass=com.carmanagement.Main"
```

Luu y:

- Repo chua khai bao san plugin `exec-maven-plugin` trong `pom.xml`, nen lenh tren goi plugin truc tiep tu Maven.
- Neu dependency chua duoc tai ve, Maven can co internet trong lan build dau tien.

## 9. Loi thuong gap

### Loi khong ket noi duoc MySQL

Kiem tra lai:

- MySQL Server da bat chua
- Da tao database `car_database` chua
- Da import bang chua
- Password trong `DBConnection.java` co trung voi MySQL that khong

### Loi `mvn is not recognized`

Ban can:

1. Cai Apache Maven
2. Them Maven vao bien moi truong `PATH`
3. Mo lai terminal

### Loi dang nhap that bai

Hay thu:

- `admin / 123`
- Hoac cac tai khoan mau trong database sau khi da chay `insert_data.sql`

## 10. De xuat

De su dung on dinh hon, nen sua file ket noi database de:

- doc `url`, `username`, `password` tu file config
- khong hard-code mat khau MySQL trong source code

Neu ban muon, toi co the lam tiep cho ban:

1. file `README.md` hoan chinh hon
2. file cau hinh `application.properties` / `db.properties`
3. tu dong hoa tao database va huong dan import ngan gon hon
