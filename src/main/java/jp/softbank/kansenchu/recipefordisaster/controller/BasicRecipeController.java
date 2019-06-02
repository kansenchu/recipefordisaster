package jp.softbank.kansenchu.recipefordisaster.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.softbank.kansenchu.recipefordisaster.dto.MultiRecipeResponse;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.service.RecipeService;
import lombok.RequiredArgsConstructor;

/**
 * レシピの各リクエストを受付するクラス.
 */
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class BasicRecipeController implements RecipeController {
  
  final RecipeService recipeService;

  /**
   * 全レシピを取得する.
   * 
   * @return 全部のレシピが含めているレスポンス
   */
  @Override
  @GetMapping
  public MultiRecipeResponse getRecipes() {
    List<RecipeDto> recipeList = recipeService.getAllRecipes();
    return new MultiRecipeResponse(recipeList);
  }

}
