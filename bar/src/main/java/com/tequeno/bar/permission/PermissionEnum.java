package com.tequeno.bar.permission;

import android.Manifest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PermissionEnum {

    COMBINE(0, ""),

    READ_SMS(10, Manifest.permission.READ_SMS),
    SEND_SMS(11, Manifest.permission.SEND_SMS),
    SMS_GROUP(12, Manifest.permission_group.SMS),
    READ_CONTACTS(20, Manifest.permission.READ_CONTACTS),
    WRITE_CONTACTS(21, Manifest.permission.WRITE_CONTACTS),
    CONTACTS_GROUP(22, Manifest.permission_group.CONTACTS),


    READ_EXTERNAL_STORAGE(30, Manifest.permission.READ_EXTERNAL_STORAGE),
    ;

    private final Integer code;
    private final String permission;

    PermissionEnum(Integer code, String permission) {
        this.code = code;
        this.permission = permission;
    }

    public Integer getCode() {
        return code;
    }

    public String getPermission() {
        return permission;
    }

    public String mapPermission(int code) {
        return PermissionEnumHolder.map.get(code);
    }

    private static class PermissionEnumHolder {

        private static Map<Integer, String> map;

        static {
            map = new HashMap<>(11);
            Arrays.asList(PermissionEnum.values())
                    .forEach(item -> map.put(item.code, item.permission));
        }

    }

}