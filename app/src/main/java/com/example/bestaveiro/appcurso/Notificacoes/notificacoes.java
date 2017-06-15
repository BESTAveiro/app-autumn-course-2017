package com.example.bestaveiro.appcurso.Notificacoes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bestaveiro.appcurso.R;
import com.example.bestaveiro.appcurso.StaticMethods;

import java.util.ArrayList;

/**
 * Created by filipe on 13/05/2016.
 */
public class notificacoes extends Fragment
{

    View myView;
    String className = "Notificações";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        StaticMethods.removeTabLayout(getActivity());
        myView = inflater.inflate(R.layout.notificacoes, container, false);
        StaticMethods.removeFAB(getActivity());
        getActivity().setTitle(className);

        Adapter ad = new Adapter();
        RecyclerView rv = (RecyclerView) myView.findViewById(R.id.notificacoes_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(ad);

        return myView;
    }


    class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder>
    {
        ArrayList<Notificacao> notArray = new ArrayList<>();

        public Adapter()
        {
            notArray = NotificacoesDB.retrieveAll();
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layInf = LayoutInflater.from(getActivity().getBaseContext());
            View convertView = layInf.inflate(R.layout.notificacao, null);

            CustomViewHolder customViewHolder = new CustomViewHolder(convertView);
            customViewHolder.textViewData = (TextView) convertView.findViewById(R.id.notData);
            customViewHolder.textViewTitulo = (TextView) convertView.findViewById(R.id.notTitulo);
            customViewHolder.textViewTexto = (TextView) convertView.findViewById(R.id.notTexto);

            return customViewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position)
        {
            Notificacao not = notArray.get(position);

            holder.textViewData.setText(not.getNicelyFormattedDate());
            holder.textViewTitulo.setText(not.titulo);
            holder.textViewTexto.setText(not.texto);
        }

        @Override
        public int getItemCount()
        {
            return notArray.size();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewData;
            TextView textViewTitulo;
            TextView textViewTexto;

            public CustomViewHolder(View v)
            {
                super(v);
            }
        }
    }


}
