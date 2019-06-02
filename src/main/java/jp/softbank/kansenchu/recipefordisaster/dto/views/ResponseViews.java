package jp.softbank.kansenchu.recipefordisaster.dto.views;

/**
 * 普段メッセージレスポンスを返す用POJOクラス.
 */
public class ResponseViews {
  public static interface MessageOnly {}
  
  public static interface MessageWithRecipe extends MessageOnly, RecipeViews.ExcludeId {}
}