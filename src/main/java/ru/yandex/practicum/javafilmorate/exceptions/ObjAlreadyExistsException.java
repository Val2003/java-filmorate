package ru.yandex.practicum.javafilmorate.exceptions;

public class ObjAlreadyExistsException extends RuntimeException {

    public ObjAlreadyExistsException(String message) {
        super(message);
    }
}
