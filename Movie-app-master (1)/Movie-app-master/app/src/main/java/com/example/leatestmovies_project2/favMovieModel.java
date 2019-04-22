package com.example.leatestmovies_project2;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class favMovieModel  implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;



    private int movieID;
    private String imageID;


    private String imageLink="http://image.tmdb.org/t/p/w185/";

    private String original_title;
    private String plot;
    private double rating;
    private String release_date;

    public favMovieModel(String original_title, String imageID, String plot, double rating, String release_date) {
        this.imageID = imageID;
        imageLink = imageLink+imageID;
        this.original_title = original_title;

        this.plot = plot;
        this.rating = rating;
        this.release_date = release_date;

    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    private int isFavorite=-1;

    public favMovieModel(Parcel imageID) {
        this.imageID = imageID.readString();
        imageLink=imageID.readString();
        original_title=imageID.readString();
        plot=imageID.readString();
        release_date=imageID.readString();
        rating=imageID.readDouble();
        isFavorite=imageID.readInt();
        movieID=imageID.readInt();

    }
    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;

    }



    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int isFavorite() {
        return isFavorite;
    }

    public void setisFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getOriginal_title() {

        return original_title;
    }



    public String getPlot() {
        return plot;
    }

    public double getRating() {
        return rating;
    }

    public String getRelease_date() {
        return release_date;
    }








//    private com.example.leatestmovies_project2.movie movie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }




    public String getImageID() {
        return imageID;
    }





    public String getImageLink() {
        return imageLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(imageID);
            dest.writeString(imageLink);
            dest.writeString(original_title);
            dest.writeString(plot);
            dest.writeString(release_date);
            dest.writeDouble(rating);
            dest.writeInt(isFavorite);
            dest.writeInt(movieID);

    }
    public static final Creator<favMovieModel> CREATOR = new Creator<favMovieModel>() {
        @Override
        public favMovieModel createFromParcel(Parcel in) {

            return new favMovieModel(in);
        }

        @Override
        public favMovieModel[] newArray(int size) {
            return new favMovieModel[size];
        }
    };

    }


