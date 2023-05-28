package ru.yandex.practicum.javafilmorate.exceptions;


public class ErrorResponseException {

    private final String error;
    private final String description;


    public ErrorResponseException(String error, String description) {
        this.error = String.format("Error with code%s", error);
        this.description = description;
    }

    public ErrorResponseException(String description) {
        this.error = "Error description is below:";
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
