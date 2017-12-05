package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
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

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String showIngredientList(@PathVariable String recipeId, Model model){
        log.debug("***Showing ingredient list for recipe id: " + recipeId + " ***");

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";

    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        log.debug("***Getting Ingredient :"+ingredientId+" Recipe: "+recipeId);

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredient/ingredient-form";

    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){

        IngredientCommand savedIngredientCommand= ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("***Saved recipe Id:" + savedIngredientCommand.getRecipeId());
        log.debug("***Saved ingredient Id:" + savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() +"/show";
    }
}
