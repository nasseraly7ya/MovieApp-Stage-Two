package com.example.leatestmovies_project2;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Json {
    String Original_Title_key = "original_title";
    String poster_path_key = "poster_path";
    String release_date_key = "release_date";
    String overview_key = "overview";
    String vote_average_key = "vote_average";


    List<movie> movies= new ArrayList<>();
    private String theMovieDB="api.themoviedb.org";
    private String path="/3/movie";
    private String apikey="a604e2aff83532e590c126dfa7df1b6a";
    private String sortType;

    public Json(String sortType) {

        this.sortType=sortType;

    }

        public String buildURL ()  {

        Uri uri =Uri.parse(theMovieDB+path).buildUpon()
                .scheme("https")
                .appendPath(sortType)
                .appendQueryParameter("api_key",apikey)
                .appendQueryParameter("page",String.valueOf(1))
                .build();


        return uri.toString();
    }



    public List<movie> extractData(String json) throws JSONException {
         String original_title;
         String image ;
         String plot;
         double rating;
         String release_date;

         if (json != null) {
             JSONObject root = new JSONObject(json);
             JSONArray results = root.getJSONArray("results");
             int i = 0;
             int lenth = results.length();
             while (i < lenth) {
                 JSONObject movieItem = results.getJSONObject(i);


                 original_title = movieItem.getString(Original_Title_key);
                 image = movieItem.getString(poster_path_key);
                 release_date = movieItem.getString(release_date_key);
                 plot = movieItem.getString(overview_key);
                 rating = movieItem.getDouble(vote_average_key);

                 movie movie = new movie(original_title, image, plot, rating, release_date);
                 movies.add(movie);

                 i++;
             }
         }


        return movies;
    }

    public String connection () throws IOException {
       URL url = new URL(buildURL());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();


try{
    connection.setRequestMethod("GET");
    connection.setConnectTimeout(15000);
    connection.setReadTimeout(10000);
    connection.connect();

    if (connection.getResponseCode() == 200) {
        InputStream inputStream = connection.getInputStream();

        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();

        if (hasInput) {
            String data = scanner.next();

            return data;

        }else return null;


    }
}catch (IOException e){
}
        finally{
    connection.disconnect();

        }
        return null;

    }


}
