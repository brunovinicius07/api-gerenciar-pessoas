package com.people.apigerenciarpessoas.exception.person;

import com.people.apigerenciarpessoas.configuration.exceptionHandler.AlertException;
import org.springframework.http.HttpStatus;

public class PersonPresentException extends AlertException {

    private static final String DEFAULT_MESSAGE = "cpf já está cadastrado!";

    public PersonPresentException() {
        super("409", DEFAULT_MESSAGE, HttpStatus.CONFLICT);
    }
}