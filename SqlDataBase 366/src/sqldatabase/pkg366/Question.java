/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqldatabase.pkg366;

/**
 *
 * @author carte
 */
public class Question {
    
    private int question_ID;
    private int test_ID;
    private String question_text;
    private int num_answers;
    private int points;
    
    public int getQuestion_ID(){
        return this.question_ID;
    }
    public void setQuestion_ID(int id){
        this.question_ID = id;
    }
    public int getTest_ID(){
        return this.test_ID;
    }
    public void setTest_ID(int testID){
        this.test_ID = testID;
    }
    public String getQuestion_text(){
        return this.question_text;
    }
    public void setQuestion_text(String question_text){
        this.question_text = question_text;
    }
    public int getNum_answers(){
        return this.num_answers;
    }
    public void setNum_answers(int num_answers){
        this.num_answers = num_answers;
    }
    public int getPoints(){
        return this.points;
    }
    public void setPoints(int points){
        this.points = points;
    }
    
    
}
