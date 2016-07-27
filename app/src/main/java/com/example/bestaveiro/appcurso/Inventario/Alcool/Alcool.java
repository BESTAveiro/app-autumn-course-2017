package com.example.bestaveiro.appcurso.Inventario.Alcool;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bestaveiro.appcurso.Inventario.ItemInventario;
import com.example.bestaveiro.appcurso.R;

import java.util.ArrayList;

public class Alcool extends Fragment
{
    public static String className = "Álcool";
    static CoordinatorLayout coordinatorLayout;
    static Snackbar snackbar;
    static Toolbar toolbar;
    static TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(className, "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_alcool, container, false);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);


        final RecyclerView rv= (RecyclerView) myView.findViewById(R.id.alcool_RecyclerView);
        final AlcoolAdapter alcoolAdapter = new AlcoolAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(alcoolAdapter);


        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        final SwipeRefreshLayout srl = (SwipeRefreshLayout) myView.findViewById(R.id.alcool_swipe);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Log.d(className, "onRefresh chamado");
                alcoolAdapter.update();

                srl.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        srl.setRefreshing(false);
                    }
                }, 1200);
            }
        });
        return myView;
    }

    class AlcoolAdapter extends RecyclerView.Adapter<AlcoolAdapter.CustomViewHolder>
    {
        ArrayList<ItemInventario> alcoolArr = new ArrayList<>();

        public class CustomViewHolder extends RecyclerView.ViewHolder
        {
            ImageView imageView;
            TextView textViewNome;
            TextView textViewQuantidade;
            ImageButton changeButton;

            public CustomViewHolder(View v)
            {
                super(v);
            }
        }

        public AlcoolAdapter()
        {
            //ir buscar as cenas à base de dados e preencher a ArrayList
            //primeiro ir buscar à bd local e se estiver ligado à net ir buscar à remota e atualizar
            // a arrayList e a bd local
            //mostrar um aviso se não conseguir buscar info da remota
            Log.d(className, "construtor adapter chamado");
            alcoolArr = AlcoolLocalDB.retrieveAll();
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layInf = LayoutInflater.from(getActivity().getBaseContext());
            View convertView = layInf.inflate(R.layout.inventario_child, null);

            CustomViewHolder customViewHolder = new CustomViewHolder(convertView);
            customViewHolder.imageView = (ImageView) convertView.findViewById(R.id.inventario_child_image_view);
            customViewHolder.textViewNome = (TextView) convertView.findViewById(R.id.inventario_child_text_view_nome);
            customViewHolder.textViewQuantidade = (TextView) convertView.findViewById(R.id.inventario_child_text_view_quantidade);
            customViewHolder.changeButton = (ImageButton) convertView.findViewById(R.id.change_button);

            return customViewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, final int position)
        {
            ItemInventario tmpItem = alcoolArr.get(position);

            // ir buscar bitmaps
            if(tmpItem.foto != null)
            {
                //viewHolder.imageView.setImageResource(tmpItem.foto);
                Glide.with(Alcool.this).load(tmpItem.foto).into(holder.imageView);
            }
            holder.textViewNome.setText(tmpItem.nome);
            holder.textViewQuantidade.setText(String.format("%.2f",tmpItem.quantidade));
            holder.changeButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // criar um alert dialog em que dê para alterar a quantidade e atualizar isso nas bases de dados
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Atualiza a Quantidade");
                    final EditText edText = new EditText(getActivity());
                    edText.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    builder.setView(edText);
                    builder.setPositiveButton("OK", null);
                    builder.setNegativeButton("Cancelar", null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            if (snackbar != null) snackbar.dismiss();
                        }
                    });
                    final AlertDialog ad = builder.create();
                    ad.setOnShowListener(new DialogInterface.OnShowListener()
                    {
                        @Override
                        public void onShow(DialogInterface dialog)
                        {
                            ad.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    float quantidade;
                                    try
                                    {
                                        quantidade = Float.parseFloat(edText.getText().toString());
                                        // atualizar a base de dados local e a remota
                                        AlcoolLocalDB.update(alcoolArr.get(position).nome, quantidade);
                                        ad.dismiss();
                                        update();
                                    } catch (Exception ex)
                                    {
                                        ex.printStackTrace();
                                        //mostrar um snackbar a dizer que tens de introduzir um número
                                        snackbar = Snackbar.make(coordinatorLayout, "Número Inválido", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                }
                            });
                        }
                    });
                    ad.show();
                    //AlcoolLocalDB.update();
                }
            });
        }

        public void update()
        {
            //ir buscar as cenas à base de dados e preencher a ArrayList
            //primeiro ir buscar à bd local e se estiver ligado à net ir buscar à remota e atualizar
            // a arrayList e a bd local
            //mostrar um aviso se não conseguir buscar info da remota
            alcoolArr = AlcoolLocalDB.retrieveAll();

            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public int getItemCount()
        {
            return alcoolArr.size();
        }

    }

}
