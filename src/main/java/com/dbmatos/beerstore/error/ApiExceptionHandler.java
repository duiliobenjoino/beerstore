package com.dbmatos.beerstore.error;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbmatos.beerstore.error.ErrorResponse.ApiError;
import com.dbmatos.beerstore.service.exceptions.BusinessException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionHandler extends AbstractErrorHandler{
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, Locale locale){
		
		List<ApiError> errors = exception.getBindingResult().getAllErrors()
								.stream()
									.map(error -> toApiError(error.getDefaultMessage(), locale))
									.collect(toList());
		
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, 	errors);
		return ResponseEntity.badRequest().body(errorResponse);
		
	}
	
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<ErrorResponse> handleInvalidFormatException(InvalidFormatException exception, Locale locale){
		final String errorCode = "generic-1";
		ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, 	toApiError(errorCode, locale, exception.getValue()));
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, Locale locale){
		final String errorCode = exception.getCode();
		ErrorResponse errorResponse = ErrorResponse.of(exception.getStatus(), toApiError(errorCode, locale));
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
}
