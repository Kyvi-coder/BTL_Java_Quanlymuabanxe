package com.carmanagement.service;
import com.carmanagement.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
public class CustomerService {
    public boolean addCustomer(String name,String phone,String address){

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Customer(name,phone,address) VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,phone);
            ps.setString(3,address);

            ps.executeUpdate();

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
