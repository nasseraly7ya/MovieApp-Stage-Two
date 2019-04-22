package com.example.leatestmovies_project2.Data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.leatestmovies_project2.favMovieModel;

@android.arch.persistence.room.Database(entities = favMovieModel.class, version = 1, exportSchema = false)


public abstract class  dataBase extends RoomDatabase {

    public abstract favoritesMoviesDAO favoritesMoviesDAO();

    private static dataBase DataBase;

    public static dataBase getDataBase(Context context) {
        if (DataBase == null){
DataBase= Room.databaseBuilder(context,dataBase.class,"favMoviesDataBase")
        .fallbackToDestructiveMigration().build();

        }

        return DataBase;
    }
}
