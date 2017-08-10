package com.example.bestaveiro.appcurso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class HorarioEquipas extends Fragment
{

    View myView3;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        myView3 = inflater.inflate(R.layout.horario_equipas, container, false);

        image = (ImageView) myView3.findViewById(R.id.imageView2);
        image.setImageResource(R.mipmap.logo_ac);
        image.setRotation(image.getRotation()+ 90);
        return myView3;
    }

}

