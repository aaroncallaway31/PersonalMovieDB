package com.portfolio.movieDBsideproject.Dao;

import com.portfolio.movieDBsideproject.model.Movie;
import com.portfolio.movieDBsideproject.model.Person;

import java.util.List;

public interface PersonDao {

    List<Person> getPeople() throws Exception;

    List<Person> getPersonByName(String name) throws Exception;

    Person getPersonById(int Id) throws Exception;

    Person createPerson(Person person) throws Exception;

    Person updatePerson(Person person, int personId) throws Exception;

    int deletePersonById(int id) throws Exception;

}
