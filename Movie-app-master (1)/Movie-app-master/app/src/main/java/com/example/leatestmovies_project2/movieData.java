package com.example.leatestmovies_project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.leatestmovies_project2.Data.dataBase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class movieData extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ImageView poster;
    ImageView starRate;
    ImageButton favourite;
    TextView title,polt,date,rate;
    executor executor = new executor();
    favMovieModel movieModel;
    Context getContext=this;
    ListView videosList;
    boolean Reviw = false;

    private favMovieModel movieModelfromDatabase;
    private java.util.List<Video> videosArray=new ArrayList<>();
    ListAdapter videosAdapter,reviewAdapter;
    private java.util.List<Review>reviewsArray = new ArrayList<Review>();
    private ListView reviewList;
    TextView error_video,error_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_data);

        movieModel= getIntent().getParcelableExtra("movie");
        new asynkTask().execute(movieModel);
        videosList=findViewById(R.id.videosList);
        reviewList=findViewById(R.id.ReviewList);
        error_review=findViewById(R.id.error_review);
        error_review.setVisibility(View.GONE);
        error_video=findViewById(R.id.error_video);
        error_video.setVisibility(View.GONE);



        poster = findViewById(R.id.img);
        starRate = findViewById(R.id.satrRate);
        title = findViewById(R.id.movietitle);
        polt = findViewById(R.id.polt);
        date = findViewById(R.id.date);
        rate = findViewById(R.id.Rate);
//
            Log.v("parcel",movieModel.getImageID());
        Picasso.get().load(movieModel.getImageLink()).into(poster);

        title.setText(movieModel.getOriginal_title());
        polt.setText(movieModel.getPlot());
        date.setText(movieModel.getRelease_date());
        rate.setText(String.valueOf(movieModel.getRating()));

        favourite=findViewById(R.id.imgFav);





                favourite.setOnClickListener(this);
            videosList.setOnItemClickListener(this);
            reviewList.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (movieModel.isFavorite() != -1) {
            Log.v("movie", "delete  Done");
            movieModel.setisFavorite(-1);
            executor.delete(this, movieModel);
            favourite.setImageResource(R.drawable.unliked);
        } else {
            Log.v("movie", "insert  Done");
            movieModel.setisFavorite(1);

            executor.insert(this, movieModel);
            favourite.setImageResource(R.drawable.liked);

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Adapter d =parent.getAdapter();
        Intent trailer=null;
        if (d == videosAdapter) {
             trailer = new Intent(Intent.ACTION_VIEW, (buildVideo(videosArray.get(position).getKey())));
        }else {
             trailer = new Intent(Intent.ACTION_VIEW, reviewsArray.get(position).getUrl());

        }
        startActivity(trailer);

    }

    private class asynkTask extends AsyncTask<favMovieModel,Void,Void> {


        @Override
        protected Void doInBackground(favMovieModel... favMovieModels) {

            movieModelfromDatabase= dataBase.getDataBase(getContext).favoritesMoviesDAO().getMovie(favMovieModels[0].getImageID());

            Json json = new Json(String.valueOf(movieModel.getMovieID()),true,false);
            Json reviews = new Json(String.valueOf(movieModel.getMovieID()),false,true);

            try {
              videosArray  = json.extractVideos(json.connection());
              reviewsArray = reviews.extractReviews(reviews.connection());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.v("ID",String.valueOf(movieModel.getMovieID()));

            if (movieModelfromDatabase != null){
            if(movieModelfromDatabase.isFavorite() !=-1) {
                favourite.setImageResource(R.drawable.liked);
            }}else {
                favourite.setImageResource(R.drawable.unliked);

            }
            Log.v("lenth",String.valueOf(videosArray.size()));

            Reviw=false;
                if (videosArray.size() !=0) {
                    videosAdapter = new movieData.ListAdapter(movieData.this, R.layout.liset_content, videosArray, false);

                    videosList.setAdapter(videosAdapter);
                }else{
                    error_video.setVisibility(View.VISIBLE);
                }
                    Reviw = true;
                    if (reviewsArray.size() != 0) {
                        reviewAdapter = new movieData.ListAdapter(movieData.this, R.layout.liset_content, reviewsArray, true);
                        reviewList.setAdapter(reviewAdapter);
                    }else {
                        error_review.setVisibility(View.VISIBLE);
                    }




        }
    }

    private Uri buildVideo(String key){
        //https://www.youtube.com/watch?v=VSqkL31w69k
        Uri uri = Uri.parse("www.youtube.com").buildUpon()
                .scheme("https").appendPath("watch")
                .appendQueryParameter("v",key)
                .build();
        Log.v("VideoURL ",uri.toString());
        return uri;
    }

        private class ListAdapter extends ArrayAdapter {
        private java.util.List list=new ArrayList();
        private boolean b;
        private TextView title=findViewById(R.id.Listtitle);

        public ListAdapter(Context context, int resource, java.util.List objects,boolean b) {
            super(context, resource, objects);
            list.addAll(objects);
            this.b=b;
        }

        @Override
        public View getView(int position,  View convertView,  ViewGroup parent) {
            View v =
            LayoutInflater.from(movieData.this).inflate(R.layout.liset_content,parent,false);
            title=v.findViewById(R.id.Listtitle);

                if (b){

                        title.setText("Review "+String.valueOf(position+1));
                    Reviw=true;
                }else{title.setText("Trailer "+String.valueOf(position+1));

            }

            return v;
            }
        }
    }

