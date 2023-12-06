package com.portfolio.movieDBsideproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MovieFullDetails {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Runtime")
    private String lengthMinutes;
    @JsonProperty("Released")
    private String releaseYear;
    @JsonProperty("Director")
    private String director;
    @JsonProperty("Actors")
    private String actors;
    @JsonProperty("Poster")
    private String poster;

    public MovieFullDetails(){}

    public MovieFullDetails(String title, String lengthMinutes, String releaseYear, String directorName){

        this.title = title;
        this.lengthMinutes = lengthMinutes;
        this.releaseYear = releaseYear;
        this.director = directorName;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getLengthMinutes(){
        return lengthMinutes;
    }

    public void setLengthMinutes(String lengthMinutes){
        this.lengthMinutes = lengthMinutes;
    }

    public String getReleaseYear(){
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear){
        this.releaseYear = releaseYear;
    }

    public String getDirector(){
        return director;
    }

    public void setDirectorId(String directorId){
        this.director = director;
    }

    public String getActor(){
        return actors;
    }

    public void setActor(String actor){
        this.actors = actor;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "MovieFullDetails{" +
                "title='" + title + '\'' +
                ", lengthMinutes='" + lengthMinutes + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}
