package com.example.leatestmovies_project2.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.leatestmovies_project2.favMovieModel;

import java.util.List;

@Dao
public interface favoritesMoviesDAO {
    @Query("SELECT * FROM favMovieModel")
    public List<favMovieModel> getFavMovies();

    @Query("SELECT * FROM favMovieModel WHERE imageID =:id")
    public favMovieModel getMovie(String id);

    @Insert
    void insert(favMovieModel movieModel);

    @Query("DELETE  FROM favMovieModel WHERE imageID =:id")
    void delete(String id);







}
