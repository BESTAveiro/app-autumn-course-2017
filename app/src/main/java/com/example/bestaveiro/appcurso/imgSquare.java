package com.example.bestaveiro.appcurso;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Ricardo on 19/08/2016.
 */
public class imgSquare extends ImageView
{

    public imgSquare(Context context) {
        super(context);
    }

    public imgSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public imgSquare(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}




