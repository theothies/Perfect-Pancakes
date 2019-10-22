package com.example.perfectpancakes.dao;


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
    public void insert(Pancake... pancakes);

    @Update
    public void update(Pancake... pancakes);

    @Delete
    public void deletePancake(Pancake pancake);

    @Query("SELECT * FROM pancakes ORDER BY id DESC")
    public List<Pancake> getAll();

    @Query("SELECT * FROM pancakes WHERE title LIKE :title")
    public Pancake getPancake(String title);

    @Query("SELECT * FROM pancakes ORDER BY id DESC LIMIT 1")
    Pancake getLastPancake();
}
