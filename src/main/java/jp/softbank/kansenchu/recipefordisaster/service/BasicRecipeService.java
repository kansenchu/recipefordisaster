package jp.softbank.kansenchu.recipefordisaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import jp.softbank.kansenchu.recipefordisaster.dao.RecipeDao;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.exception.InvalidRecipeException;
import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;
import jp.softbank.kansenchu.recipefordisaster.repository.RecipeRepo;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicRecipeService implements RecipeService {
  
  final RecipeRepo repository;

  /**
   * {@inheritDoc}
   */
  public List<RecipeDto> getAllRecipes() {
    return repository.findAll(Sort.by("id").ascending())
        .parallelStream()
        .map(RecipeDao::mapToDto)
        .collect(Collectors.toList());
  }
  
  /**
   * {@inheritDoc}
   */
  public RecipeDto getRecipe(int id) {
    return repository.findById(id)
        .orElseThrow(RecipeNotFoundException::new)
        .mapToDto();
  }

  /**
   * {@inheritDoc}
   */
  public RecipeDto addRecipe(RecipeDto recipeDto) {
    try {
      RecipeDao toSave = recipeDto.mapToDao();
      return repository.save(toSave).mapToDto();
    } catch (ConstraintViolationException ex) {
      List<String> errorFields =  ex.getConstraintViolations()
          .stream()
          .map(ConstraintViolation::getMessage)
          .collect(Collectors.toList());
      
      // there should be a better way to sort the fields based on arbitrary order
      List<String> result = new ArrayList<>();
      if (errorFields.contains("title")) { 
        result.add("title");
      }
      if (errorFields.contains("making_time")) {
        result.add("making_time");
      }
      if (errorFields.contains("serves")) {
        result.add("serves");
      }
      if (errorFields.contains("ingredients")) {
        result.add("ingredients");
      }
      if (errorFields.contains("cost")) {
        result.add("cost");
      }
      
      throw new InvalidRecipeException(String.join(", ", result));
      
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public RecipeDto editRecipe(int id, RecipeDto recipeDto) {
    return repository.findById(id).map((Function<RecipeDao, RecipeDto>) oldRecipe -> {
      if (recipeDto.getTitle() != null) {
        oldRecipe.setTitle(recipeDto.getTitle());
      }
      if (recipeDto.getMakingTime() != null) {
        oldRecipe.setMakingTime(recipeDto.getMakingTime());
      }
      if (recipeDto.getServes() != null) {
        oldRecipe.setServes(recipeDto.getServes());
      }
      if (recipeDto.getIngredients() != null) {
        oldRecipe.setIngredients(recipeDto.getIngredients());
      }
      if (recipeDto.getCost() != null) {
        oldRecipe.setCost(recipeDto.getCost());
      }
      return repository.save(oldRecipe).mapToDto();
    }).orElseThrow(RecipeNotFoundException::new);
  }
  
  /**
   * {@inheritDoc}
   */
  public RecipeDto deleteRecipe(int id) {
    return repository.findById(id)
        .map((Function<RecipeDao, RecipeDto>) recipe -> {
          repository.delete(recipe);
          return recipe.mapToDto();
        }).orElseThrow(RecipeNotFoundException::new);
  }

}
