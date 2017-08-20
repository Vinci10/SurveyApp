package com.izoo.survey;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.izoo.survey.model.Users;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutFragment extends Fragment {

    static interface LogoutListener{
        void logout();
    }
    public LogoutFragment() {
        // Required empty public constructor
    }
    private LogoutListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);
        Users user = MainActivity.getLoggedUser();
        if(user != null ){
            Button logoutButton = (Button) view.findViewById(R.id.logout_button);
            TextView loginMessage = (TextView) view.findViewById(R.id.login_message);
            loginMessage.setText("Jesteś zalogowany jako: "+ MainActivity.getLoggedUser().getLogin());
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) listener.logout();
                }
            });
        }
        return view;
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.listener = (LogoutFragment.LogoutListener) activity;
    }

}
