/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqldatabase.pkg366;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;

/**
 *
 * @author carte
 */
public class SqlDataBase366 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            DBConnect.connectToDatabase();
            System.out.println("Connection Works!");
            String test = "select * from Answer";
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(test);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Answer answer = new Answer();
                answer.answer_id = rs.getInt("answer_id");
                System.out.println(answer.answer_id);
            }
            
            DBConnect.closeConnection();
            

            Scanner scan = new Scanner(System.in);
            String selection = "";
            String username = "";
            do {
                //Username
                System.out.println("Please enter a Username");
                username = scan.nextLine();
                
                //Ask for what they want to do next
                System.out.println("Welcome " + username + ", Please make a selection: "
                        + "\n     list - list the availible tests "
                        + "\n     select [testname] - take a test "
                        + "\n     q - quit the application");
                selection = scan.nextLine();
                
                //For a List, List the test's available
                if (selection.toLowerCase().equals("list")) {
                    System.out.println("list");
                    DBConnect.connectToDatabase();
                    String testList = "select * from Test";
                    PreparedStatement pst = DBConnect.getConnection().prepareStatement(testList);
                    ResultSet result = pst.executeQuery();
                    
                    //while tests are available
                    while (result.next()) {
                        Test rTest = new Test();
                        rTest.setTest_ID(result.getInt("test_ID"));
                        rTest.setTest_name(result.getString("test_name"));
                        System.out.println(rTest.getTest_ID() +" " + rTest.getTest_name());
                    }
                    DBConnect.closeConnection();

                }
                //If its select
                if (selection.toLowerCase().contains("select")) {
                    System.out.println("Select");
                    String[] entered = selection.split(" ");
                    String title = "";
                    for (String entered1 : entered) {
                        if (!entered1.isEmpty() & !entered1.toLowerCase().equals("select")) {
                            title += entered1 + " ";
                            
                        }
                    }
                    title.trim();
                    System.out.println(title);
                }
                System.out.println("Press enter to continue");
                scan.nextLine();
            } while (!selection.equals("q"));

             DBConnect.closeConnection();
        } catch (Exception e) {
            System.out.println("EXCEPTION");
            System.out.println(e);
        }

    }
    
    public void takeTest(String TestName) throws SQLException{
        
        DBConnect.connectToDatabase();
        
        String testQuery = "SELECT * FROM test WHERE test_name like " + TestName;
        PreparedStatement stmt = DBConnect.getConnection().prepareStatement(testQuery);
        ResultSet testResult = stmt.executeQuery();
        Test test = new Test();
        
        while(testResult.next()){
            test.setTest_name(testResult.getString("test_name"));
        }
        
        DBConnect.closeConnection();
    }
}
