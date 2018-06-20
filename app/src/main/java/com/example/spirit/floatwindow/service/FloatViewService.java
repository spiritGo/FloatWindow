package com.example.spirit.floatwindow.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.spirit.floatwindow.R;
import com.example.spirit.floatwindow.utils.Util;

public class FloatViewService extends Service {

    private int videoWidth;
    private int videoHeight;
    private WindowManager wm;
    private View view;
    private int statusHeight;
    private WindowManager.LayoutParams params;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Point windowSize = Util.getWindowSize(this);
        videoWidth = Math.round(windowSize.x / 1.6f);
        videoHeight = Math.round(windowSize.y / 4f);
        System.out.println(videoWidth + ", " + videoHeight);

        showFloatView();
    }

    private void showFloatView() {
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (wm != null) {
            view = LayoutInflater.from(this).inflate(R.layout.float_window_layout, null);
            statusHeight = Util.getStatusHeight(getApplicationContext());
            params = new WindowManager.LayoutParams();
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager
                    .LayoutParams.FLAG_NOT_FOCUSABLE;
            params.gravity = Gravity.TOP | Gravity.START;
            params.format = PixelFormat.RGBA_8888;
            params.width = videoWidth;
            params.height = videoHeight;
            wm.addView(view, params);
            System.out.println("OK" + statusHeight);

            view.setOnTouchListener(new View.OnTouchListener() {

                private float startY;
                private float startX;

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getRawX();
                            startY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            params.x += event.getRawX()-startX;
                            params.y += event.getRawY()-startY;

                            wm.updateViewLayout(view,params);

                            startY = event.getRawY();
                            startX = event.getRawX();
                            break;
                        case MotionEvent.ACTION_UP:
                            System.out.println("ACTION_UP");
                            break;
                    }
                    return true;
                }
            });
        }
    }
}
