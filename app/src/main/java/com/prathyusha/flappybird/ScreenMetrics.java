package com.prathyusha.flappybird;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenMetrics {

    private static int mScreenWidth;
    private static int mScreenHeight;

    public static int dipResourceToPx(Context context, int resourceId) {
        return (int) context.getResources().getDimension(resourceId);
    }

    public static int getScreenWidth(Context context) {
        if (mScreenWidth > 0) {
            return mScreenWidth;
        } else {
            initWindowProp(context);
            return mScreenWidth;
        }
    }

    public static int getScreenHeight(Context context) {
        if (mScreenHeight > 0) {
            return mScreenHeight;
        } else {
            initWindowProp(context);
            return mScreenHeight;
        }
    }

    private static void initWindowProp(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
    }
}
