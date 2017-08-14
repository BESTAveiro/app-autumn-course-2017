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


public class ScheduleEvento extends Fragment
{

    View myView3;
    //    ImageView image;
    FragmentManager fragmentManager;
    FloatingActionButton fab;
    FloatingActionButton fab2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        myView3 = inflater.inflate(R.layout.horario_evento, container, false);

        fragmentManager = getFragmentManager();

        fab = (FloatingActionButton) myView3.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.content_frame, new ScheduleEquipas())
                        .addToBackStack(null)
                        .commit();

                MainActivity.fragStack.push(1);

            }
        });

        fab2 = (FloatingActionButton) myView3.findViewById(R.id.fab2);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.content_frame, new ScheduleEventoHorizontal())
                        .addToBackStack(null)
                        .commit();

                MainActivity.fragStack.push(1);

            }
        });

        return myView3;
    }

}
