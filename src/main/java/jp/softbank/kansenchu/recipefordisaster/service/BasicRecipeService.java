package jp.softbank.kansenchu.recipefordisaster.service;

import java.util.ArrayList;
import java.util.List;

import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;

public class BasicRecipeService implements RecipeService {

  /**
   * 全レシピ取得.
   * @return 全レシピのリスト
   */
  public List<RecipeDto> getAllRecipes() {
    return new ArrayList<>();
  }

}
