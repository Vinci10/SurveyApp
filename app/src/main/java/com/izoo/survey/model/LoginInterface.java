package com.izoo.survey.model;

import android.database.Cursor;

public interface LoginInterface {
    void setLoginView(Cursor cursor);
    void setLogoutView();
    void loginUser(Users user);
    void logoutUser();
    void setErrorMessage();
}
