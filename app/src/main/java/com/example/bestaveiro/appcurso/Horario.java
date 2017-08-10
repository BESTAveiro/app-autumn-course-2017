package com.example.bestaveiro.appcurso;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



/**
 * Created by filipe on 13/05/2016.
 */
public class Horario extends Fragment{

    View myView;
    Button btn1;
    Button btn2;
    FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StaticMethods.removeTabLayout(getActivity());

        myView = inflater.inflate(R.layout.horario, container, false);

        btn1 = (Button) myView.findViewById(R.id.btn1);
        btn2 = (Button) myView.findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                HorarioEvento fragment = new HorarioEvento();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                HorarioEquipas fragment = new HorarioEquipas();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.commit();

            }
        });


        return myView;
    }





}
