package com.example.leatestmovies_project2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class movieDataDesigner extends RecyclerView.Adapter {
    ImageButton imgBtn;
    List<movie> imgArray=new ArrayList<>();



    final private movieDataOnClickListner onClickListner;


    public movieDataDesigner(movieDataOnClickListner onClickListner) {
        this.onClickListner = onClickListner;
    }

    public interface movieDataOnClickListner {

        void onClickListner(movie str);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Picasso.get().load(imgArray.get(i).getImage()).into(imgBtn);





    }

    @Override
    public int getItemCount() {
if ( imgArray == null)
    return 0;
        else if (imgArray.size() == 0 )
            return 0;

        return 20;
    }


    public class gridList extends RecyclerView.ViewHolder implements View.OnClickListener {


        public gridList(@NonNull View itemView) {
            super(itemView);
             imgBtn = itemView.findViewById(R.id.poster);
             imgBtn.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {
            movie movie = imgArray.get(getAdapterPosition());
            onClickListner.onClickListner(movie);

        }
    }
    public void setMoviesList(List<movie> array){
        imgArray =null;
        imgArray=array;
        notifyDataSetChanged();

    }
}
