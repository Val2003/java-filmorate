package ru.yandex.practicum.javafilmorate.exceptions;

public class EntityAlreadyExistsException extends RuntimeException{

    public EntityAlreadyExistsException(String message){
        super(message);
    }
}
