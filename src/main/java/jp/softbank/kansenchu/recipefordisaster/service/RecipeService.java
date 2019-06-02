package jp.softbank.kansenchu.recipefordisaster.service;

import java.util.List;

import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.dto.SuccessResponse;

public interface RecipeService {
  /**
   * 全レシピ取得.
   * @return 全レシピのリスト
   */
  public List<RecipeDto> getAllRecipes();
  
  /**
   * 指定したIDのレシピを返す.
   * @param id 欲しいレシピ
   * @return 指定したレシピ
   * @throws RecipeNotFoundException レシピ見つかれない時
   */
  public RecipeDto getRecipe(int id);
  
}