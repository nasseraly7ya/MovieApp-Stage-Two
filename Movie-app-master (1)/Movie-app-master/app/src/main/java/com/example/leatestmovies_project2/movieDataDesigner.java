package com.example.leatestmovies_project2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class movieDataDesigner extends RecyclerView.Adapter<movieDataDesigner.gridList> {
    List<favMovieModel> imgArray=new ArrayList<>();
    public int pos;



    final private movieDataOnClickListner onClickListner;


    public movieDataDesigner(movieDataOnClickListner onClickListner, List<favMovieModel> favMovies) {
        this.onClickListner = onClickListner;
        imgArray = favMovies;

    }

    public interface movieDataOnClickListner {

        void onClickListner(favMovieModel str,int pos);
    }




    @NonNull
    @Override
    public gridList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        int layInt = R.layout.grid_layout;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(layInt,viewGroup,false);

        return new gridList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull gridList gridList, int i) {
        String imglinke=imgArray.get(i).getImageLink();
        Log.v(String.valueOf(i) + " : itemImage", imgArray.get(i).getOriginal_title() + "  :  " + imglinke);

        Picasso.get().load(imglinke).into(gridList.imgBtn);
    }



    @Override
    public int getItemCount() {
if ( imgArray == null)
    return 0;
        else if (imgArray.size() == 0 )
            return 0;

        return imgArray.size();
    }


    public class gridList extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageButton imgBtn;


        public gridList(@NonNull View itemView) {
            super(itemView);
             imgBtn = itemView.findViewById(R.id.poster);
             imgBtn.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {
            favMovieModel movie = imgArray.get(getAdapterPosition());
            Log.v(String.valueOf(getAdapterPosition())+" : itemImage",imgArray.get(getAdapterPosition()).getOriginal_title()+"  :  " +imgArray.get(getAdapterPosition()).getImageLink());

            pos=getAdapterPosition();
            onClickListner.onClickListner(movie,pos);

        }
    }

}
