package jp.softbank.kansenchu.recipefordisaster;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import jp.softbank.kansenchu.recipefordisaster.dao.RecipeDao;
import jp.softbank.kansenchu.recipefordisaster.dto.MultiRecipeResponse;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;
import jp.softbank.kansenchu.recipefordisaster.dto.SuccessResponse;

public class TestObjectRepository {
  public static RecipeDao oneRecipeDao = new RecipeDao(
      1,
      "チキンカレー",
      "45分",
      "4人",
      "玉ねぎ,肉,スパイス",
      1000,
      new Timestamp(getMillisecondFromDateString("2016-01-10 12:10:12")),
      new Timestamp(getMillisecondFromDateString("2016-01-10 12:10:12"))
  );
  public static RecipeDto oneRecipeDto = oneRecipeDao.mapToDto();
  
  public static RecipeDao newRecipeDao = new RecipeDao(
    3,
    "豚骨ラーメン",
    "8時間",
    "100人",
    "玉ねぎ,肉,スパイス",
    50000,
    new Timestamp(Instant.now().getEpochSecond()),
    new Timestamp(Instant.now().getEpochSecond())
  );
  public static RecipeDto newRecipeDto = newRecipeDao.mapToDto();
  
  public static List<RecipeDao> allRecipes = Arrays.asList(
      oneRecipeDao,
      new RecipeDao(
          2,
          "オムライス",
          "30分",
          "2人",
          "玉ねぎ,卵,スパイス,醤油",
          700,
          new Timestamp(getMillisecondFromDateString("2016-01-11 13:10:12")),
          new Timestamp(getMillisecondFromDateString("2016-01-11 13:10:12"))
      )
  );
  public static List<RecipeDto> allRecipesDto = allRecipes.stream()
      .map(RecipeDao::mapToDto).collect(Collectors.toList());
  
  public static MultiRecipeResponse allRecipesResponse = new MultiRecipeResponse(allRecipesDto);
  public static SuccessResponse getOneResponse = new SuccessResponse(SuccessResponse.Message.RETRIEVED, TestObjectRepository.oneRecipeDto);
  public static SuccessResponse addOneResponse = new SuccessResponse(SuccessResponse.Message.CREATED, TestObjectRepository.newRecipeDto);
  
  private static long getMillisecondFromDateString(String dateString) {
    return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
             .atZone(ZoneId.systemDefault()).toEpochSecond();
  }
}
