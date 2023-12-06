package com.portfolio.movieDBsideproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchMovieFullDetails {

    @JsonProperty("Search")
    private List<MovieById> search;

    public List<MovieById> getSearch() {
        return search;
    }

    public void setSearch(List<MovieById> search) {
        this.search = search;
    }
}

