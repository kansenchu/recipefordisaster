package jp.softbank.kansenchu.dto;

import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

/**
 * REST API でやりとりする時使うレシピクラス.
 * ポイントとして、値段がStringになります。
 */
@Data
@RequiredArgsConstructor
@JsonTypeName("recipe")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDto {
  private final int id;

  /** レシピの名前. */
  @NotNull
  private final String title;
  
  /** レシピの作り時間。実際JSONではmaking_timeになります. */
  @NotNull
  private final String makingTime;
  
  /** レシピに対応する人数. */
  @NotNull
  private final String serves; 
  
  /** 材料リスト。Listではなく、String扱いとしています. */
  @NotNull
  private final String ingredients; 

  /** レシピの予測値段。intではなく, Stringです. */
  @NotNull
  private final String cost; 
}
