package com.ua.bestaveiro.autumncourse2017.Drinking_G2K;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.ua.bestaveiro.autumncourse2017.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drinking_and_get2know_games);

        Typeface tf=Typeface.createFromAsset(getAssets(), "fonts/neo.ttf");
        TextView tv=(TextView) findViewById(R.id.DrinkingGameFont);
        tv.setTypeface(tf);
        TextView tv2=(TextView) findViewById(R.id.Get2KnowFont);
        tv2.setTypeface(tf);
    }

    public void changeDrinkingGame(View view){
        Intent intent= new Intent(this, DrinkingGame.class);
        startActivity(intent);
    }

    public void changeGet2Know(View view){
        Intent intent= new Intent(this, Get2Know.class);
        startActivity(intent);
    }
}
