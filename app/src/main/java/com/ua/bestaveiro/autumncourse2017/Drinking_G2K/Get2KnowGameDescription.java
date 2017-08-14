package com.ua.bestaveiro.autumncourse2017.Drinking_G2K;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.ua.bestaveiro.autumncourse2017.R;

public class Get2KnowGameDescription extends AppCompatActivity {

    String[] all_get2know_names;

    String[] all_min_players;

    String[] all_max_players;

    String[] all_materials;

    String[] all_descriptions;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get2_know_game_description);

        all_get2know_names=getResources().getStringArray(R.array.all_get2Know_games);

        all_min_players=getResources().getStringArray(R.array.all_min_players_g2k);

        all_max_players=getResources().getStringArray(R.array.all_max_players_g2k);

        all_materials=getResources().getStringArray(R.array.all_materials_g2k);

        all_descriptions=getResources().getStringArray(R.array.all_descriptions_g2k);

        position=getIntent().getIntExtra("position",0);

        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/regular.ttf");
        Typeface tf2=Typeface.createFromAsset(getAssets(),"fonts/light.ttf");
        //Botão
        Button bt=(Button) findViewById(R.id.buttonBeerRoulette);
        bt.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/neo.ttf"));
        bt.setText(all_get2know_names[position]);

        //texto estatico
        TextView tv1=(TextView) findViewById(R.id.MinPlayers);
        tv1.setTypeface(tf);
        TextView tv2=(TextView) findViewById(R.id.MaxPlayers);
        tv2.setTypeface(tf);
        TextView tv3=(TextView) findViewById(R.id.Materials);
        tv3.setTypeface(tf);

        //Texto não estatico!
        TextView tv4=(TextView) findViewById(R.id.MinPlayers2);
        tv4.setTypeface(tf2);
        tv4.setText(all_min_players[position]);

        TextView tv5=(TextView) findViewById(R.id.MaxPlayers2);
        tv5.setTypeface(tf2);
        tv5.setText(all_max_players[position]);

        TextView tv6=(TextView) findViewById(R.id.Materials2);
        tv6.setTypeface(tf2);
        tv6.setText(all_materials[position]);

        TextView tv7=(TextView) findViewById(R.id.Description);
        tv7.setTypeface(tf2);
        tv7.setText(all_descriptions[position]);
    }
}
