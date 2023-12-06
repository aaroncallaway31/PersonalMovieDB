package com.portfolio.movieDBsideproject.Services;

import com.portfolio.movieDBsideproject.model.MovieFullDetails;
import com.portfolio.movieDBsideproject.model.SearchMovieFullDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class MovieService implements MovieServiceInterface{

   public static String API_BASE_URL = "http://www.omdbapi.com/?apiKey=9249e505&t=";
   public static String API_BASE_URL_SEARCH = "http://www.omdbapi.com/?apiKey=9249e505&s=";
    public static String API_BASE_URL_ID = "http://www.omdbapi.com/?apiKey=9249e505&i=";


    private RestTemplate restTemplate = new RestTemplate();

   public MovieFullDetails getMovie(String title){
       return restTemplate.getForObject(API_BASE_URL + title, MovieFullDetails.class);
   }

    public SearchMovieFullDetails getMovieBySearch(String title){
        return restTemplate.getForObject(API_BASE_URL_SEARCH + title, SearchMovieFullDetails.class);
    }

    public MovieFullDetails getMovieById(String imdbId){
        return restTemplate.getForObject(API_BASE_URL_ID + imdbId, MovieFullDetails.class);
    }

}
