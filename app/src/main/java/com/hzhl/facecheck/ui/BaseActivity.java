/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.hzhl.facecheck.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by apple on 2018/4/20.
 */

public class BaseActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 100;
    public Context context;
    public Activity activity;

    /**
     * 跳转Activity
     * @param cla
     */
    public void starAct(Class cla){
        Intent intent = new Intent(this,cla);
        startActivity(intent);
    }

}
