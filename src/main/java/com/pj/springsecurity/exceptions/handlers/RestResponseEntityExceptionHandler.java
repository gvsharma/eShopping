package com.pj.springsecurity.exceptions.handlers;

import com.pj.springsecurity.exceptions.exceptions.GenericException;
import com.pj.springsecurity.model.exception.ErrorMessage;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
	private final ModelMapper modelMapper;

	public RestResponseEntityExceptionHandler(ModelMapper modelMapper)
	{
		this.modelMapper = modelMapper;
	}

	@ExceptionHandler({AuthenticationException.class})
	public ResponseEntity<Object> handleAccessDeniedException(Exception exception, WebRequest webRequest)
	{
		return new ResponseEntity<>("Authentication Failed", new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ErrorMessage> handleGenericExceptions(GenericException genericException, WebRequest webRequest)
	{
		ErrorMessage errorMessage = modelMapper.map(genericException, ErrorMessage.class);
		errorMessage.setStatusCode(errorMessage.getStatus().value());
		return new ResponseEntity<>(errorMessage, errorMessage.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleAllExceptions(Exception exception, WebRequest webRequest)
	{
		ErrorMessage errorMessage = modelMapper.map(exception, ErrorMessage.class);
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
