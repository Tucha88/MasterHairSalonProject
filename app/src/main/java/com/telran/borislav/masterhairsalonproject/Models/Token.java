package com.telran.borislav.masterhairsalonproject.Models;

/**
 * Created by vadim on 15.03.2017.
 */

public class Token {
    private String token;
    private boolean user;


    public Token() {
    }

    public Token(String token, boolean user) {
        this.token = token;
        this.user = user;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
