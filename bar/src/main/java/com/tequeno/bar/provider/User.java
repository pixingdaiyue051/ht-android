package com.tequeno.bar.provider;

public class User {

    public Long _id;
    public String name;

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                '}';
    }

    public final static class Table {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
    }
}
