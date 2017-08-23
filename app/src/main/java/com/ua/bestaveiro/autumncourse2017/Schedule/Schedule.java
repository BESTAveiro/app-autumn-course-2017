package com.ua.bestaveiro.autumncourse2017.Schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ua.bestaveiro.autumncourse2017.MainActivity;
import com.ua.bestaveiro.autumncourse2017.R;
import com.ua.bestaveiro.autumncourse2017.StaticMethods;


/**
 * Created by filipe on 13/05/2016.
 */
public class Schedule extends Fragment{

    View myView;
    Button btn1;
    Button btn2;
    FragmentManager fragmentManager;
    FragmentManager fragmentManager2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StaticMethods.removeTabLayout(getActivity());

        getActivity().setTitle("Schedule"); // Nome no separador

        myView = inflater.inflate(R.layout.horario, container, false); // Define o layout

        btn1 = (Button) myView.findViewById(R.id.btn1);
        btn2 = (Button) myView.findViewById(R.id.btn2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ScheduleEventoHorizontal fragment = new ScheduleEventoHorizontal();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                MainActivity.fragStack.push(1);


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();

                ScheduleEquipasHorizontal fragment = new ScheduleEquipasHorizontal();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                MainActivity.fragStack.push(1);

            }
        });


        return myView;
    }





}
