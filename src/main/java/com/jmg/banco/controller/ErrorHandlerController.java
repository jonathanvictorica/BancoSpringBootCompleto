package com.jmg.banco.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jmg.banco.exception.ErrorInfo;
import com.jmg.banco.exception.ServicioException;

@ControllerAdvice
public class ErrorHandlerController {

	@ExceptionHandler(ServicioException.class)
	public ResponseEntity<ErrorInfo> methodArgumentNotValidException(HttpServletRequest request, ServicioException e) {

		// return error info object with standard json
		ErrorInfo errorInfo = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), e.getMensaje(), request.getRequestURI());
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(AccessDeniedException.class)
	public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
	}

	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletResponse res) throws IOException {
		res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
	}

}