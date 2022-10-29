package edu.miu.cs.cs425.fairfieldlibrarywebapp.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.BookCopyNotAvailableException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.CustomNotFoundException;
import edu.miu.cs.cs425.fairfieldlibrarywebapp.exception.MemberCannotCheckoutException;

@RestControllerAdvice
public class WebAPIExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public ModelAndView handleWebAPIException(CustomNotFoundException customNotFoundException) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", customNotFoundException.getMessage());
        var modelAndView = new ModelAndView();
        modelAndView.addObject("errorMap", errorMap);
        modelAndView.setViewName("exception/general");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BookCopyNotAvailableException.class)
    public ModelAndView handleWebAPIException(BookCopyNotAvailableException bookCopyNotAvailableException) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", bookCopyNotAvailableException.getMessage());
        var modelAndView = new ModelAndView();
        modelAndView.addObject("errorMap", errorMap);
        modelAndView.setViewName("exception/general");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MemberCannotCheckoutException.class)
    public ModelAndView handleWebAPIException(MemberCannotCheckoutException memberCannotCheckoutException) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", memberCannotCheckoutException.getMessage());
        var modelAndView = new ModelAndView();
        modelAndView.addObject("errorMap", errorMap);
        modelAndView.setViewName("exception/general");
        return modelAndView;
    }
}
