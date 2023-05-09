/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqldatabase.pkg366;
import java.time.LocalDate;

/**
 *
 * @author carte
 */
public class Test {
    
    private int test_ID;
    private int creator_ID;
    private double high_score;
    private int high_score_user_ID;
    private int possible_score;
    private LocalDate date_created;
    private String test_name;
    
    public int getTest_ID(){
        return test_ID;
    }
    
    public void setTest_ID(int test_ID) {
        this.test_ID = test_ID;
    }

    // Getter and setter for creator_ID
    public int getCreator_ID() {
        return creator_ID;
    }
    public void setCreator_ID(int creator_ID) {
        this.creator_ID = creator_ID;
    }

    // Getter and setter for high_score
    public double getHigh_score() {
        return high_score;
    }
    public void setHigh_score(double high_score) {
        this.high_score = high_score;
    }

    // Getter and setter for high_score_user_ID
    public int getHigh_score_user_ID() {
        return high_score_user_ID;
    }
    public void setHigh_score_user_ID(int high_score_user_ID) {
        this.high_score_user_ID = high_score_user_ID;
    }

    // Getter and setter for possible_score
    public int getPossible_score() {
        return possible_score;
    }
    public void setPossible_score(int possible_score) {
        this.possible_score = possible_score;
    }

    // Getter and setter for date_created
    public LocalDate getDate_created() {
        return date_created;
    }
    public void setDate_created(LocalDate date_created) {
        this.date_created = date_created;
    }
    
    public String getTest_name(){
        return test_name;
    }
    public void setTest_name(String test_name){
        this.test_name = test_name;
    }
}

