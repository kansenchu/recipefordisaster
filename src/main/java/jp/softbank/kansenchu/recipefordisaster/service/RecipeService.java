package jp.softbank.kansenchu.recipefordisaster.service;

import java.util.List;

import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;

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
  
  /**
   * レシピをDBに加える.
   * @param recipeDto 加えるレシピの情報
   * @return 実際にDBに存在してる新しいレシピ
   * @throws InvalidRecipeException レシピがあっていない時
   */
  public RecipeDto addRecipe(RecipeDto recipeDto);
  
  /**
   * 現在存在してるレシピを更新.
   * @param id 変えたいレシピ
   * @param recipeDto 新しい詳細
   * @return 更新されたレシピ
   * @throws InvalidRecipeException レシピが見つからない時
   */
  public RecipeDto editRecipe(int id, RecipeDto recipeDto);
  
  /**
   * レシピを削除する.
   * @param id 削除したいレシピID
   * @return 削除したレシピ
   * @throws RecipeNotFoundException レシピが見つからない時
   */
  public RecipeDto deleteRecipe(int id);
}