package jp.softbank.kansenchu.recipefordisaster.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.validation.constraints.NotNull;

import jp.softbank.kansenchu.recipefordisaster.dao.RecipeDao;
import jp.softbank.kansenchu.recipefordisaster.dto.views.RecipeViews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * REST API でやりとりする時使うレシピクラス.
 * ポイントとして、値段がStringになります。
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("recipe")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDto {
  @JsonView(RecipeViews.IncludeId.class)
  private int id;

  /** レシピの名前。 */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String title;
  
  /** レシピの作り時間。実際JSONではmaking_timeになります。 */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String makingTime;
  
  /** レシピに対応する人数。 */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String serves; 
  
  /** 材料リスト。Listではなく、String扱いとしています。 */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String ingredients; 

  /** レシピの予測値段。intではなく, Stringです。 */
  @JsonView(RecipeViews.ExcludeId.class)
  @NotNull
  private String cost;
  
  /**
   * レシピをDAOにマッピングする。
   * @return 変換したDAO
   */
  public RecipeDao mapToDao() {
    return RecipeDao.builder()
      .id(id)
      .title(title)
      .makingTime(makingTime)
      .serves(serves)
      .ingredients(ingredients)
      .cost(cost)
      .build();
  }
}
