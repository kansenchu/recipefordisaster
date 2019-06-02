package jp.softbank.kansenchu.recipefordisaster.dao;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;

/**
 * DBとやりとりする時使うレシピクラス.
 * ポイントとして、cost (値段)がintの基準でしています。
 */
@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDao {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /** レシピの名前. */
  @NotNull(message = "title")
  private String title;

  /** レシピの作り時間. */
  @NotNull(message = "making_time")
  @Column(name = "making_time")
  private String makingTime;

  /** レシピに対応する人数. */
  @NotNull(message = "serves")
  private String serves;

  /** 材料リスト。Listではなく、String扱いとしています. */
  @NotNull(message = "ingredients")
  private String ingredients;

  /** レシピの予測値段. */
  @NotNull(message = "cost")
  private String cost;

  /** レシピの作成時間. */ 
  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  /** レシピの作成時間. */
  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
  
  public RecipeDto mapToDto() {
    return RecipeDto.builder()
        .id(id)
        .title(title)
        .makingTime(makingTime)
        .serves(serves)
        .ingredients(ingredients)
        .cost(cost)
        .build();
  }
}  
