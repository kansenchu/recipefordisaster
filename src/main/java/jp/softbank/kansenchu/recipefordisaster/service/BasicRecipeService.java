package jp.softbank.kansenchu.recipefordisaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.dao.RecipeDao;
import jp.softbank.kansenchu.recipefordisaster.repository.RecipeRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicRecipeService implements RecipeService {
  
  final RecipeRepo repository;

  /**
   * 全レシピ取得.
   * @return 全レシピのリスト
   */
  public List<RecipeDto> getAllRecipes() {
    return repository.findAll(Sort.by("id").ascending())
        .parallelStream()
        .map(RecipeDao::mapToDto)
        .collect(Collectors.toList());
  }

}
