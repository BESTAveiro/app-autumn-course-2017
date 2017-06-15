package com.example.bestaveiro.appcurso;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by filipe on 13/05/2016.
 */
public class pontuacao extends Fragment {

    ViewGroup myView;
    ListView lvteams;
    ArrayList<SingleRow> teams;
    CustomAdapter2 adapter;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = (ViewGroup) inflater.inflate(R.layout.pontuacao, container, false);

        StaticMethods.removeTabLayout(getActivity());
        StaticMethods.removeFAB(getActivity());

        lvteams = (ListView) myView.findViewById(R.id.listViewt);

        teams= new ArrayList<>();
        final String[] Teams = new String[] {"team1", "team2","team3","team4","team5"};
        int[] Points = new int[5];
        for (int i=0; i<5 ; i++){
            teams.add(new SingleRow(Teams[i],Points[i]));
        }

        adapter =new CustomAdapter2(getActivity(),teams);
        lvteams.setAdapter(adapter);

        //Conecção com firebase
        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        final DatabaseReference myRef2 = database2.getReference();

        //Obter número total de pontos da equipa
        for (int i=0; i<Teams.length; i++) {
            final String te = Teams[i];
            final int finalI = i;
            teams.get(i).Points=0;
            myRef2.child(te).addValueEventListener(new ValueEventListener() {
                public int ss = 0;

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ss=0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Team tt = ds.getValue(Team.class);

                        int p = tt.getPoints();
                        ss = ss + p;
                        teams.get(finalI).Points = ss;
                        adapter.notifyDataSetChanged();

                    }



                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });

        }


        lvteams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition= position;
                //obter valor da linha que é clicada
                String itemValue = (String) Teams[position];
                //criar intent
                Intent i = new Intent(getActivity(), teamActivity.class);
                //adicionar ao intent informação necessária para a próxima atividade
                i.putExtra("teamName", itemValue);
                startActivity(i);
            }
        });

        return myView;
    }
}


class SingleRow
{
    String NomeEquipa;
    Integer Points;
    //int image;

    public SingleRow(String nomeEquipa, Integer pontos) {
        this.NomeEquipa = nomeEquipa;
        this.Points = pontos;
        //this.image = image;
    }
}
class CustomAdapter2 extends BaseAdapter
{

    Context c;
    ArrayList<SingleRow> teams;
    LayoutInflater inflater;



    public CustomAdapter2(Context c, ArrayList<SingleRow> teams) {
        this.c = c;
        this.teams = teams;
    }

    @Override
    public int getCount() {
        return teams.size();
    }

    @Override
    public Object getItem(int position) {
        return teams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater==null){

            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView==null){

            convertView=inflater.inflate(R.layout.listviewlayout,parent,false);

        }

        MyViewHolder1 holder=new MyViewHolder1(convertView);
        holder.nameTxt2.setText(teams.get(position).NomeEquipa);
        holder.pointsTxt2.setText(String.valueOf(teams.get(position).Points));



        return convertView;
    }
}
class MyViewHolder1
{

    TextView nameTxt2;
    TextView pointsTxt2;

    public MyViewHolder1(View itemView){

        nameTxt2= (TextView) itemView.findViewById(R.id.textViewnn);
        pointsTxt2= (TextView) itemView.findViewById(R.id.textViewpp);
    }
}

