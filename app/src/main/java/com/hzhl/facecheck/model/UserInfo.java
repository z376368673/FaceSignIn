/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.hzhl.facecheck.model;


import com.alibaba.fastjson.JSON;
import com.hzhl.facecheck.utils.PreferencesUtil;

/**
 * Created by apple on 2018/4/20.
 *
 * 用户信息
 */

public class UserInfo {
                
      private String sid;// "b4fd15d20d8c47f787915d57f98c8f11",
      private String identify;// "1",
      private String phone;// "",
      private String realName;// "",
      private String sex;// "",
      private String idCard;// "",
      private String authenticationImage;// "/photo/f65f4234673743d19cd88edfced22bbd.jpg",
      private String headImage;// "",
      private String addTime;// "2018-04-19 16:29:12",
      private String token;// "6fabad6d17954c20b1f03cf49f277dd0",
      private String streetId;// "4bf9d2815092439fae51b2bebd14accf"

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAuthenticationImage() {
        return authenticationImage;
    }

    public void setAuthenticationImage(String authenticationImage) {
        this.authenticationImage = authenticationImage;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }


    /**
     * 存储用户信息
     * @param json
     */
    public static void  setUserInfo(String json){
        PreferencesUtil.putString("USER",json);
    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserInfo getUserInfo(){
        String userJson = PreferencesUtil.getString("USER","");
        UserInfo userInfo =  JSON.parseObject(userJson,UserInfo.class);
        if (userInfo==null){
            return new UserInfo();
        }else {
            return userInfo;
        }
    }



}
