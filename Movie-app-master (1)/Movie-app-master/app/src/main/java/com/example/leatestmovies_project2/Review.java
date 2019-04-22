package com.example.leatestmovies_project2;

import android.net.Uri;

class Review {
    String author,content;
    Uri url;

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Uri getUrl() {
        return url;
    }

    public Review(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url =Uri.parse(url);
    }
}
