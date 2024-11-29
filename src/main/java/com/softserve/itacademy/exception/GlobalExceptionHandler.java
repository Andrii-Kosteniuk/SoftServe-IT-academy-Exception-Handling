package com.softserve.itacademy.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NullEntityReferenceException.class)
    public ModelAndView handleNullEntityReferenceException(NullEntityReferenceException e) {
        logger.error("NullEntityReferenceException occurred: {}", e.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(EntityNotFoundException e) {
        logger.warn("EntityNotFoundException occurred: {}", e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleInternalServerErrorException(Exception e) {
        logger.error("Unhandled exception occurred: {}", e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView("500");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException() {
        logger.info("NoHandlerFoundException: Page not found.");
        ModelAndView modelAndView = new ModelAndView("404");
        modelAndView.addObject("message", "The page you're looking for was not found.");
        return modelAndView;
    }
}
