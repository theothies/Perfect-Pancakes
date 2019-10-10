package com.example.perfectpancakes;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.perfectpancakes.dao.PancakeDao;
import com.example.perfectpancakes.models.Pancake;

@Database(entities={Pancake.class},version =2 , exportSchema = false)
public abstract class PancakeRoomDatabase extends RoomDatabase {

    public abstract PancakeDao pancakeDao();

    private static PancakeRoomDatabase INSTANCE;

    public static PancakeRoomDatabase getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PancakeRoomDatabase.class,"pancake_database2").build();
        }
        return INSTANCE;
    }
}
