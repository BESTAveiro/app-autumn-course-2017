package com.ua.bestaveiro.autumncourse2017.OrganizersHandbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.ua.bestaveiro.autumncourse2017.R;
import com.ua.bestaveiro.autumncourse2017.StaticMethods;

/**
 * Created by filipe on 13/05/2016.
 */
public class OrganizersHandbook extends Fragment
{

    View myView;
    String className;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getSimpleName(),"onCreate");
        className = getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        StaticMethods.removeTabLayout(getActivity());

        getActivity().setTitle("Organizers' Handbook");

        myView = inflater.inflate(R.layout.schedule, container, false);

        Log.d(className,"onCreateView");


        String septemberDays[] = {"1 Friday", "2 Saturday", "3 Sunday" , "4 Monday" ,
                "5 Tuesday", "6 Wednesday", "7 Thursday ", "8 Friday", "9 Saturday"};



        GridView septemberGV = (GridView) myView.findViewById(R.id.gridViewCalendarSeptember);
        septemberGV.setAdapter(new OrganizersHandbookCalendarGridViewAdapter(getActivity(), septemberDays));


        return myView;
    }



}
