package com.crowdfunding.farming.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Jiang-gege
 * 2020/6/2416:32
 */
@Data
@Table(name = "t_user")
public class User {
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "CREATETIME")
    private Date createTime;

    @Column(name = "VERSION")
    private String  version;

    @Column(name = "USER_NAME")
    private String usrName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "OPEN_ID")
    private String openId;

    @Column(name = "PHONE_NUM")
    private String phoneNum;

    @Column(name = "LAST_LOGIN_TIME")
    private Date lastLoginTime;

    @Column(name = "WEIXIN_NICKNAME")
    private String weixinNickname;

    @Column(name = "FIELD1")
    private String field1;

    @Column(name = "FIELD2")
    private String field2;

    @Column(name = "FIELD3")
    private String field13;

    @Column(name = "FIELD4")
    private String field4;

    @Column(name = "FIELD5")
    private String field5;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "HEADIMGURL")
    private String headimgurl;

    @Column(name = "NICK_NAME")
    private String nickName;
}