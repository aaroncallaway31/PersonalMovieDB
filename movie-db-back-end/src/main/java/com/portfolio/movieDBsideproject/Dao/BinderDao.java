package com.portfolio.movieDBsideproject.Dao;
import java.util.List;

import com.portfolio.movieDBsideproject.model.Binder;

public interface BinderDao {

        List<Binder> getAllBinders() throws Exception;

        List<Binder> getBinderByColor(String color) throws Exception;

        Binder getBinderByMovieId(int movieId) throws Exception;

        Binder createBinder(Binder binder) throws Exception;

        Binder updateBinder(Binder binder, int movieId) throws Exception;

        int deleteBinderByMovieId(int movieId) throws Exception;
}
