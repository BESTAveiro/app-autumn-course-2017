package com.example.bestaveiro.appcurso;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ricardo on 19/08/2016.
 */
public class pop extends Activity
{

    TextView MemberName;
    TextView txthelp;
    RadioGroup radioButtonGroup;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        MemberName = (TextView) findViewById(R.id.popName);
        btn = (Button) findViewById(R.id.button2);

        //obtençao da informação extra colocada no intent
        Bundle extras = getIntent().getExtras();
        //no caso de não ter chegado nenhuma informação extra0
        if (extras==null){
            MemberName.setText("deu merda");
        }

        final String Nome = extras.getString("name");
        final String Team = extras.getString("equipa");


        MemberName.setText(Nome);

        //dar pontos
        radioButtonGroup= (RadioGroup) findViewById(R.id.rg);



        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
                View radioButton = radioButtonGroup.findViewById(radioButtonID);
                final int idx = radioButtonGroup.indexOfChild(radioButton);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        final DatabaseReference myRef = database.getReference().child(Team).child(Nome).child("points");

                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Long pp=(Long) dataSnapshot.getValue();
                                if (idx==0){
                                    myRef.setValue(pp+10);
                                }else  if (idx==1){
                                    myRef.setValue(pp+15);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        finish();
                    }
                });


            }
        });

    }
}




