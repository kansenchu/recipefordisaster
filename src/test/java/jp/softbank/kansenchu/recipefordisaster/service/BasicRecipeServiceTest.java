package jp.softbank.kansenchu.recipefordisaster.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import jp.softbank.kansenchu.recipefordisaster.repository.RecipeRepo;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class BasicRecipeServiceTest {
  
  @Mock
  private RecipeRepo recipeRepo;
  
  private RecipeService recipeService;
  
  @Before
  public void setup() {
    recipeService = new BasicRecipeService(recipeRepo);
  }
  
  @Test
  public void getAllRecipes() {
    when(recipeRepo.findAll(any(Sort.class))).thenReturn(new ArrayList<>());
    assertEquals(recipeService.getAllRecipes(), new ArrayList<>());
  }
}
