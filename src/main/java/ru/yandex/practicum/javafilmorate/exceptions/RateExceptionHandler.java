package ru.yandex.practicum.javafilmorate.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice

public class RateExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponseCode handleInvalidPathVariableOrParameter(final InvalidPathVariableOrParameterException e) {

        return new ErrorResponseCode(e.getParam(), e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponseCode handleValidationException(final ValidationException e) {

        return new ErrorResponseCode(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponseCode handleEntityDoesNotExistException(final ObjDoesNotExistException e) {

        return new ErrorResponseCode(e.getMessage());
    }
}
