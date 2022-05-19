package com.tequeno.rdm.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tequeno.rdm.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    int delete(Book book);

    @Query("SELECT * FROM BOOK")
    List<Book> queryAll();

}