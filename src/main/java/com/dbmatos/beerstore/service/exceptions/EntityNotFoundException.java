package com.dbmatos.beerstore.service.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException{

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException() {
        super("beer-6",HttpStatus.NOT_FOUND);
    }
}
