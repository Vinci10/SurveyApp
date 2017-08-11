package com.izoo.survey.model;

/**
 * Created by mateusz on 08.08.17.
 */

public class Type_Users {
    int ID_Type_Users;
    String Name_Type_User;

    public Type_Users(int ID_Type_Users, String name_Type_User) {
        this.ID_Type_Users = ID_Type_Users;
        Name_Type_User = name_Type_User;
    }

    public int getID_Type_Users() {
        return ID_Type_Users;
    }

    public void setID_Type_Users(int ID_Type_Users) {
        this.ID_Type_Users = ID_Type_Users;
    }

    public String getName_Type_User() {
        return Name_Type_User;
    }

    public void setName_Type_User(String name_Type_User) {
        Name_Type_User = name_Type_User;
    }
}
