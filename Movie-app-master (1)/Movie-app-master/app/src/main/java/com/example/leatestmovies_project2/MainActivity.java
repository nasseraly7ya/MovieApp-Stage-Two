package com.example.leatestmovies_project2;

import android.content.Intent;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    movieDataDesigner rateMoviesAdapter ;

    TextView error_massage,sortText;
    RecyclerView mRecycle ;
    Spinner sort;
    FetchData pop;

    String popular ="popular";
    String top_rated="top_rated";

    List<movie> popMovies = new ArrayList<>();
    List<movie> rateMovies = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sort=findViewById(R.id.sort);
        error_massage = findViewById(R.id.error);
        error_massage.setVisibility(View.GONE);

        sortText = findViewById(R.id.sort_by);


        pop = new FetchData();
        pop.execute();


        sort.setVisibility(View.GONE);





        SpinnerAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.sort));
        sort.setAdapter(adapter);



        mRecycle= findViewById(R.id.Recycle);

        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,4);



        mRecycle.setLayoutManager(manager);
        popMoviesAdapter = new movieDataDesigner(MainActivity.this);
        rateMoviesAdapter= new movieDataDesigner(MainActivity.this);
        mRecycle.setAdapter(popMoviesAdapter);


        sort.setOnItemSelectedListener(this);










    }







    public void onClickListner(movie movie) {
        Intent intent = new Intent(this,movieData.class);

        Bundle bundle = new Bundle();

       Seri seri = new Seri();
       seri.setOriginal_title(movie.getOriginal_title());
       seri.setImage(movie.getImage());
       seri.setPlot(movie.getPlot());
       seri.setRating(movie.getRating());
       seri.setRelease_date(movie.getRelease_date());

       bundle.putSerializable("movie",seri);

       intent.putExtras(bundle);


        startActivity(intent);

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        UIUpdate(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class FetchData extends AsyncTask<Void,Void, List<movie>>{
        ProgressBar bar =findViewById(R.id.prog);
        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
            sort.setVisibility(View.GONE);

            super.onPreExecute();
        }

        @Override
        protected List<movie> doInBackground(Void ...voids) {

            Json jsonPop,jsonRate;

                jsonPop = new Json(popular);
                jsonRate = new Json(top_rated);

            String jsonPopData=null;
            String jsonRateData=null;


            try {

                jsonPopData= jsonPop.connection();
                if(jsonPopData != null)
                popMovies= jsonPop.extractData(jsonPopData);
                jsonRateData = jsonRate.connection();
                if (jsonRateData != null)
                rateMovies=jsonRate.extractData(jsonRateData);


                return popMovies;
            }  catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(List<movie> movies) {
            bar.setVisibility(View.GONE);

            sortText.setVisibility(View.VISIBLE);
            sort.setVisibility(View.VISIBLE);

            rateMoviesAdapter.setMoviesList(rateMovies);

            popMoviesAdapter.setMoviesList(popMovies);







            super.onPostExecute(movies);


        }
    }

    private void UIUpdate(int i) {

        if (i==0){
            if (popMovies.size() == 0){
                error();
            }else {

                mRecycle.setVisibility(View.VISIBLE);
                mRecycle.setAdapter(popMoviesAdapter);
            }

        }else {
            if (rateMovies.size() == 0){
                error();
            }else {
                mRecycle.setAdapter(rateMoviesAdapter);
                mRecycle.setVisibility(View.VISIBLE);
            }

        }



    }
    public void error(){
        mRecycle.setVisibility(View.GONE);
        error_massage.setText("No Data");
        error_massage.setVisibility(View.VISIBLE);
    }


}
