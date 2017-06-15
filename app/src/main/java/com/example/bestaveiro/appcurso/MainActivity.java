package com.example.bestaveiro.appcurso;

import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bestaveiro.appcurso.Drinking_G2K.Drinking_G2K;
import com.example.bestaveiro.appcurso.Inventario.Inventario_versao_tap_swipe;
import com.example.bestaveiro.appcurso.Notificacoes.NotificacoesDB;
import com.example.bestaveiro.appcurso.Notificacoes.NotificationListener;
import com.example.bestaveiro.appcurso.Notificacoes.notificacoes;
import com.example.bestaveiro.appcurso.Schedule.Schedule;

import java.util.Set;

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

        NotificacoesDB.retrieveAll();

        StaticMethods.removeFAB(this);

        // teste para ver se os extras da notificação eram entregues
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            Set<String> set = bundle.keySet();

            for(String a : set)
            {
                Log.d("Message", String.format("%s - %s", a, bundle.getString(a)));
            }
        }


        fragStack = new FragmentStack();

        fragManager = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Muda Isto");

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
                        , new Schedule())
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

        switch (id)
        {
            case R.id.checkNotiAccess:
                Log.d("Menu", String.format("has access = %b", NotificationListener.hasAccess));
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                startActivity(intent);
                break;
        }



        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if(item.isChecked()) return true;
        item.setChecked(true);

        switch(id)
        {
            case R.id.nav_schedule:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new Schedule())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(0);
                break;

            case R.id.nav_horario:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new horario())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(1);
                break;

            case R.id.nav_inventario_swipe:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , Inventario_versao_tap_swipe.NewInstance())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(2);
                break;

            case R.id.nav_pontuaçao:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new pontuacao())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(3);
                break;

            case R.id.nav_fotos:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new fotos())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(4);
                break;

            case R.id.nav_notificacoes:
                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new notificacoes())
                        .addToBackStack("op")
                        .commit();
                fragStack.push(5);
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
