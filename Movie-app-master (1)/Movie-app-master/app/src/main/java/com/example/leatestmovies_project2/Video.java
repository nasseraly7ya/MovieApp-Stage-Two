package com.example.leatestmovies_project2;

public class Video {
    private String name;
    private String Key;

    public String getName() {
        return name;
    }

    public String getKey() {
        return Key;
    }

    public Video(String name, String key) {
        this.name = name;
        Key = key;
    }
}
