package bestaveiro.autumncourse2017.Schedule;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bestaveiro.autumncourse2017.R;

import bestaveiro.autumncourse2017.MainActivity;


public class ScheduleEquipas extends Fragment
{

    View myView1;
    FragmentManager fragmentManager;
    FragmentManager fragmentManager2;
    FloatingActionButton fab;
    FloatingActionButton fab2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        myView1 = inflater.inflate(R.layout.horario_equipas, container, false); // Define o layout

        fab = (FloatingActionButton) myView1.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ScheduleEvento fragment = new ScheduleEvento();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                MainActivity.fragStack.push(1);

            }
        });

        fab2 = (FloatingActionButton) myView1.findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager2 = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager2.beginTransaction();

                ScheduleEquipasHorizontal fragment = new ScheduleEquipasHorizontal();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                MainActivity.fragStack.push(1);

            }
        });

        return myView1;
    }

}

