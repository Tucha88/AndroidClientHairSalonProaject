package com.telran.borislav.hairsalonclientproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.telran.borislav.hairsalonclientproject.models.Master;
import com.telran.borislav.hairsalonclientproject.models.ServicesArray;

public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MapFragment.showSelectedMasterListener, MasterProfileFragment.TransferredMasterListener {
    public static final String TAG = "ONTAG";
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Master master1;
    private ServicesArray servicesArray1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setResult(RESULT_OK);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        manager = getFragmentManager();
        MapFragment fragment = new MapFragment();
        fragment.setSelectedMasterListener(this);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.second_fragment_controller, fragment, "FRAG_MAP");
        transaction.commit();
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
        getMenuInflater().inflate(R.menu.second, menu);
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

        if (id == R.id.nav_home) {
            for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
                manager.popBackStack();
            }

        } else if (id == R.id.nav_appointment) {
            manager = getFragmentManager();
            PersonalProfileFragment personalProfileFragment = new PersonalProfileFragment();
            transaction = manager.beginTransaction();
            transaction.replace(R.id.second_fragment_controller, personalProfileFragment, "PERSONAL_PROFILE");
            transaction.addToBackStack("PERSONAL_PROFILE");
            transaction.commit();
        } else if (id == R.id.nav_favorite) {

        } else if (id == R.id.nav_exit) {
            SharedPreferences sharedPreferences = getSharedPreferences("AUTH", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("TOKEN", "");
            editor.commit();
            setResult(RESULT_CANCELED);
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void showMaster(Master master) {
        this.master1 = master;
        MasterProfileFragment fragment = new MasterProfileFragment();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.second_fragment_controller, fragment, "FRAG_MASTER_PROF");
        transaction.addToBackStack("FRAG_MASTER_PROF");


//        fragment.setMasterListener(this);
        fragment.getMyMaster(master1);

        transaction.commit();

    }

    @Override
    public void transferMaster(ServicesArray servicesArray, Master master) {
        this.master1 = master;
        this.servicesArray1 = servicesArray;
        CalendarFragment calendarFragment = new CalendarFragment();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        // TODO: 13.05.2017 добавить метод для обращение на сервер для заполнения календаря
        transaction.replace(R.id.second_fragment_controller, calendarFragment, "CALENDAR_FRAGMENT");
        transaction.addToBackStack("CALENDAR_FRAGMENT");
        transaction.commit();
        calendarFragment.setMasterAndServices(servicesArray1, master1);
    }
}
