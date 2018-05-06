/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.hzhl.facecheck.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hzhl.facecheck.R;
import com.hzhl.facecheck.model.UserInfo;
import com.hzhl.facecheck.net.APIServices;
import com.hzhl.facecheck.utils.ImageSaveUtil;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 100;
    private ImageView iv_logo;
    private EditText ed_user;
    private ImageView iv_head;
    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        setTagAndAlias();
        findView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void findView() {
        ed_user = (EditText) findViewById(R.id.ed_user);
        iv_head = (ImageView) findViewById(R.id.iv_head);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        iv_head.setOnClickListener(this);
        iv_logo.setOnClickListener(this);

        UserInfo userInfo = UserInfo.getUserInfo();
       String identify = userInfo.getIdentify();
       if (!TextUtils.isEmpty(identify)){
           ed_user.setText(identify);
       }
    }

    @Override
    public void onClick(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            return;
        }

        if (iv_head == v) {
            String id = ed_user.getText().toString();
            if (TextUtils.isEmpty(id)){
                Toast.makeText(this, "请输入你的编号", Toast.LENGTH_SHORT).show();
            }else {
                APIServices.getInstance().setIdentify(id);
                Intent intent = new Intent(HomeActivity.this, FaceLivenessExpActivity.class);
                startActivity(intent);


            }
        }
        if (iv_logo == v) {
            Intent intent = new Intent(HomeActivity.this, FaceLivenessExpActivity.class);
            startActivity(intent);
           /* Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);*/
        }
    }


    /**
     * JPush设置标签与别名
     */
    private void setTagAndAlias() {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        tags.add(UserInfo.getUserInfo().getIdentify());//设置tag
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setTags(this,0,tags);
        JPushInterface.setAlias(this,0,UserInfo.getUserInfo().getIdentify());
        // }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // filePath = data.getStringExtra("file_path");
            filePath = ImageSaveUtil.loadCameraBitmapPath(this, "head_tmp.jpg");
            //faceLogin(filePath);
        }
    }
}
