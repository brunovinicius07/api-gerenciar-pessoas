package com.people.apigerenciarpessoas.exception.person;

import com.people.apigerenciarpessoas.configuration.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class PersonNotFoundException extends AlertException {
    private static final String DEFAULT_MESSAGE = "Pessoa não localizada";
    public PersonNotFoundException() {
        super("404", DEFAULT_MESSAGE, HttpStatus.NOT_FOUND);

    }
}
