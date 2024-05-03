package com.example.fragment.model;

import java.io.Serializable;

public class Song implements Serializable {
    private int id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private int favorite;

    public Song() {
    }

    public Song(int id, String title, String artist, String album, String genre, int favorite) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.favorite = favorite;
    }

    public Song(String title, String artist, String album, String genre, int favorite) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
