package com.portfolio.movieDBsideproject.Controller;

import com.portfolio.movieDBsideproject.Dao.PersonDao;
import com.portfolio.movieDBsideproject.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PersonController {

    private PersonDao personDao;

    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @RequestMapping(path = "/person", method = RequestMethod.GET)
    public List<Person> list() {

        try {
            return personDao.getPeople();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/person/name/{name}", method = RequestMethod.GET)
    public List<Person> getByName(@PathVariable String name) {
        List<Person> people = null;
        try {
            people = personDao.getPersonByName(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return people;
    }

    @RequestMapping(path = "/person/id/{id}", method = RequestMethod.GET)
    public Person getPersonById(@PathVariable int id) {
        Person person = null;
        try {
            person = personDao.getPersonById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return person;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/person", method = RequestMethod.POST)
    public Person create(@RequestBody Person person) {
        try {
            return personDao.createPerson(person);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/person/{id}", method = RequestMethod.PUT)
    public Person update(@RequestBody Person person, @PathVariable int id) {

        person.setId(id);
        try {
            Person updatedPerson = personDao.updatePerson(person, id);
            return updatedPerson;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/person/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        try {
            personDao.deletePersonById(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found");
        }
    }



}
