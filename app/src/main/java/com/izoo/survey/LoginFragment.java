package com.izoo.survey;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.izoo.survey.model.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    static interface LoginListener{
        void loginButtonClicked(String login, String password);
    }
    private LoginListener listener;
    private TextView errorMessage;
    private Spinner inputLogin;
    private EditText inputPassword;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        init(view);
        return view;
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.listener = (LoginListener) activity;
    }

    private void init(View view){
        errorMessage = (TextView) view.findViewById(R.id.error_message);
        inputLogin = (Spinner) view.findViewById(R.id.input_login);
        inputPassword = (EditText) view.findViewById(R.id.input_password);
        final Cursor cursor = DatabaseHelper.getUsers(new DatabaseHelper(getActivity()).getReadableDatabase());
        CursorAdapter loginList = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,cursor, new String[]{"Login"},new int[] { android.R.id.text1 },0);
        inputLogin.setAdapter(loginList);
        Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = ((Cursor)inputLogin.getSelectedItem()).getString(1);
                String password = inputPassword.getText().toString();
                if(listener != null) listener.loginButtonClicked(login,password);
            }
        });
    }

}
