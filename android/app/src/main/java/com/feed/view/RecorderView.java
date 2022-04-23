package com.feed.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RecorderView  extends View {
    float radius = 100f;
    int rectSize = 35;
    int outerCircleStorkeWidth = 5;

    boolean isShowInnerCircle = true;
    boolean isShowInnerSquare = false;

    public RecorderView(Context context) {
        super(context);
        init(null);
    }

    public RecorderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }


    public RecorderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public RecorderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs){
        boolean isShowInnerCircle = true;
        boolean isShowInnerSquare = false;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX =  getWidth() / 2;
        int centerY =  getHeight() / 2;


        Paint outerCircle = new Paint();
        outerCircle.setStyle(Paint.Style.STROKE);
        outerCircle.setColor(Color.rgb(255, 0, 0));
        outerCircle.setAntiAlias(true);
        outerCircle.setStrokeWidth(outerCircleStorkeWidth);
        canvas.drawCircle(centerX,centerY,radius+20,outerCircle);


        if(isShowInnerCircle) {
            Paint innerCircle = new Paint();
            innerCircle.setColor(Color.rgb(255, 0, 0));
            innerCircle.setStyle(Paint.Style.FILL);
            innerCircle.setAntiAlias(true);
            canvas.drawCircle(centerX, centerY, radius, innerCircle);
        }

        if(isShowInnerSquare) {
            Rect rect = new Rect();
            rect.left = getWidth() / 2 - rectSize;
            rect.top = getHeight() / 2 - rectSize;
            rect.right = getWidth() / 2 + rectSize;
            rect.bottom = getHeight() / 2 + rectSize;
            Paint innerRect = new Paint();
            innerRect.setColor(Color.rgb(255, 0, 0));
            innerRect.setStyle(Paint.Style.FILL);
            innerRect.setAntiAlias(true);
            innerRect.setStrokeWidth(10);
            canvas.drawRect(rect, innerRect);
        }
    }



    public void start(){
        isShowInnerSquare = true;
        isShowInnerCircle = false;
        radius=115f;
        invalidate();
    }

    public void pause(){
        isShowInnerSquare = false;
        isShowInnerCircle = true;
        radius=100f;
        invalidate();
    }

}
