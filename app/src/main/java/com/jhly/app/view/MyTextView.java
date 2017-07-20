package com.jhly.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.jhly.app.api.ScreenUtil;

/**
 * Created by r on 2017/7/19.
 */

public class MyTextView extends TextView
{
    private Paint paint;
    private String showText = "我的车牌号：";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        DisplayMetrics screenSize = ScreenUtil.getScreenSize(getContext());
        int heightPixels = screenSize.heightPixels;
        int widthPixels = screenSize.widthPixels;
        if(mode == MeasureSpec.EXACTLY){
            widthMeasureSpec = widthPixels;
            heightMeasureSpec = heightPixels/2;
        }
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius = Math.min(getWidth()/3,getHeight()/3);
        paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.holo_blue_light));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius,paint);
//        Paint textPaint = new Paint();
//        textPaint.setStyle(Paint.Style.FILL);
//        textPaint.setTextSize(60);
//        textPaint.setColor(getResources().getColor(android.R.color.white));
//        textPaint.setTextAlign(Paint.Align.CENTER);
//        canvas.drawText(showText,0,showText.length(),radius,radius+showText.length()/4,textPaint);
        super.onDraw(canvas);
    }

}
