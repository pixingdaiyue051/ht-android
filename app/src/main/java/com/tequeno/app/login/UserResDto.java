package com.tequeno.app.login;

import java.util.List;

public class UserResDto {

    public final static String TAG = "UserDto";

    public final static String TAG_LIST = "UserDto_List";

    public final static String TAG_PAGE = "UserDto_Page";

    public Long id;
    public String username;
    public Integer status;
    public List<Long> roles;
    public List<Long> structures;
    public List<Long> tenantIdList;
}