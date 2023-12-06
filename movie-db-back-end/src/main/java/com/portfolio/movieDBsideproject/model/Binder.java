package com.portfolio.movieDBsideproject.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Binder {

    @JsonProperty("binder_color")
    private String binderColor;
    private int page;
    @JsonProperty("movie_id")
    private int movieId;

    public Binder(String binderColor, int page, int movieId){
        this.binderColor = binderColor;
        this.page = page;
        this.movieId = movieId;
    }

    public Binder(){};

    public String getBinderColor(){
        return binderColor;
    }

    public void setBinderColor(String binderColor){
        this.binderColor = binderColor;
    }

    public int getPage(){
        return page;
    }

    public void setPage(int page){
        this.page = page;
    }

    public int getMovieId(){
        return movieId;
    }

    public void setMovieId(int movieId){
        this.movieId = movieId;
    }

    public void getBinderByMovieId(int movieId) {
    }

    @Override
    public String toString() {
        return "Binder{" +
                "binderColor='" + binderColor + '\'' +
                ", page=" + page +
                ", movieId=" + movieId +
                '}';
    }
}
