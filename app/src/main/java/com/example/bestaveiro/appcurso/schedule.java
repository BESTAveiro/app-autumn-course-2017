package com.example.bestaveiro.appcurso;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by filipe on 13/05/2016.
 */
public class schedule extends Fragment{

    View myView;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getName(),"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.schedule, container, false);

        Log.d(getClass().getSimpleName(),"onCreateView");

        /*CalendarView cv = (CalendarView) myView.findViewById(R.id.calendarView);
        cv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d(getClass().getSimpleName(), "calendar clicked");
            }
        });

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                Log.d(getClass().getSimpleName(), "calendar day changed");
            }
        });*/

        return myView;
    }
}
