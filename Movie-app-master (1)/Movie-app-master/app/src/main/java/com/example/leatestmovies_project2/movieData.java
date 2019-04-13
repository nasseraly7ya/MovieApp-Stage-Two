package com.example.leatestmovies_project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class movieData extends AppCompatActivity {
    ImageView poster;
    ImageView StarRate;
    TextView title,polt,date,rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_data);
        poster = findViewById(R.id.img);
        StarRate = findViewById(R.id.satrRate);
        title = findViewById(R.id.movietitle);
        polt = findViewById(R.id.polt);
        date = findViewById(R.id.date);
        rate = findViewById(R.id.Rate);
       Bundle bundle = getIntent().getExtras();
       Seri movie = (Seri) bundle.getSerializable("movie");

        Picasso.get().load(movie.getImage()).into(poster);

        title.setText(movie.getOriginal_title());
        polt.setText(movie.getPlot());
        date.setText(movie.getRelease_date());
        rate.setText(String.valueOf(movie.getRating()));
    }
}
