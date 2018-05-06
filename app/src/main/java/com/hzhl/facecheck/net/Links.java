/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.hzhl.facecheck.net;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/9/28.
 */

public interface Links {

    /**
     * 上传图片
     *
     * @param parts
     * @return
     */
    @Multipart
    @POST("user/login")
    Call<ResponseBody> userLogin(@Part List<MultipartBody.Part> parts);

    /**
     * 添加技术
     *
     * @param userInfoId
     * @param userSkillId
     * @param skillTypeId
     * @return
     */
    @POST("/yetdwell/skill/addSkill.do")
    Call<ResponseBody> addSkill(@Query("userInfoId") int userInfoId,
                                @Query("userSkillId") int userSkillId,
                                @Query("skillTypeId") int skillTypeId);

}
