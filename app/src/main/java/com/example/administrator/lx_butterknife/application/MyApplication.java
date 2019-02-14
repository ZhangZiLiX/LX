package com.example.administrator.lx_butterknife.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Author : 张自力
 * Created on time.
 *
 * 全局配置
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //1 初始化Fresco
        initFresco();

    }

    //1 初始化Fresco
    private void initFresco() {
        Fresco.initialize(this);
    }
}
