package jp.softbank.kansenchu.recipefordisaster.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import jp.softbank.kansenchu.recipefordisaster.TestObjectRepository;
import jp.softbank.kansenchu.recipefordisaster.dao.RecipeDao;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.exception.InvalidRecipeException;
import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;
import jp.softbank.kansenchu.recipefordisaster.repository.RecipeRepo;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class BasicRecipeServiceTest {
  
  @Rule
  public ExpectedException expectedEx = ExpectedException.none();
  
  @Mock
  private RecipeRepo recipeRepo;
  
  private RecipeService recipeService;
  
  @Before
  public void setup() {
    recipeService = new BasicRecipeService(recipeRepo);
  }
  
  @Test
  public void getAllRecipes() {
    when(recipeRepo.findAll(any(Sort.class))).thenReturn(TestObjectRepository.allRecipes);
    assertEquals(recipeService.getAllRecipes(), TestObjectRepository.allRecipesDto);
  }
  
  @Test
  public void getRecipe() {
    when(recipeRepo.findById(1)).thenReturn(Optional.of(TestObjectRepository.oneRecipeDao));
    assertEquals(recipeService.getRecipe(1), TestObjectRepository.oneRecipeDto);
  }
  
  @Test
  public void getNonexistentRecipe() {
    when(recipeRepo.findById(100)).thenThrow(new RecipeNotFoundException());
    expectedEx.expect(RecipeNotFoundException.class);
    recipeService.getRecipe(100);
  }
  
  @Test
  public void addRecipe() {
    when(recipeRepo.save(TestObjectRepository.newRecipeDao)).thenReturn(TestObjectRepository.newRecipeDao);
    assertEquals(recipeService.addRecipe(TestObjectRepository.newRecipeDto), TestObjectRepository.newRecipeDto);
  }
  
  @Test
  public void addInvalidRecipe() {
    when(recipeRepo.save(any(RecipeDao.class))).thenThrow(new ConstraintViolationException(null, null, null));
    expectedEx.expect(InvalidRecipeException.class);
    recipeService.addRecipe(TestObjectRepository.newRecipeDto);
  }
}
