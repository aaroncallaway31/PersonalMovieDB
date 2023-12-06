package com.portfolio.movieDBsideproject.Dao;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.portfolio.movieDBsideproject.model.Binder;
import com.portfolio.movieDBsideproject.model.Movie;
import com.portfolio.movieDBsideproject.model.MovieFullDetails;

import java.util.List;

public interface MovieDao {

    List<Movie> getAllMovies() throws Exception;

    List<Movie> getMovieByTitle(String title) throws Exception;

    Movie getMovieById(int id) throws Exception;

    Movie createMovie(Movie movie) throws Exception;

    Movie updateMovie(Movie movie, int id) throws Exception;

    int deleteMovieById(int id) throws Exception;

    void addActorByIdtoMovieActor(int movieId, int actorId) throws Exception;

    void deleteActorByIdFromMovieActor(int movieId, int actorId) throws Exception;

    void createMovieFromWeb(MovieFullDetails movie, Binder binder);

    public Movie updateMoviePoster(String poster, int id) throws Exception;
}