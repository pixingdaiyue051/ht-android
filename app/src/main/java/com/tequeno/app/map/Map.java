package com.tequeno.app.map;

import java.util.Date;

public class Map {

    public Long _id;
    public String target;
    public double longitude;
    public double latitude;
    public Date createTime;

    public final static class Table {
        public static final String TABLE_NAME = "map";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TARGET = "target";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_CREATE_TIME = "create_time";
    }
}