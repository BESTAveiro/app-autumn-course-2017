package com.example.bestaveiro.appcurso.Inventario;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.bestaveiro.appcurso.R;
import com.example.bestaveiro.appcurso.StaticMethods;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Inventario_versao_expandable_list_view extends Fragment
{

    String className = "Inventário";
    LinkedHashMap<GrupoInventario, ArrayList<ItemInventario>> mapa;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(className);

        mapa = new LinkedHashMap<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        StaticMethods.removeTabLayout(getActivity());
        View tmp;
        // Inflate the layout for this fragment
        tmp = inflater.inflate(R.layout.fragment_inventario_versao_expandable_list_view, container, false);

        ExpandableListView expListView = (ExpandableListView)tmp.findViewById(R.id.expandableListViewInventario);
        populateLinkedHashMap();

        InventarioExpandableListViewAdapter adapter = new InventarioExpandableListViewAdapter(mapa, getActivity());
        expListView.setAdapter(adapter);
        //expListView.setDividerHeight(0);
        // http://stackoverflow.com/questions/21706231/put-on-the-right-the-indicator-of-an-expandablelistview-in-android
        // para mudar o indicator de sítio e de imagem
        // Adapter.notifyDataSetChanged();

        return tmp;
    }

    // ir buscar os dados à base de dados remota
    // eventualmente é preciso escrevê-los na base de dados local
    void populateLinkedHashMap()
    {
        GrupoInventario grupo1 = new GrupoInventario("Álcool", null);
        GrupoInventario grupo2 = new GrupoInventario("Comida", null);
        GrupoInventario grupo3 = new GrupoInventario("Outros", null);
        ArrayList<ItemInventario> arrListGrupo1 = new ArrayList<>();
        ArrayList<ItemInventario> arrListGrupo2 = new ArrayList<>();
        ArrayList<ItemInventario> arrListGrupo3 = new ArrayList<>();

        arrListGrupo1.add(new ItemInventario("a", null, 20, null));
        arrListGrupo1.add(new ItemInventario("a", null, 20, null));
        arrListGrupo2.add(new ItemInventario("f", null, 200, null));
        arrListGrupo3.add(new ItemInventario("b", null, (float)14.7, null));

        mapa.put(grupo1, arrListGrupo1);
        mapa.put(grupo2, arrListGrupo2);
        mapa.put(grupo3, arrListGrupo3);
    }

}
