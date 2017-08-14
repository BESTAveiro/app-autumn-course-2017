package com.ua.bestaveiro.autumncourse2017.Schedule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ua.bestaveiro.autumncourse2017.MainActivity;
import com.ua.bestaveiro.autumncourse2017.R;


public class ScheduleEquipasHorizontal extends Fragment
{

    View myView2;
    ImageView image;
    FragmentManager fragmentManager;
    FragmentManager fragmentManager2;

    FloatingActionButton fab;
    FloatingActionButton fab2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        myView2 = inflater.inflate(R.layout.horario_equipas_horizontal, container, false); // Define o layout

        fab = (FloatingActionButton) myView2.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ScheduleEventoHorizontal fragment = new ScheduleEventoHorizontal();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                MainActivity.fragStack.push(1);

            }
        });

        fab2 = (FloatingActionButton) myView2.findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();

                ScheduleEquipas fragment = new ScheduleEquipas();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                MainActivity.fragStack.push(1);

            }
        });

        return myView2;
    }

}

