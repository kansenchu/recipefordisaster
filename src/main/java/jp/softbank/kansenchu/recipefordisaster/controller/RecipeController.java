package jp.softbank.kansenchu.recipefordisaster.controller;

import jp.softbank.kansenchu.recipefordisaster.dto.MultiRecipeResponse;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.dto.SuccessResponse;

public interface RecipeController {
  
  /**
   * 全レシピを取得する.
   * 
   * @return 全部のレシピが含めているレスポンス
   */
  public MultiRecipeResponse getRecipes();
 
  /**
   * 定義したレシピを取得する.
   * @param id 取得したいレシピID
   * @return 一個のレシピのレスポンス
   */
  public SuccessResponse getRecipe(int id);
  
  /**
   * レシピを加えるメソッド.
   * @param newRecipe 新しいレシピの詳細
   * @return 新しいレシピを含めてるレスポンス
   */
  public SuccessResponse addRecipe(RecipeDto newRecipe);
}
