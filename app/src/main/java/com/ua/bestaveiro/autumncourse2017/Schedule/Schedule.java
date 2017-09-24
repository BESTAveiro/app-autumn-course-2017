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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StaticMethods.removeTabLayout(getActivity());

        getActivity().setTitle("Schedule"); // Nome no separador

        myView = inflater.inflate(R.layout.schedule, container, false); // Define o layout

        btn1 = (Button) myView.findViewById(R.id.btn1);
        btn2 = (Button) myView.findViewById(R.id.btn2);

        fragmentManager = getFragmentManager();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.content_frame, new ScheduleEventoHorizontal())
                        .addToBackStack(null)
                        .commit();
                MainActivity.fragStack.push(1);


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.content_frame, new ScheduleEquipasHorizontal())
                        .addToBackStack(null)
                        .commit();

                MainActivity.fragStack.push(1);

            }
        });

        return myView;
    }
}
