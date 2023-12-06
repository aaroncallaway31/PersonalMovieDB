package com.portfolio.movieDBsideproject.Dao;

import com.portfolio.movieDBsideproject.model.Movie;
import com.portfolio.movieDBsideproject.model.Person;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcPersonDao implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> getPeople() throws Exception {

        List<Person> people = new ArrayList<>();
        String sql = "SELECT person_id, name FROM person;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                people.add(mapRowToPeople(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database", e);
        }
        return people;
    }


    @Override
    public List<Person> getPersonByName(String name) throws Exception{

        List<Person> allPeople = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE name ILIKE ?;";
        name = "%" + name + "%";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);

            while (results.next()) {
                Person peopleResults = mapRowToPeople(results);
                allPeople.add(peopleResults);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database", e);
        }

        return allPeople;

    }


    @Override
    public Person getPersonById(int id) throws Exception{
        Person person = new Person();
        String sql = "SELECT person_id, name FROM person WHERE person_id = ?;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

            if (results.next()) {
                person = mapRowToPeople(results);
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database", e);
        }
        return person;
    }

    @Override
    public Person createPerson(Person person) throws Exception {

        Person newPerson = null;

        String sql = "INSERT INTO person (name) VALUES (?) RETURNING person_id;";

        try {
            Integer newPersonId = jdbcTemplate.queryForObject(sql, Integer.class, person.getName());

            newPerson = getPersonById(newPersonId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }
        return newPerson;
    }

    @Override
    public Person updatePerson(Person person, int personId) throws Exception{

        Person updatedPerson = null;

        String sql = "UPDATE person SET name = ? WHERE person_id = ?;";

        try {
            jdbcTemplate.update(sql, person.getName(), personId);

            updatedPerson = getPersonById(person.getId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }

        return updatedPerson;

    }


    @Override
    public int deletePersonById(int id) throws Exception{

        int numberOfRows = 0;

        String deleteFromMovieActorFirst = "DELETE FROM movie_actor WHERE actor_id = ?;";
        String deleteFromMovieSecond = "DELETE FROM movie WHERE director_id = ?;";
        String deleteFromPersonLast = "DELETE FROM person WHERE person_id = ?;";

        try {
            jdbcTemplate.update(deleteFromMovieActorFirst, id);
            jdbcTemplate.update(deleteFromMovieSecond, id);
            numberOfRows = jdbcTemplate.update(deleteFromPersonLast, id);
        }catch (CannotGetJdbcConnectionException e) {
            throw new Exception("Unable to connect to server or database");
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Data integrity violation");
        }

        return numberOfRows;

    }

    private Person mapRowToPeople(SqlRowSet results) {
        Person person = new Person();
        person.setId(results.getInt("person_id"));
        person.setName(results.getString("name"));
        return person;
    }
}
