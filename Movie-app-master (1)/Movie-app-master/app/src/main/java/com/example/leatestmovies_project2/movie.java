package com.example.leatestmovies_project2;



public class movie {
    private String original_title;
    private String image ;
    private String plot;
    private double rating;
    private String release_date;

    public String getImageBase() {
        return ImageBase;
    }

    public void setImageBase(String imageBase) {
        ImageBase = imageBase;
    }

    private String ImageBase ="http://image.tmdb.org/t/p/w185/";

    public movie(String original_title, String image, String plot, double rating, String release_date) {
        this.original_title = original_title;
        this.image = image;
        this.plot = plot;
        this.rating = rating;
        this.release_date = release_date;
    }


    public String getOriginal_title() {

        return original_title;
    }

    public String getImage() {
        return ImageBase+image;
    }

    public String getPlot() {
        return plot;
    }

    public double getRating() {
        return rating;
    }

    public String getRelease_date() {
        return "Release Date : "+release_date;
    }


}
