package com.example.leatestmovies_project2;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.leatestmovies_project2.Data.dataBase;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class favMoviesViewModel extends AndroidViewModel {
            dataBase dataBase;
            MutableLiveData<List<favMovieModel>> liveData =new MutableLiveData<>() ;
    public favMoviesViewModel(@NonNull Application application) {

        super(application);

        dataBase = com.example.leatestmovies_project2.Data.dataBase.getDataBase(application);

    }

    public LiveData<List<favMovieModel>> getLiveData (boolean fav,String type){
        if (!fav && type != null){
            new moviesAsynltask().execute(type);
            return liveData;
        }else {


            new favAsynktask().execute();
            Log.v("favasynk","yes");

            return liveData;
        }


    }

    private class favAsynktask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            liveData.postValue(dataBase.favoritesMoviesDAO().getFavMovies());
            Log.v("fav","true");
            return null;
        }

    }

    private class moviesAsynltask extends AsyncTask<String,Void,Void> {


        @Override
        protected Void doInBackground(String... strings) {
            Json json = new Json(strings[0],false,false);

            Log.v("Asynctask","yes");
            try {
                    List list = json.extractData(json.connection());

                liveData.postValue(list);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
