package com.carmanagement.dao;

import com.carmanagement.entity.Product;
import com.carmanagement.entity.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class productDAO {
    // lấy danh sách sản phẩm
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "select * from product";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Product product = new Product();
                product.setId_product(rs.getString("id_product"));
                product.setName_product(rs.getString("name_product"));
                product.setBrand_product(rs.getString("brand_product"));
                product.setColor_product(rs.getString("color_product"));
                product.setPrice_product(rs.getInt("price_product"));
                product.setProduction_year_product(rs.getInt("production_year_product"));
                products.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return products;
    }
    // thêm xem mới

    public Boolean addNewProductWithVehicle(Product  product, String VIN){
        String queryProduct = "INSERT INTO Product(id_product, name_product, brand_product, color_product, price_product, production_year_product) VALUES(?,?,?,?,?,?)";
        String queryVehicle = "INSERT INTO Vehicle(VIN, id_product) VALUES(?,?)";
        try(Connection conn = DBConnection.getConnection()){
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(queryProduct);
            stmt.setString(1, product.getId_product());
            stmt.setString(2, product.getName_product());
            stmt.setString(3, product.getBrand_product());
            stmt.setString(4, product.getColor_product());
            stmt.setInt(5, product.getPrice_product());
            stmt.setInt(6, product.getProduction_year_product());
            stmt.executeUpdate();

            PreparedStatement stmt2 = conn.prepareStatement(queryVehicle);
            stmt2.setString(1, VIN);
            stmt2.executeUpdate();

            conn.commit();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // thêm xe đã có id

    public boolean addVehicleToExistingProduct(String vin, String idProduct) {
        String sql = "INSERT INTO Vehicle(VIN, id_product) VALUES (?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            ps.setString(2, idProduct);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // check VIN trùng

    public boolean isVinExists(String vin) {
        String sql = "SELECT 1 FROM Vehicle WHERE VIN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, vin);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // có dữ liệu → true

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // tìm xe theo brand

    public List<Product> getProductByBrand(String brandProduct) {
        List<Product> products = new ArrayList<>();
        String queryProduct = "SELECT * FROM Product WHERE brand_product LIKE ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(queryProduct)){
            stmt.setString(1,"%" + brandProduct + "%");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setId_product(rs.getString("id_product"));
                product.setName_product(rs.getString("name_product"));
                product.setBrand_product(rs.getString("brand_product"));
                product.setColor_product(rs.getString("color_product"));
                product.setPrice_product(rs.getInt("price_product"));
                product.setProduction_year_product(rs.getInt("production_year_product"));
                products.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return products;
    }

    // tìm xe theo id

    public List<Product> getProductById(String IdProduct) {
        List<Product> products = new ArrayList<>();
        String queryProduct = "SELECT * FROM Product WHERE id_product = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(queryProduct)){
            stmt.setString(1,IdProduct);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Product product = new Product();
                product.setId_product(rs.getString("id_product"));
                product.setName_product(rs.getString("name_product"));
                product.setBrand_product(rs.getString("brand_product"));
                product.setColor_product(rs.getString("color_product"));
                product.setPrice_product(rs.getInt("price_product"));
                product.setProduction_year_product(rs.getInt("production_year_product"));
                products.add(product);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  products;
    }

    // tìm xe theo mã VIN
    public Product getProductByVIN(String vin) {
        Product product = new Product();
        String queryProduct = "SELECT * FROM Product\n" +
                "  JOIN Vehicle v ON id_product = v.id_product\n" +
                "  WHERE v.VIN = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(queryProduct)){
            stmt.setString(1, vin);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                product.setId_product(rs.getString("id_product"));
                product.setName_product(rs.getString("name_product"));
                product.setBrand_product(rs.getString("brand_product"));
                product.setColor_product(rs.getString("color_product"));
                product.setPrice_product(rs.getInt("price_product"));
                product.setProduction_year_product(rs.getInt("production_year_product"));
                return product;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // xóa xe



    // xóa cả loại xe khi không còn chiếc nào

    public boolean deleteVehicle(String vin) {
        String checkSold = "SELECT 1 FROM Invoice_Detail WHERE VIN = ?";
        String getProduct = "SELECT id_product FROM Vehicle WHERE VIN = ?";
        String deleteVehicle = "DELETE FROM Vehicle WHERE VIN = ?";
        String count = "SELECT COUNT(*) FROM Vehicle WHERE id_product = ?";
        String deleteProduct = "DELETE FROM Product WHERE id_product = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // 1. check xe đã bán chưa
            PreparedStatement psCheck = conn.prepareStatement(checkSold);
            psCheck.setString(1, vin);
            ResultSet rsCheck = psCheck.executeQuery();

            if (rsCheck.next()) {
                return false;
            }

            // 2. lấy id_product
            PreparedStatement psGet = conn.prepareStatement(getProduct);
            psGet.setString(1, vin);
            ResultSet rs = psGet.executeQuery();

            if (!rs.next()) return false;

            String idProduct = rs.getString("id_product");

            // 3. xóa Vehicle
            PreparedStatement psDelete = conn.prepareStatement(deleteVehicle);
            psDelete.setString(1, vin);
            psDelete.executeUpdate();

            // 4. kiểm tra còn xe không
            PreparedStatement psCount = conn.prepareStatement(count);
            psCount.setString(1, idProduct);
            ResultSet rsCount = psCount.executeQuery();

            if (rsCount.next() && rsCount.getInt(1) == 0) {
                // 5. xóa Product
                PreparedStatement psDeleteP = conn.prepareStatement(deleteProduct);
                psDeleteP.setString(1, idProduct);
                psDeleteP.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // sửa thông tin xe

    public Boolean updateProduct(Product product) {
        String query = "UPDATE Product \n" +
                "SET name_product = ?, brand_product = ?, color_product = ? , price_product = ?, production_year_product = ?\n" +
                "WHERE id_product = ?";
        try(Connection conn = DBConnection.getConnection();PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, product.getName_product());
            stmt.setString(2, product.getBrand_product());
            stmt.setString(3, product.getColor_product());
            stmt.setInt(4, product.getPrice_product());
            stmt.setInt(5, product.getProduction_year_product());
            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    // sửa mã VIN

    public Boolean updateVehicleByVIN(String oldVIN, String newVIN) {
        String query = "UPDATE Vehicle \n" +
                "SET VIN = ?\n" +
                "WHERE VIN = ?";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, oldVIN);
            stmt.setString(2, newVIN);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // lấy danh sách xe chưa bán

    public List<Product> getUnsoldVehicles(){
        List<Product> products = new ArrayList<>();
        String query = "p.*,\n" +
                "v.VIN\n" +
                "FROM Product p\n" +
                "JOIN Vehicle v ON p.id_product = v.id_product\n" +
                "WHERE v.VIN NOT IN (\n" +
                "    SELECT VIN\n" +
                "    FROM Invoice_Detail\n" +
                ")";
        try(Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query);ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                Product product = new Product();
                product.setId_product(rs.getString("id_product"));
                product.setName_product(rs.getString("name_product"));
                product.setBrand_product(rs.getString("brand_product"));
                product.setColor_product(rs.getString("color_product"));
                product.setPrice_product(rs.getInt("price_product"));
                product.setProduction_year_product(rs.getInt("production_year_product"));
                products.add(product);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  products;
    }
}
