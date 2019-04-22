package com.example.leatestmovies_project2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.leatestmovies_project2.Data.dataBase;

public class executor {
    boolean insert,delete;
    Context application;
    Context context;
    String id=null ;
    favMovieModel defulte=null;

    public void insert (Context context , favMovieModel movie){

        insert=true;
        application=context;
        movie.setisFavorite(1);
        new asynktask().execute(movie);
    }

    public void delete (Context application, favMovieModel movieModel){
        delete=true;
        this.application=application;
        movieModel.setisFavorite(-1);
        new asynktask().execute(movieModel);
    }
    public favMovieModel getMovie(Context context, String id){
        this.context = context;
        this.id=id;
         asynktask asynktask =new asynktask();
         asynktask.execute();
        this.id=null;
        return defulte;
    }


    private class asynktask extends AsyncTask<favMovieModel,Void,Void> {

        @Override
        protected Void doInBackground(favMovieModel... favMovieModels) {
                if (insert) {
                    dataBase.getDataBase(application).favoritesMoviesDAO().insert(favMovieModels[0]);
                    Log.v("insert","done");

                    insert=false;
                    return null;
                }else if (delete){
                    Log.v("databaseLength",String.valueOf(dataBase.getDataBase(application).favoritesMoviesDAO().getFavMovies().size()));

                    dataBase.getDataBase(application).favoritesMoviesDAO().delete(favMovieModels[0].getImageID());
                    Log.v("delete","done");
                    delete=false;
                    Log.v("databaseLength",String.valueOf(dataBase.getDataBase(application).favoritesMoviesDAO().getFavMovies().size()));

                    return null;
                }else if (id!=null){
                    favMovieModels[0]=dataBase.getDataBase(context.getApplicationContext()).favoritesMoviesDAO().getMovie(id);
                    if (favMovieModels[0] != null) {
                    }
                    return null;
                }
            return null;
        }


    }
}
