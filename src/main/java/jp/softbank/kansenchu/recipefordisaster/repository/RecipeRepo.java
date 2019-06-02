package jp.softbank.kansenchu.recipefordisaster.repository;

import jp.softbank.kansenchu.recipefordisaster.dao.RecipeDao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * レシピをデータベースで管理するクラス.
 * JpaRepositoryを継承しているため、ただでデフォルトのデータベースからレシピ取り出すことができる。
 */
@Repository
public interface RecipeRepo extends JpaRepository<RecipeDao, Integer> {

}