package com.example.bestaveiro.appcurso.Schedule;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bestaveiro.appcurso.MainActivity;
import com.example.bestaveiro.appcurso.R;

import java.util.Date;

/**
 * Created by Ricardo on 03/06/2016.
 */
public class ScheduleCalendarGridViewAdapter extends BaseAdapter
{
    Context con;
    String texts[];
    FragmentManager fragManager;

    public ScheduleCalendarGridViewAdapter(Context c, String texts[])
    {
        con = c;
        this.texts = texts;
        fragManager = ((AppCompatActivity) con).getSupportFragmentManager();
    }

    @Override
    public int getCount()
    {
        return texts.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(con).inflate(R.layout.schedule_calendar_item, null);
        }

        TextView day = (TextView) convertView.findViewById(R.id.schedule_calendar_day);

        day.setText(texts[position]);

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Adapter", String.format("%d clicked", position));

                fragManager.beginTransaction()
                        .replace(R.id.content_frame
                                , ShowPDF.newInstance(new Date()))
                        .addToBackStack(null)
                        .commit();

                MainActivity.fragStack.push(0);
                Log.d("entry count", fragManager.getBackStackEntryCount()+"");
            }
        });

        return convertView;
    }
}
