package com.portfolio.movieDBsideproject.Controller;

import com.portfolio.movieDBsideproject.Dao.MovieDao;
import com.portfolio.movieDBsideproject.Services.MovieService;
import com.portfolio.movieDBsideproject.model.Binder;
import com.portfolio.movieDBsideproject.model.Movie;
import com.portfolio.movieDBsideproject.model.MovieFullDetails;
import com.portfolio.movieDBsideproject.model.SearchMovieFullDetails;
import org.apache.coyote.Request;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin
@RestController
public class MovieController {

    private MovieDao movieDao;
    private MovieService movieService;


    public MovieController(MovieDao movieDao, MovieService movieService) {
        this.movieDao = movieDao;
        this.movieService = movieService;
    }

    @RequestMapping(path = "/movies", method = RequestMethod.GET)
    public List<Movie> list() {

        try {
            return movieDao.getAllMovies();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

//    @RequestMapping(path = "/movie/title/{title}", method = RequestMethod.GET)
//    public List<Movie> getByTitle(@PathVariable String title) {
//        List<Movie> movies = null;
//        try {
//            movies = movieDao.getMovieByTitle(title);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return movies;
//    }

    @RequestMapping(path = "/movie", method = RequestMethod.GET)
    public List<Movie> getByTitle(@RequestParam(defaultValue = "") String title, @RequestParam(defaultValue = "0") int id) {
        List<Movie> movies = new ArrayList<>();
        try {
            if (!title.isEmpty()) {
                movies = movieDao.getMovieByTitle(title);
            } else if (id > 0) {
                movies.add(movieDao.getMovieById(id));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return movies;
    }


//    @RequestMapping(path = "/movie/id/{id}", method = RequestMethod.GET)
//    public Movie getById(@PathVariable int id) {
//        Movie movie = null;
//        try {
//            movie = movieDao.getMovieById(id);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return movie;
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/movie", method = RequestMethod.POST)
    public Movie create(@RequestBody Movie movie) {

        try {
            return movieDao.createMovie(movie);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @RequestMapping(path = "/movie/{id}", method = RequestMethod.PUT)
    public Movie update(@RequestBody Movie movie, @PathVariable int id) {
        movie.setId(id);
        try {
            Movie updatedMovie = movieDao.updateMovie(movie, id);
            return updatedMovie;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/movie/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        try {
            movieDao.deleteMovieById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }
    }

    @RequestMapping(path = "/movie/{movieId}/{actorId}", method = RequestMethod.POST)
    public void post(@PathVariable int movieId, @PathVariable int actorId) {
        try {
            movieDao.addActorByIdtoMovieActor(movieId, actorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Successful, couldn't add actor");
        }
    }

    @RequestMapping(path = "/movie/{movieId}/{actorId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int movieId, @PathVariable int actorId) {
        try {
            movieDao.deleteActorByIdFromMovieActor(movieId, actorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Successful, couldn't remove actor");
        }
    }

    @RequestMapping(path = "/createFromApi", method = RequestMethod.POST)
    public void createFromWeb(@RequestParam String title, @RequestBody Binder binder) {

        MovieFullDetails movie = movieService.getMovie(title);
        movieDao.createMovieFromWeb(movie, binder);
    }

    @RequestMapping(path = "/searchFromApi", method = RequestMethod.GET)
    public SearchMovieFullDetails searchFromWebFullDetails(@RequestParam String title) {

        SearchMovieFullDetails movie = movieService.getMovieBySearch(title);
        return movie;
    }

    @RequestMapping(path = "/createByIdFromWeb", method = RequestMethod.POST)
    public List<Movie> createByIdFromWeb(@RequestParam String imdbId, @RequestBody Binder binder) {

        MovieFullDetails movie = movieService.getMovieById(imdbId);

        try {
            return movieDao.getMovieByTitle(movie.getTitle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(path = "/updatePoster", method = RequestMethod.PUT)
    public Movie updatePoster(@RequestParam String imdbId) {
        MovieFullDetails movie = movieService.getMovieById(imdbId);
        List<Movie> movies = null;
        try {
            movies = movieDao.getMovieByTitle(movie.getTitle());
            movieDao.updateMoviePoster(movie.getPoster(), movies.get(0).getId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            return movieDao.getMovieByTitle(movie.getTitle()).get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}




