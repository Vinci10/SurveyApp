package com.izoo.survey;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.izoo.survey.model.History;
import com.izoo.survey.model.Survey;
import com.izoo.survey.model.Users;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static Users loggedUser;
    private int currentPosition = 0;
    private String[] titles;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        this.navigationView = navigationView;
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getSupportFragmentManager();
                        if (fragMan.getBackStackEntryCount() == 0) return;
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
                        if (fragment instanceof LoginFragment) {
                            currentPosition = 0;
                        }
                        if (fragment instanceof SurveyListFragment) {
                            currentPosition = 1;
                        }
                        if (fragment instanceof StatisticsFragment) {
                            currentPosition = 2;
                        }
                        setActionBarTitle(currentPosition);
                        navigationView.getMenu().getItem(currentPosition).setChecked(true);
                    }
                }
        );

        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new LoginFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        }
        else{
            currentPosition = savedInstanceState.getInt("position");
            if(loggedUser != null) setActionBarTitle(currentPosition);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragMan = getSupportFragmentManager();
            Fragment fragment = fragMan.findFragmentByTag("visible_fragment");
            if (fragMan.getBackStackEntryCount() == 0) super.onBackPressed();
            else {
                if (fragment instanceof SurveyFragment){
                    if(SurveyFragment.getHistory() == null){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setMessage("Jesteś pewien, że chcesz wyjść? Wszystkie odpowiedzi zostaną utracone.");
                        alertDialogBuilder.setPositiveButton("Tak",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        setSurveyListFragment();
                                    }
                                });
                        alertDialogBuilder.setNegativeButton("Nie", null);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }else super.onBackPressed();
                }
                else super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = new Fragment();
        if(loggedUser != null && id != R.id.nav_close){
            if (id == R.id.nav_login) {
                fragment = new LoginFragment();
            } else if (id == R.id.nav_survey) {
                fragment = new SurveyListFragment();
            } else {
                fragment = new StatisticsFragment();
            }
        } else if(loggedUser == null && id != R.id.nav_close) fragment = new LoginFragment();
        else System.exit(0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment,"visible_fragment")
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        getSupportActionBar().setTitle(item.getTitle());
        return true;
    }

    public void logoutUser(){
        loggedUser = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        cleanBackStack(fragmentManager);
        fragmentManager.beginTransaction().replace(R.id.content_frame,new LoginFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        setActionBarTitle(0);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public void loginUser(Users user){
        loggedUser = user;
        setSurveyListFragment();
        hideSoftKeyboard(this);
    }

    public static Users getLoggedUser(){
        return loggedUser;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void cleanBackStack(FragmentManager fragmentManager){
        int count = fragmentManager.getBackStackEntryCount();
        for(int i = 0; i < count; ++i)
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }

    private void setActionBarTitle(int position) {
        getSupportActionBar().setTitle(titles[position]);
    }

    public void setSurveyListFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        cleanBackStack(fragmentManager);
        fragmentManager.beginTransaction().replace(R.id.content_frame,new SurveyListFragment(),"visible_fragment")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
        setActionBarTitle(1);
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    public void setSurveyFragment(int ID_Survey, History history){
        SurveyFragment fragment = new SurveyFragment();
        SurveyFragment.set(ID_Survey,history);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "visible_fragment")
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void setStatisticsFragment(boolean pop){
        StatisticsFragment fragment = new StatisticsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(pop) fragmentManager.popBackStack();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "visible_fragment")
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    public void setSummaryFragment(int ID_Survey){
        SummaryFragment fragment = new SummaryFragment();
        SummaryFragment.setID_Survey(ID_Survey);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "visible_fragment")
                .addToBackStack(null).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }
}
