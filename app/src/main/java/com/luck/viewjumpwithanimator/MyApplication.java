package com.luck.viewjumpwithanimator;

import android.app.Application;
import android.os.Handler;

/*************************************************************************************
 * Module Name:
 * Description:
 * Author: 李桐桐
 * Date:   2019/4/12
 *************************************************************************************/
public class MyApplication extends Application {
    private static MyApplication mInstance = null;

    public static MyApplication getInstance() {
        return mInstance;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
