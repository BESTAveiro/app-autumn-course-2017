package com.example.bestaveiro.appcurso.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bestaveiro.appcurso.MainActivity;
import com.example.bestaveiro.appcurso.R;


public class ScheduleEventoHorizontal extends Fragment
{

    View myView4;
    FragmentManager fragmentManager;
    FragmentManager fragmentManager2;

    FloatingActionButton fab;
    FloatingActionButton fab2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        myView4 = inflater.inflate(R.layout.horario_evento_horizontal, container, false);

        fab = (FloatingActionButton) myView4.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ScheduleEquipasHorizontal fragment = new ScheduleEquipasHorizontal();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                MainActivity.fragStack.push(1);

            }
        });

        fab2 = (FloatingActionButton) myView4.findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();

                ScheduleEvento fragment = new ScheduleEvento();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                MainActivity.fragStack.push(1);

            }
        });

        return myView4;
    }

}
