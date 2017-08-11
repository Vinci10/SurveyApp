package com.izoo.survey.model;

import java.net.PasswordAuthentication;

/**
 * Created by mateusz on 07.08.17.
 */

public class Users {
    int ID_User;
    String Login;
    String Password;
    int ID_Type_Users;

    // constructors
    public Users() {
    }

    public Users(String Login, String Password) {
        this.Login=Login;
        this.Password=Password;
    }

    public Users(int id, String Login, String Password) {
        this.ID_User=id;
        this.Login=Login;
        this.Password=Password;
    }
    public Users(int id, String Login, String Password, int ID_Type_Users) {
        this.ID_User=id;
        this.Login=Login;
        this.Password=Password;
        this.ID_Type_Users=ID_Type_Users;
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

    public int getID_Type_Users() {
        return ID_Type_Users;
    }

    public void setID_Type_Users(int ID_Type_Users) {
        this.ID_Type_Users = ID_Type_Users;
    }
}
