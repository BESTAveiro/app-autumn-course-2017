package com.ua.bestaveiro.autumncourse2017.OrganizersHandbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;

import com.ua.bestaveiro.autumncourse2017.R;


public class ShowPDF extends Fragment
{
    int dayToShow;

    //http://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
    public static ShowPDF newInstance(int day)
    {
        ShowPDF tmp = new ShowPDF();

        Bundle args = new Bundle();
        args.putInt("dateToShow", day);
        tmp.setArguments(args);

        Log.d("ShowPDF", day+"");

        return tmp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dayToShow = getArguments().getInt("dateToShow");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View myView = inflater.inflate(R.layout.schedule_show_pdf, container, false);

        int realDayToShow = 0;

        switch (dayToShow)
        {
            case 0:
                realDayToShow = 2;
                break;
            case 1:
                realDayToShow = 5;
                break;
            case 2:
                realDayToShow = 7;
                break;
            case 3:
                realDayToShow = 10;
                break;
            case 4:
                realDayToShow = 12;
                break;
            case 5:
                realDayToShow = 14;
                break;
            case 6:
                realDayToShow = 16;
                break;
            case 7:
                realDayToShow = 18;
                break;
            case 8:
                realDayToShow = 20;
                break;
        }

        PDFView pdfView = (PDFView) myView.findViewById(R.id.pdfView);
        pdfView.fromAsset("OH.pdf").defaultPage(realDayToShow).load();


        return myView;
    }

}
