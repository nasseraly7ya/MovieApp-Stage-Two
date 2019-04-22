package com.example.leatestmovies_project2;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Json {
    /**
    put your API_KEY
    */
    private String API_key=null;
    private  boolean Review  ;
    private  boolean video;
    String Original_Title_key = "original_title";
    String poster_path_key = "poster_path";
    String release_date_key = "release_date";
    String overview_key = "overview";
    String vote_average_key = "vote_average";

//    https://api.themoviedb.org/3/movie/popular&apikey=a604e2aff83532e590c126dfa7df1b6a


    List<favMovieModel> movies= new ArrayList<>();
    List<Video> videos= new ArrayList<>();

    private String theMovieDB="api.themoviedb.org";
    private String path="/3/movie";
    private String apikey=API_key;
    private String sortType;
    private String id="id";
    private List<com.example.leatestmovies_project2.Review> reviews =new ArrayList<>();

    public Json(String sortType,boolean video,boolean review) {
        this.video = video;
        Review=review;
        this.sortType=sortType;

    }

        public String buildURL ()  {
            Uri uri=null;
            if (!video && !Review){
        uri =Uri.parse(theMovieDB+path).buildUpon()
                .scheme("https")
                .appendPath(sortType)
                .appendQueryParameter("api_key",apikey)
                .appendQueryParameter("page",String.valueOf(1))
                .build();

            Log.v("URL",uri.toString());}
            else if (video){
                uri =Uri.parse(theMovieDB+path).buildUpon()
                        .scheme("https")
                        .appendPath(sortType)
                        .appendPath("videos")
                        .appendQueryParameter("api_key",apikey)
                        .appendQueryParameter("page",String.valueOf(1))
                        .appendQueryParameter("total_results","3")
                        .build();

                Log.v("URL",uri.toString());
            }  else if (Review){
                uri =Uri.parse(theMovieDB+path).buildUpon()
                        .scheme("https")
                        .appendPath(sortType)
                        .appendPath("reviews")
                        .appendQueryParameter("api_key",apikey)
                        .appendQueryParameter("page",String.valueOf(1))
                        .appendQueryParameter("total_results","3")
                        .build();

                Log.v("URL",uri.toString());
            }
            return uri.toString();

        }





    public List<favMovieModel> extractData(String json) throws JSONException {
        String original_title;
        String image;
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

                    int id_ = movieItem.getInt(id);
                    original_title = movieItem.getString(Original_Title_key);
                    image = movieItem.getString(poster_path_key);
                    release_date = movieItem.getString(release_date_key);
                    plot = movieItem.getString(overview_key);
                    rating = movieItem.getDouble(vote_average_key);

                    favMovieModel movieModel = new favMovieModel(original_title, image, plot, rating, "Release Date : "+release_date);
                    movieModel.setMovieID(id_);
                    movies.add(movieModel);

                    i++;
                }
                return movies;
            }

        return null;
    }

        public List<Video> extractVideos(String json) throws  JSONException {

            if (json != null) {
                JSONObject root = new JSONObject(json);
                JSONArray results = root.getJSONArray("results");
                int i = 0;
                int lenth = results.length();


                while (i < lenth && i<3) {
                        JSONObject movieItem = results.getJSONObject(i);

                        Video video = new Video(movieItem.getString("name"), movieItem.getString("key"));
                        videos.add(video);
i++;
                    }
                    Log.v("lenth",String.valueOf(videos.size()));

                    return videos;


            }
        return null;

    }
    public List<Review> extractReviews(String json) throws  JSONException {

        if (json != null) {
            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray("results");
            int i = 0;
            int lenth = results.length();


            while (i < lenth && i<3) {
                JSONObject movieItem = results.getJSONObject(i);

                Review review = new Review(movieItem.getString("author"), movieItem.getString("content"),movieItem.getString("url"));
                reviews.add(review);
                i++;
            }
            Log.v("lenth",String.valueOf(reviews.size()));

            return reviews;


        }
        return null;

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
