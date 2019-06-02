package jp.softbank.kansenchu.recipefordisaster.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import jp.softbank.kansenchu.recipefordisaster.TestObjectRepository;
import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;
import jp.softbank.kansenchu.recipefordisaster.repository.RecipeRepo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

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
    when(recipeRepo.save(TestObjectRepository.newRecipeNoTimestamp)).thenReturn(TestObjectRepository.newRecipeDao);
    assertEquals(recipeService.addRecipe(TestObjectRepository.newRecipeNoId), TestObjectRepository.newRecipeDto);
  }
  
  @Test
  public void editRecipe() {
    when(recipeRepo.findById(1)).thenReturn(Optional.of(TestObjectRepository.oneRecipeDao));
    when(recipeRepo.save(TestObjectRepository.editedRecipeDao)).thenReturn(TestObjectRepository.editedRecipeDao);
    assertEquals(recipeService.editRecipe(1, TestObjectRepository.newRecipeDto), TestObjectRepository.editedRecipeDao.mapToDto());
  }
  
  @Test
  public void editNonexistentRecipe() {
    when(recipeRepo.findById(1)).thenReturn(Optional.empty());
    expectedEx.expect(RecipeNotFoundException.class);
    recipeService.editRecipe(1, TestObjectRepository.newRecipeDto);
  }
  
  @Test
  public void deleteRecipe() {
    when(recipeRepo.findById(1)).thenReturn(Optional.of(TestObjectRepository.oneRecipeDao));
    assertEquals(recipeService.deleteRecipe(1), TestObjectRepository.oneRecipeDto);
    verify(recipeRepo, times(1)).delete(TestObjectRepository.oneRecipeDao);
  }
  
  @Test
  public void deleteNonexistentRecipe() {
    when(recipeRepo.findById(1)).thenReturn(Optional.empty());
    expectedEx.expect(RecipeNotFoundException.class);
    recipeService.deleteRecipe(1);
  }
}
