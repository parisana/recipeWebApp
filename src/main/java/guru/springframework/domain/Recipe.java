package guru.springframework.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Parisana on 3/12/17
 */
@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;

    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer servings;

    private String source;

    private String url;

    private String directions;

    //one recipe will have many ingredients, which will be mapped by each recipe deleting one recipe will delete the related ingredients as well
    private Set<Ingredient> ingredients= new HashSet<>();

    private Byte[] image;

    private Notes notes;

    private Difficulty difficulty;

    @DBRef
    private Set<Category> categories= new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        //notes.setRecipe(this);//causes circular dependency
    }

    public Recipe addIngredient(Ingredient ingredient){
        //ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }
}
