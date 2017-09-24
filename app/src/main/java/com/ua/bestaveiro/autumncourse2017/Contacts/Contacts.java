package com.ua.bestaveiro.autumncourse2017.Contacts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ua.bestaveiro.autumncourse2017.R;

/**
 * Created by joao on 06/09/2017.
 */

public class Contacts extends Fragment {

    View myView;
    ListView listview1;
    ListView listview2;
    String[] pessoas;
    String[] numero;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.contacts, container, false);
        getActivity().setTitle("Contacts"); // Nome no separador

        listview1 = (ListView) myView.findViewById(R.id.listview_contacts1);
        listview2 = (ListView) myView.findViewById(R.id.listview_contacts2);

        pessoas = getResources().getStringArray(R.array.nome_das_pessoas);
        numero = getResources().getStringArray(R.array.telemoveis_das_pessoas);

        ArrayAdapter<String> ListViewAdapter1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, pessoas);

        ArrayAdapter<String> ListViewAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, numero);

        listview1.setAdapter(ListViewAdapter1);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    Intent intent = new Intent(getActivity(), People.class);
                    intent.putExtra("position", position);
                    startActivity(intent);


            }
        });

        listview2.setAdapter(ListViewAdapter2);
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener(){

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


