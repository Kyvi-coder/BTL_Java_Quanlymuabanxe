package com.carmanagement.service;
import com.carmanagement.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
public class CarService {
    public boolean addCar(String name,String brand,String vin,int quantity){

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Product(name,brand,vin,quantity) VALUES(?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,brand);
            ps.setString(3,vin);
            ps.setInt(4,quantity);

            ps.executeUpdate();

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
