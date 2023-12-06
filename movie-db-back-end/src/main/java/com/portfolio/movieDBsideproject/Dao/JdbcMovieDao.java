package com.portfolio.movieDBsideproject.Dao;

import com.portfolio.movieDBsideproject.model.Binder;
import com.portfolio.movieDBsideproject.model.Movie;
import com.portfolio.movieDBsideproject.model.MovieFullDetails;
import com.portfolio.movieDBsideproject.model.Person;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component

public class JdbcMovieDao implements MovieDao {

    private final JdbcTemplate jdbcTemplate;
    private PersonDao personDao;

    public JdbcMovieDao(JdbcTemplate jdbcTemplate, PersonDao personDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDao = personDao;
    }


    @Override
    public List<Movie> getAllMovies() throws Exception {

        List<Movie> allMovies = new ArrayList<>();
        String sql = "SELECT * FROM movie;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Movie movieResults = mapRowToMovie(results);
                allMovies.add(movieResults);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database", e);
        }

        return allMovies;
    }


    @Override
    public List<Movie> getMovieByTitle(String title) {

        List<Movie> allMovies = new ArrayList<>();
        String sql = "SELECT * FROM movie WHERE title ILIKE ?;";
        title = "%" + title + "%";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, title);

            while (results.next()) {
                Movie movieResults = mapRowToMovie(results);
                allMovies.add(movieResults);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to connect to server or database", e);
        }

        return allMovies;
    }


    @Override
    public Movie getMovieById(int id) throws Exception {

        Movie movie = new Movie();

        String sql = "SELECT * FROM movie WHERE movie_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

            if (results.next()) {
                movie = mapRowToMovie(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database", e);
        }
        return movie;
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {

        Movie newMovie = null;
        String sql = "INSERT INTO movie (title, length_minutes, release_year, director_id, poster) VALUES " +
                "(?, ?, ?, ?, ?) RETURNING movie_id;";

        try {
            Integer newMovieId = jdbcTemplate.queryForObject(sql, Integer.class, movie.getTitle(), movie.getLengthMinutes(), movie.getReleaseYear(), movie.getDirectorId(), movie.getPoster());

            newMovie = getMovieById(newMovieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }
        return newMovie;

    }

    @Override
    public Movie updateMovie(Movie movie, int id) throws Exception {

        Movie updatedMovie = null;

        String sql = "UPDATE movie SET title = ?, length_minutes = ?, release_year = ?, director_id = ?, poster = ? WHERE movie_id = ?;";

        try {
            jdbcTemplate.update(sql, movie.getTitle(), movie.getLengthMinutes(), movie.getReleaseYear(), movie.getDirectorId(), movie.getPoster(), id);

            updatedMovie = getMovieById(movie.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }

        return updatedMovie;
    }

    @Override
    public Movie updateMoviePoster(String poster, int id) throws Exception {

        Movie updatedMovie = null;

        String sql = "UPDATE movie SET poster = ? WHERE movie_id = ?;";

        try {
            jdbcTemplate.update(sql, poster, id);

            updatedMovie = getMovieById(id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }

        return updatedMovie;

    }

    @Override
    public int deleteMovieById(int id) throws Exception {

        int numberOfRows = 0;

        String deleteFromMovieActorFirst = "DELETE FROM movie_actor WHERE movie_id = ?;";
        String deleteFromBinderSecond = "DELETE FROM binder WHERE movie_id = ?";
        String deleteFromMovieLast = "DELETE FROM movie WHERE movie_id = ?";

        try {
            jdbcTemplate.update(deleteFromMovieActorFirst, id);
            jdbcTemplate.update(deleteFromBinderSecond, id);
            numberOfRows = jdbcTemplate.update(deleteFromMovieLast, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }

        return numberOfRows;

    }

    @Override
    public void addActorByIdtoMovieActor(int movieId, int actorId) throws Exception {

        String sql = "INSERT INTO movie_actor (movie_id, actor_id) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, movieId, actorId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }
    }

    @Override
    public void deleteActorByIdFromMovieActor(int movieId, int actorId) throws Exception {

        String sql = "DELETE FROM movie_actor WHERE movie_id = ? AND actor_id = ?;";

        try {
            jdbcTemplate.update(sql, movieId, actorId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }


    }

    @Override
    public void createMovieFromWeb(MovieFullDetails movie, Binder binder) {
        String sqlMovie = "INSERT INTO movie (title, length_minutes, release_year, director_id, poster) VALUES (?,?,?,?, ?) RETURNING movie_id;";
        String sqlPerson = "INSERT INTO person (name) VALUES (?) RETURNING person_id;";
        String sqlBinder = "INSERT INTO binder (binder_color, page, movie_id) VALUES (?,?,?);";
        String sqlMovieActor = "INSERT INTO movie_actor (movie_id, actor_id) VALUES (?,?);";

        int directorId = 0;
        try {
            List<Person> searchedPeople = personDao.getPersonByName(movie.getDirector());
            if (searchedPeople.size() > 0) {
                directorId = searchedPeople.get(0).getId();
            } else {

                directorId = jdbcTemplate.queryForObject(sqlPerson, int.class, movie.getDirector());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String[] minutes = movie.getLengthMinutes().split(" ");
        String releaseYear = movie.getReleaseYear().substring(movie.getReleaseYear().length() - 4);
        int movieId = 0;
        try {
            List<Movie> returnedMovie = getMovieByTitle(movie.getTitle());
            if (returnedMovie.size() == 0) {
                movieId = jdbcTemplate.queryForObject(sqlMovie, int.class, movie.getTitle(), Integer.parseInt(minutes[0]), Integer.valueOf(releaseYear), directorId, movie.getPoster());
                jdbcTemplate.update(sqlBinder, binder.getBinderColor(), binder.getPage(), movieId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        String actors = movie.getActor();
        String[] actorsList = actors.split(", ");

        List<Integer> actorsInMovie = new ArrayList<>();
        int personId = 0;
        try {
            for (String actor : actorsList) {
                List<Person> searchedPeople = personDao.getPersonByName(actor);
                if (searchedPeople.size() > 0) {
                    personId = searchedPeople.get(0).getId();
                    actorsInMovie.add(personId);
                } else {
                    personId = jdbcTemplate.queryForObject(sqlPerson, int.class, actor);
                    actorsInMovie.add(personId);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (movieId != 0) {
            String movieActorSql = "SELECT actor_id FROM movie_actor WHERE movie_id = ? AND actor_id = ?;";
            for (Integer actor : actorsInMovie) {
                SqlRowSet result = jdbcTemplate.queryForRowSet(movieActorSql, movieId, actor);
                if (!result.next()) {
                    jdbcTemplate.update(sqlMovieActor, movieId, actor);
                }
            }
        }
    }

//    {
//        "Title": "Hard Candy",
//            "Year": "2005",
//            "Rated": "R",
//            "Released": "28 Apr 2006",
//            "Runtime": "104 min",
//            "Genre": "Drama, Thriller",
//            "Director": "David Slade",
//            "Writer": "Brian Nelson",
//            "Actors": "Patrick Wilson, Elliot Page, Sandra Oh",
//            "Plot": "Hayley's a smart, charming teenage girl. Jeff's a handsome, smooth fashion photographer. An Internet chat, a coffee shop meet-up, an impromptu fashion shoot back at Jeff's place. Jeff thinks it's his lucky night. He's in for a sur...",
//            "Language": "English",
//            "Country": "United States",
//            "Awards": "10 wins & 13 nominations",
//            "Poster": "https://m.media-amazon.com/images/M/MV5BMTc0MzgzNTI3N15BMl5BanBnXkFtZTcwNDk3MDIzMQ@@._V1_SX300.jpg",
//            "Ratings": [
//        {
//            "Source": "Internet Movie Database",
//                "Value": "7.0/10"
//        },
//        {
//            "Source": "Rotten Tomatoes",
//                "Value": "67%"
//        },
//        {
//            "Source": "Metacritic",
//                "Value": "58/100"
//        }
//    ],
//        "Metascore": "58",
//            "imdbRating": "7.0",
//            "imdbVotes": "164,846",
//            "imdbID": "tt0424136",
//            "Type": "movie",
//            "DVD": "26 Aug 2016",
//            "BoxOffice": "$1,024,640",
//            "Production": "N/A",
//            "Website": "N/A",
//            "Response": "True"
//    }

    private Movie mapRowToMovie(SqlRowSet results) {
        Movie movie = new Movie();
        movie.setId(results.getInt("movie_id"));
        movie.setTitle(results.getString("title"));
        movie.setLengthMinutes(results.getInt("length_minutes"));
        movie.setReleaseYear(results.getInt("release_year"));
        movie.setDirectorId(results.getInt("director_id"));
        movie.setPoster(results.getString("poster"));
        return movie;
    }
}
