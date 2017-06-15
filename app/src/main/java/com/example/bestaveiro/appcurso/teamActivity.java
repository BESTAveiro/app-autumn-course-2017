package com.example.bestaveiro.appcurso;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class teamActivity extends AppCompatActivity {

    TextView tvTeamName;
    ListView lvMembers;
    TextView tvTeamLeader;
    ArrayList<Team> mTeams = new ArrayList<>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        tvTeamName= (TextView) findViewById(R.id.tvTeamName);
        lvMembers = (ListView) findViewById(R.id.lvMembers);
        tvTeamLeader = (TextView) findViewById(R.id.tvTeamLeader);

        //obtençao da informação extra colocada no intent
        Bundle extras = getIntent().getExtras();

        //no caso de não ter chegado nenhuma informação extra0
        if (extras==null){
            tvTeamName.setText("deu merda");
        }


        final String value1 = extras.getString("teamName");

        if (value1.equals("team1")){
            tvTeamLeader.setText(value1);
        }else if (value1.equals("team2")){
            tvTeamLeader.setText("sfsfd");
        }else if (value1.equals("team3")){
            tvTeamLeader.setText("dsfsfd");
        }else if (value1.equals("team4")){
            tvTeamLeader.setText("dsfsfd");
        }else if (value1.equals("team5")){
            tvTeamLeader.setText("dsfsfd");
        }


        //se for recebida alguma informação
        if (value1 != null){

            tvTeamName.setText(value1);


            //Definir conecção com firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference(value1);

            adapter=new CustomAdapter(this,mTeams);
            lvMembers.setAdapter(adapter);

            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Team t= dataSnapshot.getValue(Team.class);
                    mTeams.add(t);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    mTeams.clear();
                    myRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            Team t= dataSnapshot.getValue(Team.class);
                            mTeams.add(t);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            lvMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent i = new Intent(teamActivity.this,pop.class);
                    //adicionar informação
                    //nome equipa
                    i.putExtra("equipa", value1);
                    //nome membro
                    Team member = (Team) mTeams.get(position);
                    String memberName =(String) member.getName();
                    i.putExtra("name",memberName);
                    startActivity(i);

                }
            });
        }
    }
}

