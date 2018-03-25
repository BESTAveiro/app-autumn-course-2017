package com.ua.bestaveiro.autumncourse2017.Contacts;


import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ua.bestaveiro.autumncourse2017.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by joao on 06/09/2017.
 */

public class Contacts extends Fragment {

    View myView;
    String[] nomesDasPessoas;

    // Array of integers points to images stored in /res/drawable/
    int[] fotos_de_perfil = new int[]{
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
            R.drawable.foto_de_perfil,
    };
    static class ViewHolder {
        TextView textview;
        ImageView imageview;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.contacts, container, false);
        getActivity().setTitle("Contacts"); // Nome no separador

        nomesDasPessoas = getResources().getStringArray(R.array.nome_das_pessoas);



        ViewHolder holder = new ViewHolder();
        holder.imageview = (ImageView) myView.findViewById(R.id.imageView_contacts);
        holder.textview = (TextView) myView.findViewById(R.id.textView_contacts);
        myView.setTag(holder);


        // Each row in the list stores country name. and profile pic
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        int length = fotos_de_perfil.length;

        for(int i=0;i<length;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", "" + nomesDasPessoas[i]);
            hm.put("flag", Integer.toString(fotos_de_perfil[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag","txt"};

        // Ids of views in row_contacts
        int[] to = { R.id.imageView_contacts,R.id.textView_contacts};

        // Instantiating an adapter to store each items
        // R.layout.row_contacts defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(myView.getContext(), aList, R.layout.row_contacts, from, to);

        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) myView.findViewById(R.id.listview_contacts1);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    Intent intent = new Intent(getActivity(), People.class);
                    intent.putExtra("position", position);
                    startActivity(intent);


            }
        });
        return myView;
    }


}