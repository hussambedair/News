package com.example.android.mynews;

import android.app.Application;

import com.example.android.mynews.DataBases.MyDataBase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyDataBase.init(this);
    }
}
