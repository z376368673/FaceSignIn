/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.hzhl.facecheck.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.hzhl.facecheck.R;


/**
 * 此登录方式为加强安全级别 密码+ 人脸：先使用用户名密码登录拿到uid，
 * 再使用uid和人脸 调用https://aip.baidubce.com/rest/2.0/face/v2/verify接口
 * <p>
 * <p>
 * 实际应用时，为了防止破解app盗取ak，sk（为您在百度的标识，有了ak，sk就能使用您的账户），
 * 建议把ak，sk放在服务端，移动端把相关参数传给您出服务端去调用百度人脸注册和比对服务，
 * 然后再加上您的服务端返回的登录相关的返回参数给移动端进行相应的业务逻辑
 */

public class SignInActivity extends BaseActivity implements View.OnClickListener {


    private TextView resultTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        findView();

    }

    private void findView() {
        resultTv = (TextView) findViewById(R.id.result_tv);

    }


    @Override
    public void onClick(View view) {

    }
}
