package com.izoo.survey;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.izoo.survey.model.DBAdapter;
import com.izoo.survey.model.LoginInterface;
import com.izoo.survey.model.Users;

public class LoginFragment extends Fragment implements LoginInterface {

    private Presenter presenter;
    private Spinner inputLogin;
    private EditText inputPassword;
    private View view;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        try{
            presenter = new Presenter(this,getActivity());
        }catch (Exception e){
            Toast.makeText(getActivity(),"Błąd bazy danych",Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(presenter != null) presenter.onDestroy();
    }

    @Override
    public void setLoginView(Cursor cursor){
        inputLogin = (Spinner) view.findViewById(R.id.input_login);
        inputPassword = (EditText) view.findViewById(R.id.input_password);
        inputLogin.setVisibility(View.VISIBLE);
        inputPassword.setVisibility(View.VISIBLE);
        view.findViewById(R.id.error_message).setVisibility(View.VISIBLE);
        ((TextView) view.findViewById(R.id.login_message)).setText("Login");
        CursorAdapter loginList = new SimpleCursorAdapter(getActivity(),
                R.layout.spinner_list,cursor, new String[]{"Login"},new int[] { android.R.id.text1 },0);
        inputLogin.setAdapter(loginList);
        Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setText("Zaloguj");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = ((Cursor)inputLogin.getSelectedItem()).getString(1);
                String password = inputPassword.getText().toString();
                try {
                    presenter.loginUser(login, password);
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),"Błąd podczas logowania",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void loginUser(Users user){
        ((MainActivity)getActivity()).loginUser(user);
    }

    @Override
    public void setErrorMessage(){
        TextView error = (TextView) view.findViewById(R.id.error_message);
        error.setText("Nieprawidłowe hasło");
    }

    @Override
    public void setLogoutView(){
        Button logoutButton = (Button) view.findViewById(R.id.login_button);
        logoutButton.setText("Wyloguj");
        ((Spinner) view.findViewById(R.id.input_login)).setVisibility(View.GONE);
        ((EditText) view.findViewById(R.id.input_password)).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.error_message)).setVisibility(View.GONE);
        TextView loginMessage = (TextView) view.findViewById(R.id.login_message);
        loginMessage.setText("Jesteś zalogowany jako: " + MainActivity.getLoggedUser().getLogin());
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logoutUser();
            }
        });
    }

    @Override
    public void logoutUser(){
        ((MainActivity)getActivity()).logoutUser();
    }

    class LoginTask extends AsyncTask<Object, Void, Boolean> {
        Cursor cursor;
        @Override
        protected Boolean doInBackground(Object... params) {
            DBAdapter dbAdapter = (DBAdapter) params[0];
            try {
                dbAdapter.openReadableDatabase();
                cursor = dbAdapter.getAllUsers();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean success) {
            if (!success) {
                String text = "Błąd bazy danych";
                SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
                biggerText.setSpan(new RelativeSizeSpan(1.45f), 0, text.length(), 0);
                Toast.makeText(getActivity(),biggerText,Toast.LENGTH_SHORT).show();
            }else{
                setLoginView(cursor);
            }
        }
    }

    class Presenter{
        Users user;
        LoginInterface loginFragment;
        DBAdapter dbAdapter;

        Presenter(LoginInterface loginFragment,Activity activity)throws Exception{
            user = MainActivity.getLoggedUser();
            this.loginFragment = loginFragment;
            dbAdapter = new DBAdapter(activity);
            createView();
        }

        void createView() throws Exception{
            if(user != null){
                loginFragment.setLogoutView();
            }
            else{
                new LoginTask().execute(dbAdapter);
            }
        }

        void logoutUser(){
            loginFragment.logoutUser();
        }

        void loginUser(String login, String password)throws Exception{
            Users user = dbAdapter.findUser(login,password);
            if(user != null )
                loginFragment.loginUser(user);
            else
                loginFragment.setErrorMessage();
        }
        void onDestroy(){
            dbAdapter.Close();
        }
    }
}
