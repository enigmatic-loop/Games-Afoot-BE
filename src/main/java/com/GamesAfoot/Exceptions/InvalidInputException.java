package com.GamesAfoot.Exceptions;

import com.GamesAfoot.Models.Progress;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(Progress progress) {
        super("Something is invalid \n" + progress);
    }
}
