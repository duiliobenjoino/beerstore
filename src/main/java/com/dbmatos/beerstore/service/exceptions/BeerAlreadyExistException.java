package com.dbmatos.beerstore.service.exceptions;

import org.springframework.http.HttpStatus;

public class BeerAlreadyExistException extends BusinessException{

	private static final long serialVersionUID = 1L;

	public BeerAlreadyExistException() {
        super("beer-5",HttpStatus.BAD_REQUEST);
    }
}
