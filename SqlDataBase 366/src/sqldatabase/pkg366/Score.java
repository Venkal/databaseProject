/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqldatabase.pkg366;
import java.time.LocalDateTime;

/**
 *
 * @author carte
 */
public class Score {
    
    private int user_ID;
    private int test_ID;
    private int score;
    private LocalDateTime date_achieved;
    
    public int getUser_ID(){
        return user_ID;
    }
    
    public void setUser_ID(int userID){
        this.user_ID = userID;
    }
    
    public int getTest_ID(){
        return test_ID;
    }
    
    public void setTest_ID(int testID){
        this.test_ID = testID;
    }
   
    public int getScore(){
        return score;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public LocalDateTime getDate_achieved(){
        return date_achieved;
    }
    
    public void setDate_Achieved(LocalDateTime date_achieved){
        this.date_achieved = date_achieved;
    }
    
}
