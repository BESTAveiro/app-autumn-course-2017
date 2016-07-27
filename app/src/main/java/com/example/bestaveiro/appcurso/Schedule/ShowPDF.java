package com.example.bestaveiro.appcurso.Schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bestaveiro.appcurso.R;

import java.util.Date;

public class ShowPDF extends Fragment
{

    //http://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
    public static ShowPDF newInstance(Date date)
    {
        ShowPDF tmp = new ShowPDF();

        Bundle args = new Bundle();
        args.putSerializable("dateToShow", date);
        tmp.setArguments(args);

        Log.d("ShowPDF", BundleArguments.dateToShow.st);
        Log.d("ShowPDF", date.toString());

        return tmp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getArguments().getSerializable("dateToShow");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.schedule_show_pdf, container, false);
    }

    public enum BundleArguments
    {
        dateToShow("dateToShow");
        private final String st;

        BundleArguments(String st)
        {
            this.st = st;
        }
    }

}
