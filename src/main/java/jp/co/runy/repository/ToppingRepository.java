package jp.co.runy.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.domain.Topping;

/**
 * トッピングテーブルを操作するリポジトリ.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class ToppingRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final String TABLE_NAME = "toppings";
	/** Toppingオブジェクトを生成するローマッパー */
	private final static RowMapper<Topping> TOPPING_ROW_MAPPER = new BeanPropertyRowMapper<>(Topping.class);

	/**
	 * 商品タイプごとのトッピングを取得する.
	 * 
	 * @return トッピングリスト
	 */
	public List<Topping> findByType(String type) {
		String sql = "SELECT id,type,name,price_m,price_l FROM " + TABLE_NAME + " WHERE type=:type";

		SqlParameterSource param = new MapSqlParameterSource().addValue("type", type);

		List<Topping> ToppingList = template.query(sql, param, TOPPING_ROW_MAPPER);

		return ToppingList;
	}

//	/**
//	 * 主キー検索をする.
//	 * 
//	 * @param id トッピングID
//	 * @return トッピング情報
//	 */
//	public Topping load(Integer id) {
//		String sql = "SELECT id,type,name,price_m,price_l FROM " + TABLE_NAME
//				+ " where id=:id;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
//		Topping Topping = template.queryForObject(sql, param, TOPPING_ROW_MAPPER);
//		return Topping;
//	}

}
