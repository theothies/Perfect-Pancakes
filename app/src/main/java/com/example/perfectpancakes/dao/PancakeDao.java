package com.example.perfectpancakes.dao;


import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.perfectpancakes.models.Pancake;

import java.util.List;

@Dao
public interface PancakeDao {

    @Insert
    void insert(Pancake... pancakes);

    @Update
    void update(Pancake... pancakes);

    @Delete
    void deletePancake(Pancake pancake);

    @Query("SELECT * FROM pancakes ORDER BY id DESC")
    List<Pancake> getAll();

    @Query("SELECT * FROM pancakes WHERE title LIKE :title")
    Pancake getPancake(String title);

    @Query("DELETE FROM pancakes WHERE title LIKE :title")
    void deleteByTitle(String title);

}
