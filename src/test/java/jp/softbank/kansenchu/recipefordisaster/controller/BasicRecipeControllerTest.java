package jp.softbank.kansenchu.recipefordisaster.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import jp.softbank.kansenchu.recipefordisaster.TestObjectRepository;
import jp.softbank.kansenchu.recipefordisaster.service.RecipeService;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class BasicRecipeControllerTest {
  
  @Mock
  private RecipeService recipeService;
  
  private RecipeController recipeController;
  
  @Before
  public void setup() {
    recipeController = new BasicRecipeController(recipeService);
  }

  @Test
  public void getAllRecipes() {
    when(recipeService.getAllRecipes()).thenReturn(TestObjectRepository.allRecipesDto);
    assertEquals(recipeController.getRecipes(), TestObjectRepository.allRecipesResponse);
  }

}