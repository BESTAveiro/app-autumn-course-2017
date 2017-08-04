package com.example.bestaveiro.appcurso.Schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.bestaveiro.appcurso.R;
import com.example.bestaveiro.appcurso.StaticMethods;

/**
 * Created by filipe on 13/05/2016.
 */
public class Schedule extends Fragment
{

    View myView;
    String className;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getSimpleName(),"onCreate");
        className = getClass().getSimpleName();
        getActivity().setTitle(className);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        StaticMethods.removeTabLayout(getActivity());

        myView = inflater.inflate(R.layout.schedule, container, false);

        Log.d(className,"onCreateView");

        String augustDays[] = {"23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String septemberDays[] = {"1", "2", "3"};

        GridView augustGV = (GridView) myView.findViewById(R.id.gridViewCalendarAugust);
        augustGV.setAdapter(new ScheduleCalendarGridViewAdapter(getActivity(), augustDays));

        GridView septemberGV = (GridView) myView.findViewById(R.id.gridViewCalendarSeptember);
        septemberGV.setAdapter(new ScheduleCalendarGridViewAdapter(getActivity(), septemberDays));

        return myView;
    }



}
