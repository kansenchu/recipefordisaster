package jp.softbank.kansenchu.recipefordisaster.service;

import java.util.List;

import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;

public interface RecipeService {
  /**
   * 全レシピ取得.
   * @return 全レシピのリスト
   */
  public List<RecipeDto> getAllRecipes();
  
}