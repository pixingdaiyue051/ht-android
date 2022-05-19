package com.tequeno.rdm.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tequeno.rdm.entity.Book;

/**
 * 数据库映射实例
 * entities 数据库中的表
 * exportSchema 数据库信息以json形式输出 需要在build.gradle设置文件目录
 */
@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {

    public abstract BookDao bookDao();
}