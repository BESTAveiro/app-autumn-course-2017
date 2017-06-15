package com.example.bestaveiro.appcurso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ricardo on 19/08/2016.
 */
public class CustomAdapter extends BaseAdapter
{

    Context c;
    ArrayList<Team> teams;
    LayoutInflater inflater;

    public CustomAdapter(Context c, ArrayList<Team> teams) {
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

            convertView=inflater.inflate(R.layout.lv_member,parent,false);

        }

        MyViewHolder2 holder=new MyViewHolder2(convertView);
        holder.nameTxt.setText(teams.get(position).getName());
        holder.pointsTxt.setText(String.valueOf(teams.get(position).getPoints()));



        return convertView;
    }

    class MyViewHolder2{

        TextView nameTxt;
        TextView pointsTxt;

        public MyViewHolder2 (View itemView){

            nameTxt= (TextView) itemView.findViewById(R.id.tvMemberName);
            pointsTxt= (TextView) itemView.findViewById(R.id.tvMemberPoints);
        }
    }
}





