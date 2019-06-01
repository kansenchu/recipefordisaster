package jp.softbank.kansenchu.recipefordisaster.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BasicRecipeServiceTest {
  
  private RecipeService recipeService;
  
  @Before
  public void setup() {
    recipeService = new BasicRecipeService();
  }

  @Test
  public void getAllRecipes() {
    assertEquals(recipeService.getAllRecipes(), new ArrayList<>());
  }
}
