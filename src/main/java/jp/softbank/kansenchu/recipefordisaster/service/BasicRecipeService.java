package jp.softbank.kansenchu.recipefordisaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.exception.InvalidRecipeException;
import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;
import jp.softbank.kansenchu.recipefordisaster.dao.RecipeDao;
import jp.softbank.kansenchu.recipefordisaster.repository.RecipeRepo;
import lombok.RequiredArgsConstructor;

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
      ArrayList<String> missing = new ArrayList<String>();
      if (recipeDto.getTitle() == null) {
        missing.add("title");
      }
      if (recipeDto.getMakingTime() == null) {
        missing.add("making_time");
      }
      if (recipeDto.getIngredients() == null) {
        missing.add("ingredients");
      }
      if (recipeDto.getServes() == null) {
        missing.add("serves");
      }
      if (recipeDto.getCost() == null) {
        missing.add("cost");
      }
      if (!missing.isEmpty()) {
        throw new InvalidRecipeException(String.join(",", missing));
      } else {
        throw ex;
      }
    }
  }

}
