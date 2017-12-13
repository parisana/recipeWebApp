package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Parisana on 5/12/17
 */
@Slf4j
@Controller
public class IngredientController {

    private final UnitOfMeasureService unitOfMeasureService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(UnitOfMeasureService unitOfMeasureService, RecipeService recipeService, IngredientService ingredientService) {
        this.unitOfMeasureService = unitOfMeasureService;

        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("***Showing ingredient list for recipe id: " + recipeId + " ***");

        model.addAttribute("recipe", recipeService.findCommandById(recipeId));

        return "recipe/ingredient/list";

    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        log.debug("***Getting Ingredient :"+ingredientId+" Recipe: "+recipeId);

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){

        log.debug("***Getting update ingredient form***");

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredient-form";

    }

    @RequestMapping(value = "/recipe/{recipeId}/ingredient", method = {RequestMethod.POST, RequestMethod.GET})
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){

        IngredientCommand savedIngredientCommand= ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("***Saved recipe Id:" + savedIngredientCommand.getRecipeId());
        log.debug("***Saved ingredient Id:" + savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() +"/show";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipeIngredient(@PathVariable String recipeId, Model model){
        log.debug("***Getting new ingredient form***");

        RecipeCommand recipeCommand= recipeService.findCommandById(recipeId);
        //todo handle exceptions in case null

        IngredientCommand ingredientCommand= new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);

        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredient-form";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId){
        log.debug("***Deleting ingredient with Id: "+ingredientId);
        ingredientService.deleteIngredientById(recipeId, ingredientId);

        return "redirect:/recipe/"+ recipeId +"/ingredients";
    }
}
