package br.com.digitalbooking.carros.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UniqueAttributeValueRepeatedException extends RuntimeException {
    public UniqueAttributeValueRepeatedException(String msg) {
        super(msg);
    }
}
