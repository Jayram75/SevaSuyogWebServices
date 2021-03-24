package in.sevasuyog.controller;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.sevasuyog.model.enums.ResponseMessage;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = LogManager.getLogger(MyControllerAdvice.class);
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
    	ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
		String msg = violation.getPropertyPath() + " " + violation.getMessage();
        return new ResponseEntity<Object>(
        	msg, new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        return new ResponseEntity<Object>(
        	e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler({ UnsupportedOperationException.class })
    public ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException e, WebRequest request) {
        return new ResponseEntity<Object>(
        	ResponseMessage.OPERATION_NOT_ALLOWED.name(), new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }
    
    @ExceptionHandler({ Throwable.class })
    public ResponseEntity<Object> handleThrowable(Throwable e, WebRequest request) {
    	LOGGER.error(e);
        return new ResponseEntity<Object>(
        	ResponseMessage.SOMETHING_WENT_WRONG.name(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    
    @InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
}