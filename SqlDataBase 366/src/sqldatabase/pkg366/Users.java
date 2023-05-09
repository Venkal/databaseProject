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
public class Users {

    private int user_ID;
    private String username;
    private String password;

    // Getter and setter for user_ID
    public int getUser_ID() {
        return user_ID;
    }
    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
