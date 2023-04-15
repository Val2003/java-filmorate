package ru.yandex.practicum.filmorate.exceptions;

public class EntityDoesNotExistException extends RuntimeException{

    public EntityDoesNotExistException(String message){
        super(message);
    }
}
