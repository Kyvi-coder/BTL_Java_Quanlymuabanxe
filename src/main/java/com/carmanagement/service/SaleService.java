package com.carmanagement.service;
import com.carmanagement.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SaleService {
    public boolean createContract(
            String customerName,
            String phone,
            String address,
            String carName,
            int quantity,
            String paymentMethod
    ){

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Invoice(customer_name,phone,address,car_name,quantity,payment_method) VALUES(?,?,?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,customerName);
            ps.setString(2,phone);
            ps.setString(3,address);
            ps.setString(4,carName);
            ps.setInt(5,quantity);
            ps.setString(6,paymentMethod);

            ps.executeUpdate();

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
