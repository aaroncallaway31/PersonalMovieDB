package com.portfolio.movieDBsideproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

    @JsonProperty("movie_id")
    private int id;
    private String title;
    @JsonProperty("length_minutes")
    private int lengthMinutes;
    @JsonProperty("release_year")
    private int releaseYear;
    @JsonProperty("director_id")
    private int directorId;

    private String poster;

    public Movie(int id, String title, int lengthMinutes, int releaseYear, int directorId) {

        this.id = id;
        this.title = title;
        this.lengthMinutes = lengthMinutes;
        this.releaseYear = releaseYear;
        this.directorId = directorId;
    }

    public Movie() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getLengthMinutes() {
        return lengthMinutes;
    }

    public void setLengthMinutes(int lengthMinutes) {
        this.lengthMinutes = lengthMinutes;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDirectorId() {
        return directorId;
    }

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", lengthMinutes=" + lengthMinutes +
                ", releaseYear=" + releaseYear +
                ", directorId=" + directorId +
                ", poster='" + poster + '\'' +
                '}';
    }
}

