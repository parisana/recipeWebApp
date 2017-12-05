package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Parisana on 4/12/17
 */
@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService= recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model){
        log.debug("***getting show page***");

        model.addAttribute("recipe", recipeService.findById(new Long(id)));

        return "recipe/show";

    }

    @GetMapping
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model){
        log.debug("***getting new recipe page***");

        model.addAttribute("recipe", new RecipeCommand());

        return "recipe-form";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand){
        log.debug("***getting save/update page***");
        RecipeCommand savedRecipeCommand= recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        log.debug("***getting update/edit page***");
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe-form";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id, Model model){
        log.debug("***Deleting id: "+id +" ***");
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }
}
