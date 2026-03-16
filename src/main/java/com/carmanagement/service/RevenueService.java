package com.carmanagement.service;
import com.carmanagement.database.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
public class RevenueService {
    public double getRevenue(){

        double total = 0;

        try{

            Connection conn = DBConnection.getConnection();

            String sql = "SELECT SUM(total) FROM Invoice";

            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(sql);

            if(rs.next()){
                total = rs.getDouble(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return total;
    }
}
