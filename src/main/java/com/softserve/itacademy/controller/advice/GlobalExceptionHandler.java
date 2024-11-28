package com.softserve.itacademy.controller.advice;

import exception.EntityNotFoundException;
import exception.NullEntityReferenceException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullEntityReferenceException.class)
    public ModelAndView handleNullEntityReferenceException(HttpServletResponse res, NullEntityReferenceException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("code", res.getStatus());
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleEntityNotFoundException(HttpServletResponse res, EntityNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error/error");
        modelAndView.addObject("code", res.getStatus());
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }

}
