package com.example.bestaveiro.appcurso;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.view.View;

/**
 * Created by Ricardo on 28/06/2016.
 */
public class StaticMethods
{
    public static void removeTabLayout(Activity act)
    {
        TabLayout tabs = (TabLayout) act.findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);
    }

    public static void removeFAB(Activity act)
    {
        FloatingActionButton fab = (FloatingActionButton) act.findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
    }

    public static FloatingActionButton showFAB(Activity act)
    {
        FloatingActionButton fab = (FloatingActionButton) act.findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        return fab;
    }
}
