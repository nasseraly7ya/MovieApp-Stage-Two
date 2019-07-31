package com.example.leatestmovies_project2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;


import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements movieDataDesigner.movieDataOnClickListner, AdapterView.OnItemSelectedListener {

    movieDataDesigner popMoviesAdapter ;



    public TextView error_massage,sortText;
    RecyclerView mRecycle ;
    ProgressBar progressBar ;
    Spinner sort;
    com.example.leatestmovies_project2.Data.dataBase dataBase;

    String popular ="popular";
    String top_rated="top_rated";
    String type;

    List<favMovieModel> popMovies = new ArrayList<>();
    List<favMovieModel> rateMovies = new ArrayList<>();


    List<favMovieModel> favMovies = new ArrayList<>();
    private boolean onRestart;
    private favMoviesViewModel viewModel;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sort=findViewById(R.id.sort);
        error_massage = findViewById(R.id.error);
        error_massage.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progrss);



        sortText = findViewById(R.id.sort_by);





        SpinnerAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.sort));
        sort.setAdapter(adapter);



        mRecycle= findViewById(R.id.Recycle);

        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,4);

    dataBase.getDataBase(this);

        mRecycle.setLayoutManager(manager);
        popMoviesAdapter = new movieDataDesigner(MainActivity.this,favMovies);
        mRecycle.setAdapter(popMoviesAdapter);

        sort.setOnItemSelectedListener(this);










    }







    public void onClickListner(favMovieModel movie,int pos) {
        Intent intent = new Intent(this,movieData.class);

        intent.putExtra("movie",movie);


        startActivity(intent);

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        this.position = position;
        Log.v("position : ", String.valueOf(position));
        viewModel = ViewModelProviders.of(this).get(favMoviesViewModel.class);
type=popular;
        if (position !=2) {
            favMovies.clear();
            popMoviesAdapter.notifyDataSetChanged();
            if (position == 1) {
                type = top_rated;

            }
//




            ( viewModel).getLiveData(false, type).observe(this, new Observer<List<favMovieModel>>() {
                @Override
                public void onChanged(@Nullable List<favMovieModel> favMovieModels) {

                    if (favMovieModels != null && favMovieModels.size() !=0) {
                        progressBar.setVisibility(View.GONE);

                        favMovies.clear();
                        popMoviesAdapter.imgArray.clear();
                        popMoviesAdapter.notifyDataSetChanged();
                        favMovies.addAll(favMovieModels);

                        popMoviesAdapter.notifyDataSetChanged();
                    }else {
                        error();
                    }
                }
            });

        } else if (position == 2 ) {
            favMovies.clear();
            popMoviesAdapter.notifyDataSetChanged();
            Log.v("favOnCeate", "yes");
            ((favMoviesViewModel) viewModel).getLiveData(true,null).observe(this, new Observer<List<favMovieModel>>() {
                @Override
                public void onChanged(@Nullable List<favMovieModel> favMovieModels) {
//                    Log.v(String.valueOf(favMovieModels.size()), favMovieModels.toString());
                    if (favMovieModels != null && favMovieModels.size() !=0) {
                        progressBar.setVisibility(View.GONE);
                        mRecycle.setVisibility(View.VISIBLE);
                        error_massage.setVisibility(View.GONE);
                        favMovies.clear();
                        favMovies.addAll(favMovieModels);
                        popMoviesAdapter.notifyDataSetChanged();
                    }else {
                        error();
                    }


                }
            });
//

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }








    public void error(){

            progressBar.setVisibility(View.GONE);

            mRecycle.setVisibility(View.GONE);
            error_massage.setText("No Data");
            error_massage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (position == 2) {
            favMovies.clear();
            popMoviesAdapter.notifyDataSetChanged();
            Log.v("fav", "yes");
            ( viewModel).getLiveData(true, null).observe(this, new Observer<List<favMovieModel>>() {
                @Override
                public void onChanged(@Nullable List<favMovieModel> favMovieModels) {
                    if (favMovieModels != null && favMovieModels.size() !=0) {
                        progressBar.setVisibility(View.GONE);

                        favMovies.clear();
                        favMovies.addAll(favMovieModels);
                        popMoviesAdapter.notifyDataSetChanged();

                    }else {
                        error();
                    }

                }
            });
        }


    }





}
