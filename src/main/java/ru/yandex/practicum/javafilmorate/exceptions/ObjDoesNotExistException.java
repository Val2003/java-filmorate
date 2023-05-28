package ru.yandex.practicum.javafilmorate.exceptions;

public class ObjDoesNotExistException extends RuntimeException {

    public ObjDoesNotExistException(String message) {
        super(message);
    }
}
