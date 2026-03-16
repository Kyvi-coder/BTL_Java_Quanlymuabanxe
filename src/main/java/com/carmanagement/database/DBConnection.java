package com.carmanagement.database;
import java.sql.Connection;
import java.sql.DriverManager;
public class DBConnection {
    public static Connection getConnection(){

        Connection conn = null;

        try{

            String url = "jdbc:mysql://localhost:3306/car_management";
            String user = "root";
            String password = "123456";

            conn = DriverManager.getConnection(url,user,password);

        }catch(Exception e){
            e.printStackTrace();
        }

        return conn;
    }
}
