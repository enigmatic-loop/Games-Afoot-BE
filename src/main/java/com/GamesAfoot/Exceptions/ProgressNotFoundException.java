package com.GamesAfoot.Exceptions;

public class ProgressNotFoundException extends RuntimeException {

    public ProgressNotFoundException(Integer id) {
        super("Progress not found with id: "+ id);
    }

}
