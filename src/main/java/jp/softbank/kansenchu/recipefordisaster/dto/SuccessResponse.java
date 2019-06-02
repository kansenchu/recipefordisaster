package jp.softbank.kansenchu.recipefordisaster.dto;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 全レシピ返す用POJOクラス.
 */
@Data
@RequiredArgsConstructor
public class SuccessResponse {
  /**
   * 可能なメッセージを代表するenum.
   */
  public enum Message {
    RETRIEVED("Recipe details by id"),
    CREATED("Recipe successfully created!");

    private String message;
    
    private Message(String message) {
      this.message = message;
    }

    @JsonValue
    public String getMessage() {
      return message;
    }
  }
  
  final Message message;
  
  List<RecipeDto> recipe;

  /**
   * 一個のレシピを受けて直接リストに入れるコンスラクタ.
   * @param message 送りたいメッセージ
   * @param recipe 入れたいレシピ
   */
  public SuccessResponse(Message message, RecipeDto recipe) {
    this.message = message;
    this.recipe = Arrays.asList(recipe);
  }
}
