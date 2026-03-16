package com.carmanagement.service;
import com.carmanagement.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
public class EmployeeService {
    public boolean addEmployee(String name,String phone,String position){

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Employee(name,phone,position) VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,name);
            ps.setString(2,phone);
            ps.setString(3,position);

            ps.executeUpdate();

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
