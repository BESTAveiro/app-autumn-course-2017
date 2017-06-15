package com.example.bestaveiro.appcurso.Inventario;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Ricardo on 16/08/2016.
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior
{

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.d("scroll", "onStartNestedScroll");
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        Log.d("scroll", "onNestedScroll");

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE)
        {
            child.hide();
        }
        else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE)
        {
            child.show();
        }
    }
}
