package jp.softbank.kansenchu.recipefordisaster.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;

import jp.softbank.kansenchu.recipefordisaster.dto.views.ResponseViews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * エラーのレスポンス用POJOクラス。
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
  public enum Message {
    NOT_FOUND("No Recipe found"),
    CREATION_FAILED("Recipe creation failed!");

    private String message;
    
    private Message(String message) {
      this.message = message;
    }

    @JsonValue
    public String getMessage() {
      return message;
    }
  }

  /** リクエストに返信メッセージ。 */
  @JsonView(ResponseViews.MessageOnly.class)
  final Message message;

  @JsonView(ResponseViews.MessageWithRequired.class)
  /** 欠けてるパラメータのString. */
  String required;

}

