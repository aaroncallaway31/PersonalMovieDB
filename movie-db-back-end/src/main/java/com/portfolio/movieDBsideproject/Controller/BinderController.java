package com.portfolio.movieDBsideproject.Controller;

import com.portfolio.movieDBsideproject.Dao.BinderDao;
import com.portfolio.movieDBsideproject.model.Binder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class BinderController {

    private BinderDao binderDao;

    public BinderController(BinderDao binderDao) {
        this.binderDao = binderDao;
    }

    @RequestMapping(path = "/binder", method = RequestMethod.GET)
    public List<Binder> list() {

        try {
            return binderDao.getAllBinders();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @RequestMapping(path = "/binder/color/{color}", method = RequestMethod.GET)
    public List<Binder> getByColor(@PathVariable String color) {
            List<Binder> binders = null;
        try {
            binders = binderDao.getBinderByColor(color);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return binders;
    }

    @RequestMapping(path = "/binder/movieId/{movieId}", method = RequestMethod.GET)
    public Binder getBinderByMovieId(@PathVariable int movieId) {
        Binder binder = null;

        try {
            binder = binderDao.getBinderByMovieId(movieId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return binder;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/binder", method = RequestMethod.POST)
    public Binder create(@RequestBody Binder binder) {

        try {
            return binderDao.createBinder(binder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @RequestMapping(path = "/binder/{id}", method = RequestMethod.PUT)
    public Binder update(@RequestBody Binder binder, @PathVariable int newMovieId) {
        binder.setMovieId(newMovieId);
        try {
            Binder updatedBinder = binderDao.updateBinder(binder, newMovieId);
            return updatedBinder;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Binder not found");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/binder/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        try {
            binderDao.deleteBinderByMovieId(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Binder not found");
        }
    }







}
