package com.carmanagement.service;
import com.carmanagement.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
public class WarrantyService {
    public boolean addWarranty(String contractID,String start,String end){

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "INSERT INTO Warranty(contract_id,start_date,end_date) VALUES(?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,contractID);
            ps.setString(2,start);
            ps.setString(3,end);

            ps.executeUpdate();

            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }
}
