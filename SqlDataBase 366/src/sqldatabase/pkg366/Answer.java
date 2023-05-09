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
public class Answer {

    public int answer_id;
    private int question_id;
    private String answer_text;
    private boolean is_correct;

    public int getAnswer_id() {
        return this.answer_id;
    }

    public void setAnswer_id(int id) {
        this.answer_id = id;
    }

    public int getQuestion_id() {
        return this.question_id;
    }

    public void setQuestion_id(int id) {
        this.question_id = id;
    }

    public String getAnswer_text() {
        return this.answer_text;
    }

    public void setAnswer_text(String ans_text) {
        this.answer_text = ans_text;
    }

    public boolean getIs_Correct() {
        return this.is_correct;
    }
    public void setIs_Correct(boolean correct){
        this.is_correct = correct;
    }

}
