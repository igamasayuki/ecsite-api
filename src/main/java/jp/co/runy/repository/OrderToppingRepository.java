package jp.co.runy.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jp.co.runy.domain.OrderTopping;


/**
 * 注文商品のトッピングテーブルを操作するリポジトリ.
 *
 * @author igamasayuki
 *
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 注文商品のトッピングテーブル名 */
	private final static String TABLE_ORDER_TOPPINGS = "order_toppings";

	/**
	 * 注文商品のトッピングを新規登録する.
	 *
	 * @param orderTopping 登録したい注文商品のトッピングドメイン
	 * @return 自動採番されたIDが入っている登録した注文商品のトッピングドメイン
	 */
	synchronized public OrderTopping insert(OrderTopping orderTopping) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		String sql = "INSERT INTO " + TABLE_ORDER_TOPPINGS
				+ "(topping_id,order_item_id) VALUES(:toppingId,:orderItemId);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumNames = { "id" };
		template.update(sql, param, keyHolder, keyColumNames);
		orderTopping.setId(keyHolder.getKey().intValue());
		return orderTopping;
	}
}
