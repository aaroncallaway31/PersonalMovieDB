package com.portfolio.movieDBsideproject.Dao;

import com.portfolio.movieDBsideproject.model.Binder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component


public class JdbcBinderDao implements BinderDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcBinderDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}


    @Override
    public List<Binder> getAllBinders() throws Exception {

        List<Binder> allBinders = new ArrayList<>();
        String sql = "SELECT * FROM binder;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

            while (results.next()) {
                Binder binderResults = mapRowToBinder(results);
                allBinders.add(binderResults);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database", e);
        }

        return allBinders;
    }

    @Override
    public List<Binder> getBinderByColor(String color) throws Exception {

        List<Binder> binderPages = new ArrayList<>();
        String sql = "SELECT * FROM binder WHERE binder_color ILIKE ?;";
        color = "%" + color + "%";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, color);

            while (results.next()) {
                Binder binderResults = mapRowToBinder(results);
                binderPages.add(binderResults);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception ("Unable to connect to server or database", e);
        }
         return binderPages;

    }

    @Override
    public Binder getBinderByMovieId(int movieId) throws Exception {

        Binder binder = new Binder();

        String sql = "SELECT * FROM binder WHERE movie_id = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, movieId);

            if(results.next()) {
                binder = mapRowToBinder(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception ("Unable to connect to server or database" ,e);
        }
        return binder;

    }

    @Override
    public Binder createBinder(Binder binder) throws Exception {

        Binder newBinder = null;
        String sql = "INSERT INTO binder (binder_color, page, movie_id) VALUES " +
                        "(?, ?, ?) RETURNING binder_color;";
//TEST THIS METHOD!!!!!!!!!!!!
        try {
            newBinder = jdbcTemplate.queryForObject(sql, Binder.class, binder.getBinderColor(), binder.getPage(), binder.getMovieId());

        }catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }
        return newBinder;

    }

    @Override
    public Binder updateBinder(Binder binder, int movieId) throws Exception {

        Binder updatedBinder = null;

        String sql = "UPDATE binder SET binder_color = ?, page = ?, movie_id = ? WHERE movie_id = ?;";

        try {
           jdbcTemplate.update(sql, binder.getBinderColor(), binder.getPage(), binder.getMovieId(), movieId);

           updatedBinder.getBinderByMovieId(movieId);
        }catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }

        return updatedBinder;

    }

    @Override
    public int deleteBinderByMovieId(int movieId) throws Exception {

        int numberOfRows = 0;

        String deleteSql = "DELETE FROM binder WHERE movie_id = ?;";

        try {
            numberOfRows = jdbcTemplate.update(deleteSql, movieId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }
        return numberOfRows;
    }

    private Binder mapRowToBinder(SqlRowSet results) {
        Binder binder = new Binder();
        binder.setBinderColor(results.getString("binder_color"));
        binder.setPage(results.getInt("page"));
        binder.setMovieId(results.getInt("movie_id"));

        return binder;
    }
}
