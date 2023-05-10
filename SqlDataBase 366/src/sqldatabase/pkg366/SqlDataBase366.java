/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqldatabase.pkg366;

import java.sql.SQLException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.postgresql.util.PSQLException;

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
                        + "\n   Login - Login"
                        + "\n   New - Create New Account"
                        + "\n   Exit - Exit Program"
                        + "\n\nPlease type in \"Login\" or \"New\" or \"Exit\" to continue");
                selection = scan.nextLine();
                if (selection.toLowerCase().equals("login")) {
                    USER = userLogin(username, password, scan, USER, pass);
                }

                //Create New User
                if (selection.toLowerCase().equals("new")) {
                    addUser(username, password, scan);

                }
                try {
                    //IF LOGIN WENT THROUGHUSER.getUsername()
                    if (USER.getUsername() != null) {
                        do {
                            //Ask for what they want to do next
                            System.out.println("Welcome " + USER.getUsername() + ", Please make a selection: "
                                    + "\n     list - list the availible tests "
                                    + "\n     select [testname] - take a test "
                                    + "\n     Exit - quit the application"
                                    + "\n     Edit - Edit the username and password for the current user"
                                    + "\n     Create - Walks you through creating a test");
                            selection = scan.nextLine();

                            //For a List, List the test's available
                            if (selection.toLowerCase().equals("list")) {
//                                System.out.println("list");
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
                                System.out.println("\n\nPress Enter to Continue");
                                scan.nextLine();
                                DBConnect.closeConnection();

                            }
                            //If its select
                            if (selection.toLowerCase().contains("select")) {
//                                System.out.println("Select");
                                String[] entered = selection.split(" ");
                                String title = "";
                                for (String entered1 : entered) {
                                    if (!entered1.isEmpty() & !entered1.toLowerCase().equals("select")) {
                                        title += entered1 + " ";

                                    }
                                }
                                title = title.trim();
                                takeTest(title, USER.getUser_ID());

                            }
                            
                            if(selection.toLowerCase().equals("edit")){
                                System.out.println("What would you like to change?"
                                        + "\n   User - Username"
                                        + "\n   Pass - Password");
                                String selec = scan.nextLine();
                                
                                //Edit Username
                                if(selec.toLowerCase().equals("user")){
                                    System.out.println("You would like to change your current Username?"
                                            + "\n   Y - Yes, Continue"
                                            + "\n   N - Exit");
                                    String yn = scan.nextLine();
                                    if(yn.toLowerCase().equals("y")){
                                        System.out.println("Your current Username is: "+USER.getUsername()+""
                                                + "\nPlease enter what you would like your username to be:");
                                        String newUsername = scan.nextLine();
                                        DBConnect.connectToDatabase();
                                        String updateNameString = "UPDATE users SET username= ? WHERE user_id = "+USER.getUser_ID();
                                        PreparedStatement pst = DBConnect.getConnection().prepareStatement(updateNameString);
                                        pst.setString(1, newUsername);
                                        try{
                                        pst.executeQuery();
                                        }catch(Exception e){
                                            System.out.println("Change Complete");
                                        }
                                        DBConnect.closeConnection();
                                        break;
                                    }
                                }
                                
                                //Edit Password
                                if(selec.toLowerCase().equals("pass")){
                                    System.out.println("Would you like to change you Password?"
                                            + "\n   Please be aware of the new password, as it will be required to log back into the system."
                                            + "\n"
                                            + "\n   Y - Yes Change my Password"
                                            + "\n   N - Cancel and Exit");
                                    String yn = scan.nextLine();
                                    if(yn.toLowerCase().equals("y")){
                                        System.out.println("\nYour current Password is: "+USER.getPassword()+""
                                                + "\n   Please enter your new password:" );
                                        String newPassword = scan.nextLine();
                                        DBConnect.connectToDatabase();
                                        String updatePassString = "UPDATE users SET pass= ? WHERE user_id = "+USER.getUser_ID();
                                        PreparedStatement pst = DBConnect.getConnection().prepareStatement(updatePassString);
                                        pst.setString(1,newPassword);
                                        try{
                                        pst.executeQuery();
                                        }catch(Exception e){
                                            System.out.println("Change Complete");
                                        }
                                        DBConnect.closeConnection();
                                        break;
                                    }
                                }
                            }
                            if(selection.toLowerCase().equals("create")){
                                createTest(USER.getUser_ID());
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
                } catch (NullPointerException npe) {
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

    public static void takeTest(String TestName, int userID) throws SQLException {

        Scanner scan = new Scanner(System.in);
        DBConnect.connectToDatabase();

        int score = 0;

        //Get the test to take
        String testQuery = "SELECT * FROM test WHERE test_name like ?";
        PreparedStatement tstmt = DBConnect.getConnection().prepareStatement(testQuery);
        System.out.println("143");
        String sqlTestName = "" + TestName + "";
        tstmt.setString(1, sqlTestName);
        ResultSet testResult = tstmt.executeQuery();
        System.out.println("146");
        Test test = new Test();

        while (testResult.next()) {
            test.setTest_ID(testResult.getInt("test_id"));
            test.setTest_name(testResult.getString("test_name"));
            test.setPossible_score(testResult.getInt("possible_score"));
            test.setCreator_ID(testResult.getInt("creator_id"));
            test.setHigh_score(testResult.getInt("high_score"));
            test.setHigh_score_user_ID(testResult.getInt("high_score_user_id"));
        }

        //get the user that made the test
        String userQuery = "SELECT * FROM users WHERE user_ID = " + test.getCreator_ID();
        PreparedStatement ustmt = DBConnect.getConnection().prepareStatement(userQuery);
        ResultSet userResult = ustmt.executeQuery();

        Users auser = new Users();
        while (userResult.next()) {
            auser.setUsername(userResult.getString("username"));
            auser.setUser_ID(userResult.getInt("user_ID"));
        }

        //get the user with the high score
        String userHQuery = "SELECT * FROM users WHERE user_id = " + test.getHigh_score_user_ID();
        PreparedStatement uhstmt = DBConnect.getConnection().prepareStatement(userHQuery);
        ResultSet userHResult = uhstmt.executeQuery();

        Users huser = new Users();
        while (userHResult.next()) {
            huser.setUsername(userHResult.getString("username"));
            huser.setUser_ID(userHResult.getInt("user_id"));
        }

        //display what test is being taken
        if (test.getHigh_score() > 0) {
            System.out.println("You are taking the test | " + test.getTest_name() + " | by " + auser.getUsername() + "\n The current High Score is " + test.getHigh_score()
                    + " Acheived by " + huser.getUsername());
        } else {
            System.out.println("You are taking the test | " + test.getTest_name() + " | by " + auser.getUsername() + " \n There is currently no High Score for this test.\nBe the first to set one!!!");
        }

        //get the questions for the test
        String questionQuery = "SELECT * FROM question WHERE test_ID = " + test.getTest_ID();
        PreparedStatement qstmt = DBConnect.getConnection().prepareStatement(questionQuery);
        ResultSet questionResult = qstmt.executeQuery();

        //variable for question number
        int questionNumber = 1;
        //for each question get the answers
        while (questionResult.next()) {
            Question question = new Question();
            question.setNum_answers(questionResult.getInt("num_answers"));
            question.setQuestion_ID(questionResult.getInt("question_ID"));
            question.setQuestion_text(questionResult.getString("question_text"));
            question.setPoints(questionResult.getInt("points"));
            //display the question
            System.out.println(questionNumber + ") " + question.getQuestion_text());
            questionNumber++;

            //get the answers for the question
            String answerQuery = "SELECT * FROM answer WHERE question_ID = " + question.getQuestion_ID();
            PreparedStatement astmt = DBConnect.getConnection().prepareStatement(answerQuery);
            ResultSet answerResult = astmt.executeQuery();

            //create an arraylist to iterate through later
            ArrayList<Answer> answers = new ArrayList<Answer>();

            while (answerResult.next()) {
                Answer answer = new Answer();
                answer.setAnswer_id(answerResult.getInt("answer_ID"));
                answer.setAnswer_text(answerResult.getString("answer_Text"));
                answer.setIs_Correct(answerResult.getBoolean("is_correct"));
                answers.add(answer);
            }
            //variable for the correct answer char (a, b, c, ect...)
            char correctAnswer = '\n';
            //display the answers to the user
            for (Answer answer : answers) {
                char a = (char) (answers.indexOf(answer) + 97);
                System.out.println("    " + a + ") " + answer.getAnswer_text());
                //set the correct answer
                if (answer.getIs_Correct()) {
                    correctAnswer = a;
                }
            }
            //retreive the user 
            System.out.println("Enter your answer (ex. [a], [b], [c], ...");
            String fAnswer = scan.nextLine();
            while (fAnswer.length() > 1 && (char) fAnswer.toLowerCase().charAt(0) < 97 && fAnswer.toLowerCase().charAt(0) > 122) {

                System.out.println("Incorrect input \nEnter your answer again (ex. [a], [b], [c], ...");
                fAnswer = scan.nextLine();
            }
            char charAnswer = fAnswer.charAt(0);

            if (charAnswer == correctAnswer) {
                System.out.println("Thats Correct!!!");
                score += question.getPoints();
            } else {
                System.out.println("Sorry, that was incorrect.");
            }
        }
        System.out.println("You completed the test with a score of : " + score + "/" + test.getPossible_score());
        if (score > test.getHigh_score()) {
            System.out.println("Congradulations you have the new High Score!!!");
            String updateHS = "UPDATE test SET high_score = " + score + ", high_score_user_ID = " + userID + " WHERE test_ID = " + test.getTest_ID();
            PreparedStatement upstmt = DBConnect.getConnection().prepareStatement(updateHS);
            upstmt.executeUpdate();
        }

        System.out.println("SELECT SCORE");
        System.out.println(userID + " " + test.getTest_ID());
        String scoreStatement = "SELECT * FROM score WHERE user_id = " + userID + " AND test_id = " + test.getTest_ID();
        PreparedStatement scorestmt = DBConnect.getConnection().prepareStatement(scoreStatement);
        ResultSet scoreResult = scorestmt.executeQuery();
        Score oldScore = new Score();
        while (scoreResult.next()) {
            oldScore.setUser_ID(scoreResult.getInt("user_id"));
            oldScore.setTest_ID(scoreResult.getInt("test_id"));
            oldScore.setScore(scoreResult.getInt("score"));
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        if (oldScore.getUser_ID() == userID && oldScore.getTest_ID() == test.getTest_ID()) {
            if (score > oldScore.getScore()) {
                //System.out.println("UPDATE SCORE");           Bug Testing
                String upScore = "UPDATE score SET score = " + score + ", date_achieved = cast('" + dtf.format(now) + "' as date) WHERE user_id = " + userID + " AND test_id = " + test.getTest_ID();
                PreparedStatement upstmt = DBConnect.getConnection().prepareStatement(upScore);
                upstmt.executeUpdate();
            }
        } else {
            String inScore = "INSERT INTO score (user_id, test_id, score, date_achieved) VALUES (" + userID + ", " + test.getTest_ID() + ", " + score + ", cast('" + dtf.format(now) + "' as date))";
            PreparedStatement instmt = DBConnect.getConnection().prepareStatement(inScore);
            instmt.executeUpdate();
            //System.out.println("INSTERT SCORE");      Bug Testing
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
        DBConnect.closeConnection();
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
        try {
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
            DBConnect.closeConnection();
            return USER;
        } catch (SQLException sqle) {
            System.out.println("Username and Password may be Incorrect");
        }
        DBConnect.closeConnection();
        return null;
    }

    public static void createTest(int userID) throws SQLException {
        DBConnect.connectToDatabase();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the name of your new test: ");
        String testName = scan.nextLine();

        String testQuery = "SELECT test_name FROM test WHERE test_name like ?";
        PreparedStatement tstmt = DBConnect.getConnection().prepareStatement(testQuery);
        String sqlTestName = "" + testName + "";
        tstmt.setString(1, sqlTestName);
        ResultSet testResult = tstmt.executeQuery();

        Test test = new Test();
        int totalScore = 0;

        String another = "";
        if (!testResult.next()) {
            //insert test for the ID
            test.setTest_name(testName);
            test.setCreator_ID(userID);
            String inTestQ = "INSERT INTO test(creator_id, test_name) VALUES(" + userID + ", ?)";
            PreparedStatement inTstmt = DBConnect.getConnection().prepareStatement(inTestQ);
            inTstmt.setString(1, test.getTest_name());
            inTstmt.executeUpdate();
            
            String testQ = "SELECT test_id FROM test WHERE test_name like ?";
            PreparedStatement tqstmt = DBConnect.getConnection().prepareStatement(testQ);
            tqstmt.setString(1, test.getTest_name());
            ResultSet tResult = tqstmt.executeQuery();
            while(tResult.next()){
                test.setTest_ID(tResult.getInt("test_id"));
            }
            
            do {
                Question question = new Question();
                System.out.println("Enter a question.");
                question.setQuestion_text(scan.nextLine());
                
                //typesafe for points
                boolean isValidInput = false;

                while (!isValidInput) {
                    System.out.println("How many points will this question be worth?");

                    if (scan.hasNextInt()) {
                        int pts = scan.nextInt();
                        scan.nextLine();
                        question.setPoints(pts);
                        totalScore += pts;
                        isValidInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter an integer.");
                        scan.next();
                    }
                }
                
                
                int numAnswers = 0;
                String anotherA = "";
                ArrayList<Answer> answers = new ArrayList<>();
                
                do{
                    Answer answer = new Answer();
                    System.out.println("Enter an answer");
                    String answerText = scan.nextLine();
                    answer.setAnswer_text(answerText);
                    
                        System.out.println("Is this the correct answer Enter \"yes\" or \"no\"");
                        if(scan.nextLine().toLowerCase().equals("yes")){
                            System.out.println("This answer is the correct one for this question.");
                            answer.setIs_Correct(true);
                        }
                        else{
                            System.out.println("This answer is not the correct one for this question.");
                            answer.setIs_Correct(false);
                        }
                    
                    answers.add(answer);
                    
                    numAnswers++;
                    System.out.println("Would you like to add another answer? Enter \"yes\" or \"no\"");
                    anotherA = scan.nextLine();
                } while(anotherA.toLowerCase().equals("yes"));
                
                String inQuestionQ = "INSERT INTO question(test_id, question_text, num_answers, points) VALUES( " + test.getTest_ID() + ", ?, " + numAnswers + ", " + question.getPoints() + ")";
                PreparedStatement inQstmt = DBConnect.getConnection().prepareStatement(inQuestionQ);
                inQstmt.setString(1, question.getQuestion_text());
                inQstmt.executeUpdate();
                
                String questionQuery = "SELECT question_id FROM question WHERE test_id = " + test.getTest_ID() + " AND question_text like ?";
                PreparedStatement qQstmt = DBConnect.getConnection().prepareStatement(questionQuery);
                qQstmt.setString(1, question.getQuestion_text());
                ResultSet questionResult = qQstmt.executeQuery();
                while(questionResult.next()){
                    question.setQuestion_ID(questionResult.getInt("question_id"));
                }
                for(Answer a: answers){
                    String inAnswerQuery = "INSERT INTO answer(question_id, answer_text, is_correct) VALUES(" + question.getQuestion_ID() +", ?, " + a.getIs_Correct() +")";
                    PreparedStatement aQstmt = DBConnect.getConnection().prepareStatement(inAnswerQuery);
                    aQstmt.setString(1, a.getAnswer_text());
                    aQstmt.executeUpdate();
                }
                System.out.println("Would you like to add another question? Enter \"yes\" or \"no\"");
                another = scan.nextLine();
            } while (another.toLowerCase().equals("yes"));
            
            String updateTest = "UPDATE test SET possible_score = " + totalScore + " WHERE test_id = " + test.getTest_ID();
            PreparedStatement uTstmt = DBConnect.getConnection().prepareStatement(updateTest);
            uTstmt.executeUpdate();
        }

        else{
            System.out.println("That test already exists.");
        }

        DBConnect.closeConnection();
    }

}
