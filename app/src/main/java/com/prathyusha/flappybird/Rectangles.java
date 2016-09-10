package com.prathyusha.flappybird;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class Rectangles extends View {

    private Paint mGreen = new Paint();
    private Rect mTopRect = new Rect();
    private Rect mBottomRect = new Rect();
    private int mScreenWidth;
    private int mScreenHeight;
    private int mGroundHeight;
    private int mGap;

    public Rectangles(Context context) {
        super(context);

        mScreenWidth = ScreenMetrics.getScreenWidth(context);
        mScreenHeight = ScreenMetrics.getScreenHeight(context);
        mGroundHeight = ScreenMetrics.dipResourceToPx(context, R.dimen.ground_height);
        mGap = ScreenMetrics.dipResourceToPx(context, R.dimen.rectangle_gap);
    }

    public Rectangles(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScreenWidth = ScreenMetrics.getScreenWidth(context);
        mScreenHeight = ScreenMetrics.getScreenHeight(context);
        mGroundHeight = ScreenMetrics.dipResourceToPx(context, R.dimen.ground_height);
        mGap = ScreenMetrics.dipResourceToPx(context, R.dimen.rectangle_gap);
    }

    public Rectangles(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScreenWidth = ScreenMetrics.getScreenWidth(context);
        mScreenHeight = ScreenMetrics.getScreenHeight(context);
        mGroundHeight = ScreenMetrics.dipResourceToPx(context, R.dimen.ground_height);
        mGap = ScreenMetrics.dipResourceToPx(context, R.dimen.rectangle_gap);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        mGreen.setColor(Color.argb(255, 84, 243, 84));
        mGreen.setStyle(Paint.Style.FILL);

        float bottom = ((mScreenHeight - mGroundHeight) / 2) - (mGap / 2);
        float top = ((mScreenHeight - mGroundHeight) / 2) + (mGap / 2);

        float bottom2 = ((mScreenHeight - mGroundHeight) / 2) - (mGap / 2);
        float top2 = ((mScreenHeight - mGroundHeight) / 4) + (mGap / 4);

        mTopRect.set(0, 0, mScreenWidth, (int)bottom);
        canvas.drawRect(mTopRect, mGreen);

        mBottomRect.set(0, (int) top, mScreenWidth, (mScreenHeight - mGroundHeight));
        canvas.drawRect(mBottomRect, mGreen);

      /*  mTopRect.set(0, 0, mScreenWidth, (int)bottom2);
        canvas.drawRect(mTopRect2, mGreen);

        mBottomRect.set(0, (int) top2, mScreenWidth, (mScreenHeight - mGroundHeight));
        canvas.drawRect(mBottomRect2, mGreen);*/


    }
}