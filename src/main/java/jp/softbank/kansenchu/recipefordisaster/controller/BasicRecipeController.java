package jp.softbank.kansenchu.recipefordisaster.controller;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

import jp.softbank.kansenchu.recipefordisaster.dto.ErrorResponse;
import jp.softbank.kansenchu.recipefordisaster.dto.MultiRecipeResponse;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.dto.SuccessResponse;
import jp.softbank.kansenchu.recipefordisaster.dto.views.ResponseViews;
import jp.softbank.kansenchu.recipefordisaster.exception.InvalidRecipeException;
import jp.softbank.kansenchu.recipefordisaster.exception.RecipeNotFoundException;
import jp.softbank.kansenchu.recipefordisaster.service.RecipeService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * レシピの各リクエストを受付するクラス.
 */
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class BasicRecipeController implements RecipeController {
  
  final RecipeService recipeService;

  /**
   * {@inheritDoc}
   */
  @GetMapping
  public MultiRecipeResponse getRecipes() {
    List<RecipeDto> recipeList = recipeService.getAllRecipes();
    return new MultiRecipeResponse(recipeList);
  }
  
  /**
   * {@inheritDoc}
   */
  @JsonView(ResponseViews.MessageWithRecipe.class)
  @GetMapping(value = "/{id}")
  public SuccessResponse getRecipe(@PathVariable int id) {
    return new SuccessResponse(SuccessResponse.Message.RETRIEVED,
        recipeService.getRecipe(id));
  }
  
  /**
   * レシピを加えるメソッド.
   * @param newRecipe 新しいレシピの詳細
   * @return 新しいレシピを含めてるレスポンス
   */
  @JsonView(ResponseViews.MessageWithRecipe.class)
  @PostMapping
  public SuccessResponse addRecipe(@RequestBody RecipeDto newRecipe) {       
    RecipeDto actualRecipe = recipeService.addRecipe(newRecipe);
    return new SuccessResponse(SuccessResponse.Message.CREATED, actualRecipe);
  }
  
  /**
   * @{inheritDoc}
   */
  @JsonView(ResponseViews.MessageWithRecipe.class)
  @PatchMapping(value = "/{id}")
  public SuccessResponse editRecipe(@PathVariable int id, @RequestBody RecipeDto recipe) {
    RecipeDto newRecipe = recipeService.editRecipe(id, recipe);
    return new SuccessResponse(SuccessResponse.Message.UPDATED, newRecipe);
  }
  
  /**
   * {@inheritDoc}
   */
  @JsonView(ResponseViews.MessageOnly.class)
  @DeleteMapping(value = "/{id}")
  public SuccessResponse deleteRecipe(@PathVariable int id) {
    recipeService.deleteRecipe(id);
    return new SuccessResponse(SuccessResponse.Message.DELETED);
  }
  
  /**
   * レシピが見つかれない時対応するハンドラー.
   * @param ex もらったエクセプション
   * @return 決定のエラーメッセージ
   */
  @JsonView(ResponseViews.MessageOnly.class)
  @ExceptionHandler(RecipeNotFoundException.class)
  @ResponseStatus(HttpStatus.OK)
  public ErrorResponse recipeNotFoundHandler(RecipeNotFoundException ex) {
    return new ErrorResponse(ErrorResponse.Message.NOT_FOUND);
  }

  @ExceptionHandler(InvalidRecipeException.class)
  @ResponseStatus(HttpStatus.OK)
  public ErrorResponse invalidRecipeHandler(InvalidRecipeException ex) {
    return new ErrorResponse(ErrorResponse.Message.CREATION_FAILED, ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.OK)
  public ErrorResponse invalidRecipeHandler(Exception ex) {
    System.out.println(ex.getCause().getMessage());
    return new ErrorResponse(ErrorResponse.Message.CREATION_FAILED, ex.getMessage());
  }

}
