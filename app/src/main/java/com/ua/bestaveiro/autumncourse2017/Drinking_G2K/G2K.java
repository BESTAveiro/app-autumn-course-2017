package com.ua.bestaveiro.autumncourse2017.Drinking_G2K;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ua.bestaveiro.autumncourse2017.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G2K extends Fragment
{

    static String className = "Get to Know";

    public G2K()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_g2_k, container, false);
    }

}
