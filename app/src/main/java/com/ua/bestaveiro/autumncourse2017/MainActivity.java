package com.ua.bestaveiro.autumncourse2017;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ua.bestaveiro.autumncourse2017.Drinking_G2K.Drinking_G2K;
import com.ua.bestaveiro.autumncourse2017.OrganizersHandbook.OrganizersHandbook;
import com.ua.bestaveiro.autumncourse2017.Schedule.Schedule;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{
    FragmentManager fragManager;
    public static FragmentStack fragStack;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragStack = new FragmentStack();

        fragManager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        setTitle("Muda Isto");

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // para começar automaticamente no primeiro fragment
        fragManager.beginTransaction()
                .replace(R.id.content_frame
                        , new OrganizersHandbook())
                .commit();
        fragStack.push(0);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragManager.getBackStackEntryCount() > 0)
            {
                fragManager.popBackStack();
                int i = fragStack.pop();
                Log.d("onBackPressed", String.format("%d retirado do stack", i));
                navigationView.getMenu().getItem(i).setChecked(true);
            }
            else super.onBackPressed();
        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


//        if(item.isChecked()) return true;
        item.setChecked(true);

        switch(id)
        {
            case R.id.nav_schedule:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new OrganizersHandbook())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(0);
                break;

            case R.id.nav_horario:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new Schedule())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(1);
                break;

            case R.id.nav_drinking_get2know:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new Drinking_G2K())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(6);
                break;
        }

        Log.d("back stack entry count", ""+fragManager.getBackStackEntryCount());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
