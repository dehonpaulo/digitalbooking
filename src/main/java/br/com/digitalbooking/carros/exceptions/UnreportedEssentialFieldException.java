package br.com.digitalbooking.carros.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnreportedEssentialFieldException extends RuntimeException {
    public UnreportedEssentialFieldException(String msg) {
        super(msg);
    }
}
