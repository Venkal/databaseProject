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
    static boolean pass = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //Begining Tests
//            DBConnect.connectToDatabase();
//            System.out.println("Connection Works!");
//            String test = "select * from Answer";
//            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(test);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Answer answer = new Answer();
//                answer.answer_id = rs.getInt("answer_id");
//                System.out.println(answer.answer_id);
//            }
//            
//            DBConnect.closeConnection();
//            

            Scanner scan = new Scanner(System.in);
            String selection = "";
            String username = "";
            String password = "";
            Users USER = new Users(); //The logged in user
            do {
                //Username
                System.out.println("Welcome! What would you like to do?"
                        + "\n   Login"
                        + "\n   Create New Account"
                        + "\n   Exit Program"
                        + "\n\nPlease type in \"Login\" or \"New\" or \"Exit\" to continue");
                selection = scan.nextLine();
                if (selection.toLowerCase().equals("login")) {
                    USER = userLogin(username, password, scan, USER, pass);
                }

                //Create New User
                if (selection.toLowerCase().equals("new")) {
                    addUser(username, password, scan);

                }
                try{
                    //IF LOGIN WENT THROUGHUSER.getUsername()
                    if (USER.getUsername()!=null) {
                        do {
                        //Ask for what they want to do next
                        System.out.println("Welcome " + USER.getUsername() + ", Please make a selection: "
                                + "\n     list - list the availible tests "
                                + "\n     select [testname] - take a test "
                                + "\n     Exit - quit the application");
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
                                System.out.println(rTest.getTest_ID() + " " + rTest.getTest_name());
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
                        System.out.println("\n");
                    } while (!selection.toLowerCase().equals("exit"));
                    //Close Connection after Done
                    DBConnect.closeConnection();
                    //Logout User
                    USER.setUsername(null);
                    USER.setPassword(null);
                    selection = "";
                    }
                }
                catch(NullPointerException npe){
                    System.out.println("Problem occured with Username and Password, Please try again");
                }
                
                System.out.println("\n");
            } while (!selection.toLowerCase().equals("exit"));

            DBConnect.closeConnection();
        } catch (Exception e) {
            System.out.println("EXCEPTION");
            System.out.println(e);
        }

    }

    public void takeTest(String TestName) throws SQLException {

        DBConnect.connectToDatabase();

        String testQuery = "SELECT * FROM test WHERE test_name like " + TestName;
        PreparedStatement stmt = DBConnect.getConnection().prepareStatement(testQuery);
        ResultSet testResult = stmt.executeQuery();
        Test test = new Test();

        while (testResult.next()) {
            test.setTest_name(testResult.getString("test_name"));
        }

        DBConnect.closeConnection();
    }

    public static void addUser(String username, String password, Scanner scan) throws SQLException {
        System.out.println("Please enter your Username");
        username = scan.nextLine();
        System.out.println("Please enter the password for the User: " + username);
        password = scan.nextLine();
        //Connect to Database and Create user
        DBConnect.connectToDatabase();
        String userInsert = "INSERT INTO users (username, pass) VALUES (?, ?)";
        PreparedStatement pst = DBConnect.getConnection().prepareStatement(userInsert);
        pst.setString(1, username);
        pst.setString(2, password);
        try {
            pst.executeQuery();
        } catch (Exception e) {
            System.out.println("Now Please Login with those credentials");
        }
        //Used to Debug and Make sure Username and Password were stored in the Database
//                    String userList = "SELECT * FROM users WHERE username like ? AND pass like ?";
//                    PreparedStatement getUser = DBConnect.getConnection().prepareStatement(userList);
//                    pst.setString(1, username);
//                    pst.setString(2,password);
//                    ResultSet userResult = pst.executeQuery();
//                    while(userResult.next()){
//                        Users user = new Users();
//                        user.setPassword(userResult.getString("pass"));
//                        user.setUsername(userResult.getString("username"));
//                        user.setUser_ID(userResult.getInt("user_id"));
//                        USER = user;
//                        System.out.println(user.getUsername() + " " + user.getPassword());
//                    }

    }

    public static Users userLogin(String username, String password, Scanner scan, Users USER, boolean pass) throws SQLException, Exception {
        System.out.println("Please enter your Username");
        username = scan.nextLine();
        System.out.println("Please enter the password for the User: " + username);
        password = scan.nextLine();

        //Get Users/Passwords from pgAdmin
        DBConnect.connectToDatabase();
        String userList = "SELECT * FROM users WHERE username like ? AND pass like ?";
        PreparedStatement pst = DBConnect.getConnection().prepareStatement(userList);
        pst.setString(1, username);
        pst.setString(2, password);
        try{
        ResultSet userResult = pst.executeQuery();
        while (userResult.next()) {
            Users user = new Users();
            user.setPassword(userResult.getString("pass"));
            user.setUsername(userResult.getString("username"));
            user.setUser_ID(userResult.getInt("user_id"));
            USER = user;
        }
        if (USER.getUsername() == null && USER.getPassword() == null) {
            throw new SQLException();
        }
        pass = true;
        return USER;
        } catch(SQLException sqle){
            System.out.println("Username and Password may be Incorrect");
        }
        return null;
    }

}
