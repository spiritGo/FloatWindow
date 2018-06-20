package com.example.spirit.floatwindow.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class Util {
    public static Point getWindowSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = null;
        if (wm != null) {
            int width = wm.getDefaultDisplay().getWidth();
            int height = wm.getDefaultDisplay().getHeight();
            point = new Point();
            point.y = height;
            point.x = width;
        }
        return point;
    }

    public static int getStatusHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        int size = 0;
        if (resourceId > 0) {
            size = context.getResources().getDimensionPixelSize(resourceId);
        }
        return size;
    }
}
