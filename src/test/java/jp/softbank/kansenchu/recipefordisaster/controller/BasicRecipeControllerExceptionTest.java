package jp.softbank.kansenchu.recipefordisaster.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import jp.softbank.kansenchu.recipefordisaster.TestObjectRepository;
import jp.softbank.kansenchu.recipefordisaster.exception.InvalidRecipeException;
import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;
import jp.softbank.kansenchu.recipefordisaster.service.RecipeService;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class BasicRecipeControllerExceptionTest {

  @Mock
  private RecipeService recipeService;
  
  private RecipeExceptionController recipeController;
  
  @Before
  public void setup() {
    recipeController = new BasicRecipeController(recipeService);
  }
  
  @Test
  public void notFoundException() {
    assertEquals(recipeController.recipeNotFoundHandler(new RecipeNotFoundException()),
        TestObjectRepository.notFoundResponse);
  }
  
  @Test
  public void invalidRecipeException() {
    assertEquals(recipeController.invalidRecipeHandler(
        new InvalidRecipeException(TestObjectRepository.allFieldsMissingStr)
        ), TestObjectRepository.invalidRecipeResponse);
  }
}
