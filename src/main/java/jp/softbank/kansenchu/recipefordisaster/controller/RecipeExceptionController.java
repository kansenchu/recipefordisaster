package jp.softbank.kansenchu.recipefordisaster.controller;

import jp.softbank.kansenchu.recipefordisaster.dto.ErrorResponse;
import jp.softbank.kansenchu.recipefordisaster.exception.InvalidRecipeException;
import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;

/**
 * このアプリケーションのエラーハンドラーを列挙するインターフェース。
 */
public interface RecipeExceptionController {
  
  public ErrorResponse recipeNotFoundHandler(RecipeNotFoundException ex);

  public ErrorResponse invalidRecipeHandler(InvalidRecipeException ex);

}
