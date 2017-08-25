package com.ua.bestaveiro.autumncourse2017.Drinking_G2K;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ua.bestaveiro.autumncourse2017.R;

public class Drinking_G2K_fragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View myView;
        myView = inflater.inflate(R.layout.activity_main_drinking_and_get2know_games, null);

        Typeface tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/neo.ttf");
        TextView tv=(TextView) myView.findViewById(R.id.DrinkingGameFont);
        tv.setTypeface(tf);
        TextView tv2=(TextView) myView.findViewById(R.id.Get2KnowFont);
        tv2.setTypeface(tf);

        ((ImageButton) myView.findViewById(R.id.Get2KnowButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent intent= new Intent(getActivity(), Get2Know.class);
                startActivity(intent);
            }
        });

        ((ImageButton) myView.findViewById(R.id.DrinkingGameButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View view)
            {
                Intent intent= new Intent(getActivity(), DrinkingGame.class);
                startActivity(intent);
            }
        });

        return myView;
    }
}
