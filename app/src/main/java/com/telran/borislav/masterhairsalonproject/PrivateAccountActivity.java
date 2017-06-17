package com.telran.borislav.masterhairsalonproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentEditAccountInfo;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentFirstMap;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentPrivateAccount;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentScheduleTemplate;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentServicesAdd;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentServicesList;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentTwoWeekScheduleList;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentTwoWeekSchedulePageView;
import com.telran.borislav.masterhairsalonproject.Models.CalendarDayCustom;
import com.telran.borislav.masterhairsalonproject.Models.Master;
import com.telran.borislav.masterhairsalonproject.Models.Services;
import com.telran.borislav.masterhairsalonproject.Tasks.GetMyProfileTask;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

import java.util.ArrayList;
import java.util.List;

public class PrivateAccountActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GetMyProfileTask.AsyncResponse, FragmentPrivateAccount.FragmentPrivateAccountListener, FragmentEditAccountInfo.onClickListenerFromEditInfo, FragmentServicesList.ListFragmentListener, FragmentServicesAdd.AddItemFragmentListener, FragmentTwoWeekScheduleList.TwoWeekScheduleListener {

    FragmentPrivateAccount fragmentPrivateAccount;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private TextView nameMasterTxt;
    private TextView lastNameMasterTxt;
    private Toolbar toolbar;
    private boolean isDrawerLocked = false;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Hair Salon :)");
        toolbar.setNavigationIcon(R.drawable.ic_action_bar_home);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDrawerLocked) {
                    drawerStateSwitcher();
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        setSupportActionBar(toolbar);
        setResult(RESULT_OK);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        fragmentPrivateAccount = new FragmentPrivateAccount();
        fragmentPrivateAccount.setListener(this);
        transaction.add(R.id.content_private_account,fragmentPrivateAccount,Utils.PRIVATE_ACCOUNT);
        transaction.commit();
        getSupportActionBar().setTitle("Account");
//        new GetMyProfileTask(this, getTokenFromShared(),"/master/info",this).execute();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.private_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_personal_account) {
            cleanBackStack();
            transaction.replace(R.id.content_private_account, fragmentPrivateAccount,Utils.PRIVATE_ACCOUNT);
            transaction.addToBackStack(Utils.PRIVATE_ACCOUNT);
            transaction.commit();
            getSupportActionBar().setTitle("Account");
        } else if (id == R.id.nav_my_adress) {
            cleanBackStack();
            FragmentFirstMap fragmentFirstMap = new FragmentFirstMap();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.content_private_account, fragmentFirstMap, Utils.FRAGMENT_FIRST_MAP);
            transaction.addToBackStack(Utils.FRAGMENT_FIRST_MAP);
            transaction.commit();
        } else if (id == R.id.nav_my_servises) {
            cleanBackStack();
            FragmentServicesList fragmentServicesList = new FragmentServicesList();
            fragmentServicesList.setFragmentListener(this);
            transaction = manager.beginTransaction();
            transaction.replace(R.id.content_private_account,fragmentServicesList,Utils.SERVICE_LIST);
            transaction.addToBackStack(Utils.SERVICE_LIST);
            transaction.commit();
            getSupportActionBar().setTitle("My services");
        } else if (id == R.id.nav_two_week_schedule) {
            cleanBackStack();
            FragmentTwoWeekScheduleList fragmentTwoWeekScheduleList = new FragmentTwoWeekScheduleList();
            transaction = manager.beginTransaction();
            fragmentTwoWeekScheduleList.setListener(this);
            transaction.replace(R.id.content_private_account, fragmentTwoWeekScheduleList, Utils.TWO_WEEK_SCHEDULE_FRAGMENT);
            transaction.addToBackStack(Utils.TWO_WEEK_SCHEDULE_FRAGMENT);
            transaction.commit();
            getSupportActionBar().setTitle("My schedule");
        } else if (id == R.id.nav_schedule) {
            cleanBackStack();
            FragmentScheduleTemplate fragmentScheduleTemplate = new FragmentScheduleTemplate();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.content_private_account,fragmentScheduleTemplate,Utils.SCHEDULE_TEMPLATE);
            transaction.addToBackStack(Utils.SCHEDULE_TEMPLATE);
            transaction.commit();
            getSupportActionBar().setTitle("My schedule template");
        } else if (id == R.id.nav_portfolio) {
//            transaction.replace(R.id.content_private_account, new FragmentPortfolio(), "FRAG_PORTFOL");
//            getSupportActionBar().setTitle("My portfolio");
//            cleanBackStack();

//            transaction.commit();
        } else if (id == R.id.nav_exit) {
            cleanBackStack();
            exit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void exit() {
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.AUTH, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Utils.TOKEN, "");
        setResult(RESULT_CANCELED);
        editor.commit();
        finish();
    }

    private void drawerStateSwitcher() {
        if (isDrawerLocked) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//            toolbar.setNavigationIcon(R.drawable.ic_action_bar_home);
            isDrawerLocked = false;
            manager.popBackStack();
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            toolbar.setNavigationIcon(R.drawable.ic_action_bar_back);
            isDrawerLocked = true;
        }
    }

    private String getTokenFromShared(){
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.AUTH,MODE_PRIVATE);
        return sharedPreferences.getString(Utils.TOKEN,"");
    }

    @Override
    public void processFinish() {
        FragmentPrivateAccount fragmentPrivateAccount = (FragmentPrivateAccount) manager.findFragmentByTag(Utils.PRIVATE_ACCOUNT);
        fragmentPrivateAccount.fillInfo(getMasterInfo());
    }

    @Override
    public void profileGetError(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    private Master getMasterInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.PROFILE,MODE_PRIVATE);
        return new Gson().fromJson(sharedPreferences.getString(Utils.MASTER_PROFILE,""),Master.class);
    }


    @Override
    public void reDownloadMasterInfo() {
        new GetMyProfileTask(this, getTokenFromShared(),"/master/info",this).execute();
    }

    @Override
    public void editMasterInfo() {
        FragmentEditAccountInfo fragmentEditAccountInfo = new FragmentEditAccountInfo();
        transaction = manager.beginTransaction();
        fragmentEditAccountInfo.setListener(this);
        transaction.replace(R.id.content_private_account,fragmentEditAccountInfo,Utils.EDIT_ACCOUNT);
        transaction.addToBackStack(Utils.EDIT_ACCOUNT);
        transaction.commit();
        getSupportActionBar().setTitle("Edit Account Info");
    }

    @Override
    public void onClickListener() {
        for (int i = 0; i < manager.getBackStackEntryCount()-1; i++) {
            manager.popBackStack();
        }
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, fragmentPrivateAccount,Utils.PRIVATE_ACCOUNT);
        transaction.addToBackStack(Utils.PRIVATE_ACCOUNT);
        transaction.commit();
    }

    @Override
    public void itemSelected(Services item) {

    }

    @Override
    public void addItem() {
        FragmentServicesAdd fragmentServicesAdd = new FragmentServicesAdd();
        fragmentServicesAdd.setmListener(this);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account,fragmentServicesAdd,Utils.SERVICE_ADD);
        transaction.addToBackStack(Utils.SERVICE_ADD);

        transaction.commit();
    }

    @Override
    public void callback() {
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            manager.popBackStack();
        }
        FragmentServicesList fragmentServicesList = new FragmentServicesList();
        fragmentServicesList.setFragmentListener(this);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account,fragmentServicesList,Utils.SERVICE_LIST);
        transaction.addToBackStack(Utils.SERVICE_LIST);
        transaction.commit();
    }

    private void cleanBackStack() {
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            manager.popBackStack();
        }
    }

    @Override
    public void onDayClick(CalendarDayCustom calendarDayCustom, ArrayList<CalendarDayCustom> calendarDays, int position) {
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        TypeToken<List<CalendarDayCustom>> typeToken = new TypeToken<List<CalendarDayCustom>>() {
        };

        bundle.putString(Utils.CALENDAR_DAY_BUNDLE, gson.toJson(calendarDayCustom, CalendarDayCustom.class));
        bundle.putString(Utils.CALENDAR_DAYS_BUNDLE, gson.toJson(calendarDays, typeToken.getType()));
        bundle.putInt(Utils.POSITION, position);
        FragmentTwoWeekSchedulePageView pageView = new FragmentTwoWeekSchedulePageView();
        pageView.setArguments(bundle);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.content_private_account, pageView, Utils.TWO_WEEK_PAGER_VIEW);
        transaction.addToBackStack(Utils.TWO_WEEK_PAGER_VIEW);
        transaction.commit();
    }
}
