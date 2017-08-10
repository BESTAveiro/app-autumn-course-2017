package com.example.bestaveiro.appcurso.OrganizersHandbook;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by Ricardo on 04/06/2016.
 */
public class ShowPopUp extends Fragment
{
    PopupWindow popUp;
    LinearLayout layout;
    TextView tv;
    LayoutParams params;
    LinearLayout mainLayout;
    Button but;
    boolean click = true;
    Context con;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        con = getActivity().getApplicationContext();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        popUp = new PopupWindow(con);
        layout = new LinearLayout(con);
        mainLayout = new LinearLayout(con);
        tv = new TextView(con);
        but = new Button(con);
        but.setText("Click Me");
        but.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (click) {
                    //popUp.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
                    //popUp.retrieve(0, 0, 300, 300);
                    popUp.showAsDropDown(but);
                    Log.d("ShowPopUp", String.format("is popup showing? %b", popUp.isShowing()));
                    click = false;
                } else {
                    popUp.dismiss();
                    click = true;
                }
            }

        });
        params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Hi this is a sample text for popup window");
        layout.addView(tv, params);
        popUp.setContentView(layout);
        popUp.setWidth(LayoutParams.WRAP_CONTENT);
        popUp.setHeight(LayoutParams.WRAP_CONTENT);
        // popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        mainLayout.addView(but, params);

        return mainLayout;
    }
}
