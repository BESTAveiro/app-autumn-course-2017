package com.example.bestaveiro.appcurso.OrganizersHandbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bestaveiro.appcurso.R;
import com.github.barteksc.pdfviewer.PDFView;

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

        PDFView pdfView = (PDFView) myView.findViewById(R.id.pdfView);
        pdfView.fromAsset("example.pdf").defaultPage(dayToShow).load();


        return myView;
    }

}
