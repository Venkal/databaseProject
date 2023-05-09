/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqldatabase.pkg366;
import java.sql.*;

/**
 *
 * @author carte
 */
public class DBConnect {
        
    static String jdbcURL = "jdbc:postgresql://localhost:5432/UniQuiz";
    static String username = "postgres";
    static String password = "July7163";
    
    private static Connection conn;
    
    public static void connectToDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(jdbcURL, username, password);
        }
        catch(ClassNotFoundException driverException){
            System.out.println("Cannot load the driver.");
            driverException.printStackTrace();
        }
        catch(SQLException sqlException){
            System.out.println("Cannot connect to database.");
            sqlException.printStackTrace();
        }
    }
    
    public static void closeConnection() throws SQLException {
        if (conn != null){
            conn.close();
        }
    }
    
    public static Connection getConnection() {
        if (conn == null) {
            connectToDatabase();
        }
        return conn;
    }
    
}
