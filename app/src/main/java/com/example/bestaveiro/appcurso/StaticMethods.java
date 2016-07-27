package com.example.bestaveiro.appcurso;

import android.app.Activity;
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
}
