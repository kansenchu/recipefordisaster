package jp.softbank.kansenchu.recipefordisaster.controller;

import jp.softbank.kansenchu.recipefordisaster.dto.MultiRecipeResponse;

public interface RecipeController {
  
  /**
   * 全レシピを取得する.
   * 
   * @return 全部のレシピが含めているレスポンス
   */
  public MultiRecipeResponse getRecipes();
  
}
