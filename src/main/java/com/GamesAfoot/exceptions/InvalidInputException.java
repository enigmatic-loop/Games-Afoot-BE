package com.GamesAfoot.exceptions;

import com.GamesAfoot.models.Progress;

public class InvalidInputException extends RuntimeException{

    public InvalidInputException(Progress progress) {
        super("Something is invalid \n" + progress);
    }
}
