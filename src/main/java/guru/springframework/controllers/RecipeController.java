package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Parisana on 4/12/17
 */
@Slf4j
@Controller
public class RecipeController {

    private final static String RECIPE_RECIPEFORM_URL= "recipe/recipe-form";
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService= recipeService;
    }

    @GetMapping("recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        log.debug("***getting show page***");

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        return "recipe/show";

    }

    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        log.debug("***getting new recipe page***");

        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult){
        log.debug("***Saving Recipe***");

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug("*** "+objectError.toString()+" ***"));
            return RECIPE_RECIPEFORM_URL;
        }
        RecipeCommand savedRecipeCommand= recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        log.debug("***getting update/edit page***");
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipe-form";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id, Model model){
        log.debug("***Deleting id: "+id +" ***");
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundExceptions(Exception e){
        log.debug("***Getting error page! Error Message: "+e.getMessage()+" ***");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/404error");
        modelAndView.addObject("exceptions", e);

        return modelAndView;
    }
}
