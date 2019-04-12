package com.luck.viewjumpwithanimator;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/3/6
 *************************************************************************************/
public class ScreenUtils {
    public static final String TAG = "ScreenUtils";
    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_HEIGHT = 0;


    public static int getScreenWidth() {
        if (SCREEN_WIDTH == 0) {
            readScreenSize();
        }

        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        if (SCREEN_HEIGHT == 0) {
            readScreenSize();
        }
        return SCREEN_HEIGHT;
    }

    private static void readScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        boolean isFlag = (MyApplication.getInstance().getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        Log.d(TAG, "screen width " + SCREEN_WIDTH + " height " + SCREEN_HEIGHT + " " + isFlag);
        if (SCREEN_HEIGHT < SCREEN_WIDTH && !isFlag) {
            SCREEN_HEIGHT = SCREEN_HEIGHT ^ SCREEN_WIDTH;
            SCREEN_WIDTH = SCREEN_HEIGHT ^ SCREEN_WIDTH;
            SCREEN_HEIGHT = SCREEN_HEIGHT ^ SCREEN_WIDTH;
        }
    }
}
