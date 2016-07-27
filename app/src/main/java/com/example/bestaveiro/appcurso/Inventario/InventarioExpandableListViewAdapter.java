package com.example.bestaveiro.appcurso.Inventario;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bestaveiro.appcurso.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Ricardo on 26/06/2016.
 */
public class InventarioExpandableListViewAdapter extends BaseExpandableListAdapter
{

    String className;
    LinkedHashMap<GrupoInventario, ArrayList<ItemInventario>> mapa;
    Context con;
    GrupoInventario arrayGrupo[];

    public InventarioExpandableListViewAdapter(LinkedHashMap<GrupoInventario, ArrayList<ItemInventario>> mapa, Context con)
    {
        className = this.getClass().getSimpleName();
        this.mapa = mapa;
        this.con = con;
        arrayGrupo = mapa.keySet().toArray(new GrupoInventario[mapa.keySet().size()]);
    }


    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
        Log.d(className, "notifyDataSetChanged");

        arrayGrupo = mapa.keySet().toArray(new GrupoInventario[mapa.keySet().size()]);
    }

    @Override
    public int getGroupCount()
    {
        return mapa.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return mapa.get(arrayGrupo[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return arrayGrupo[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return mapa.get(arrayGrupo[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return 0;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            LayoutInflater layInf = LayoutInflater.from(con);
            convertView = layInf.inflate(R.layout.inventario_group, null);

            viewHolder = new ViewHolder();
            viewHolder.textViewNome = (TextView) convertView.findViewById(R.id.inventario_group_text_view);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.inventario_group_image_view);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GrupoInventario tmpGrupo = arrayGrupo[groupPosition];

        // ir buscar bitmaps
        if(tmpGrupo.foto != null) viewHolder.imageView.setImageBitmap(null);
        viewHolder.textViewNome.setText(tmpGrupo.nome);

        //convertView.setPadding(0,0,0,60);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            LayoutInflater layInf = LayoutInflater.from(con);
            convertView = layInf.inflate(R.layout.inventario_child, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.inventario_child_image_view);
            viewHolder.textViewNome = (TextView) convertView.findViewById(R.id.inventario_child_text_view_nome);
            viewHolder.textViewQuantidade = (TextView) convertView.findViewById(R.id.inventario_child_text_view_quantidade);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ItemInventario tmpItem = mapa.get(arrayGrupo[groupPosition]).get(childPosition);

        // ir buscar bitmaps
        if(tmpItem.foto != null) viewHolder.imageView.setImageBitmap(null);
        viewHolder.textViewNome.setText(tmpItem.nome);
        viewHolder.textViewQuantidade.setText(String.format("%f",tmpItem.quantidade));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    class ViewHolder
    {
        ImageView imageView;
        TextView textViewNome;
        TextView textViewQuantidade;
    }
}
