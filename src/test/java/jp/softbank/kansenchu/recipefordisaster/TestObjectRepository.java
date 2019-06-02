package jp.softbank.kansenchu.recipefordisaster;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;

import jp.softbank.kansenchu.recipefordisaster.dao.RecipeDao;
import jp.softbank.kansenchu.recipefordisaster.dto.RecipeDto;

public class TestObjectRepository {
  public static RecipeDao oneRecipe = new RecipeDao(
      1,
      "チキンカレー",
      "45分",
      "4人",
      "玉ねぎ,肉,スパイス",
      1000,
      new Timestamp(getMillisecondFromDateString("2016-01-10 12:10:12")),
      new Timestamp(getMillisecondFromDateString("2016-01-10 12:10:12"))
  );
  
  public static RecipeDao newRecipe = new RecipeDao(
    3,
    "豚骨ラーメン",
    "8時間",
    "100人",
    "玉ねぎ,肉,スパイス",
    50000,
    new Timestamp(getMillisecondFromDateString("2016-01-10 12:10:12")),
    new Timestamp(getMillisecondFromDateString("2016-01-10 12:10:12"))
  );
  
  public static List<RecipeDao> allRecipes = new ArrayList<>();
  
  static {
      allRecipes.add(oneRecipe);
      allRecipes.add(new RecipeDao(
          2,
          "オムライス",
          "30分",
          "2人",
          "玉ねぎ,卵,スパイス,醤油",
          700,
          new Timestamp(getMillisecondFromDateString("2016-01-11 13:10:12")),
          new Timestamp(getMillisecondFromDateString("2016-01-11 13:10:12"))
      ));
  };
  
  private static long getMillisecondFromDateString(String dateString) {
    return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
             .atZone(ZoneId.systemDefault()).toEpochSecond();
  }
}
