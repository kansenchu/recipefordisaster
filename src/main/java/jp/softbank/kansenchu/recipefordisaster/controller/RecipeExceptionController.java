package jp.softbank.kansenchu.recipefordisaster.controller;

import jp.softbank.kansenchu.recipefordisaster.dto.ErrorResponse;
import jp.softbank.kansenchu.recipefordisaster.exception.InvalidRecipeException;
import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;

/**
 * このアプリケーションのエラーハンドラーを列挙するインターフェース。
 */
public interface RecipeExceptionController {
  
  /**
   * レシピが見つかれない時対応するハンドラー。
   * @param ex もらったエクセプション
   * @return 決定のエラーメッセージ
   */
  public ErrorResponse recipeNotFoundHandler(RecipeNotFoundException ex);

  /**
   * 不正レシピ作成しようとする時に対応するハンドラー。
   * @param ex もらったエクセプション
   * @return 決定のエラーメッセージ
   */
  public ErrorResponse invalidRecipeHandler(InvalidRecipeException ex);

}
