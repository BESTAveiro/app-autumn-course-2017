package com.example.bestaveiro.appcurso.Schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bestaveiro.appcurso.R;


public class ScheduleEvento extends Fragment
{

    View myView1;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        myView1 = inflater.inflate(R.layout.horario_evento, container, false);

        image = (ImageView) myView1.findViewById(R.id.imageView1);
//        image.setRotation(image.getRotation()+ 90);

        return myView1;
    }

}
