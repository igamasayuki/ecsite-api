package jp.co.runy.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.domain.Item;

/**
 * 商品テーブルを操作するリポジトリクラス.
 * 
 * @author igamasayuki
 *
 */
@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	private static final String TABLE_NAME = "items";
	/** Itemオブジェクトを生成するローマッパー */
	private final static RowMapper<Item> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

	/**
	 * 商品情報を値段の安い順に取得する.
	 * 
	 * @param type 商品タイプ
	 * @return 商品リスト（値段の安い順）
	 */
	public List<Item> findByType(String type) {
		String sql = "SELECT id,type,name,description,price_m,price_l,image_path,deleted FROM " + TABLE_NAME
				+ " WHERE type=:type;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("type", type);

		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);

		return itemList;
	}

//	/**
//	 * 曖昧検索.
//	 * 
//	 * @param name
//	 * @return 曖昧検索結果（値段の安い順）
//	 */
//	public List<Item> findByLikeName(String searchName) {
//		String sql = "SELECT id,type,name,description,price_m,price_l,image_path,deleted FROM " + TABLE_NAME
//				+ " WHERE name ILIKE :name ORDER BY price_m, price_l;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + searchName + "%");
//
//		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
//		return itemList;
//	}
//
	/**
	 * 主キー検索をする.
	 * 
	 * @param id 商品ID
	 * @return 商品情報
	 */
	public Item load(Integer id) {
		String sql = "SELECT id,type,name,description,price_m,price_l,image_path,deleted FROM " + TABLE_NAME
				+ " where id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);
		return item;
	}
}
