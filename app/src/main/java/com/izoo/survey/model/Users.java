package com.izoo.survey.model;

/**
 * Created by mateusz on 07.08.17.
 */

public class Users {
    private int ID_User;
    private String Login;
    private String Password;
    private String Type_Users;

    // constructors

    public Users(int id, String Login, String Password) {
        this.ID_User=id;
        this.Login=Login;
        this.Password=Password;
        Type_Users = null;
    }

    public int getID_User() {
        return ID_User;
    }

    public void setID_User(int ID_User) {
        this.ID_User = ID_User;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getType_Users() {
        return Type_Users;
    }

    public void setType_Users(String Type_Users) {
        this.Type_Users = Type_Users;
    }
}
