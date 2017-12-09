package guru.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Parisana on 7/12/17
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleRecipeIdFormatException(Exception e){

        log.error("***Handling Number Format Exception. Message: "+e.getMessage()+" ***");

        ModelAndView modelAndView= new ModelAndView();

        modelAndView.addObject("exceptions", e);
        modelAndView.setViewName("error/400error");

        return modelAndView;

    }

}
