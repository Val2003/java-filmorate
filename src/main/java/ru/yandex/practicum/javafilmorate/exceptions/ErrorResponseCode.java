package ru.yandex.practicum.javafilmorate.exceptions;


public class ErrorResponseCode {

    private final String error;
    private final String description;


    public ErrorResponseCode(String error, String description) {
        this.error = String.format("ERROR with %s", error);
        this.description = description;
    }

    public ErrorResponseCode(String description) {
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
