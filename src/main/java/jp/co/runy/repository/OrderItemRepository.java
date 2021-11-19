package jp.co.runy.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import jp.co.runy.domain.Item;
import jp.co.runy.domain.OrderItem;
import jp.co.runy.domain.OrderTopping;
import jp.co.runy.domain.Topping;


/**
 * 注文商品テーブルを操作するリポジトリ.
 *
 * @author igamasayuki
 *
 */
@Repository
public class OrderItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/** 注文商品テーブルの要素を全て取得するSELECT */
	private final static String ORDER_ITEMS_SELECT = " oi.id AS order_item_id, oi.item_id AS order_item_item_id ,oi.order_id AS order_item_order_id, oi.quantity, oi.size ";
	/** 商品テーブルの要素を全て取得するSELECT */
	private final static String ITEMS_SELECT = " i.id AS item_id, i.name AS item_name, i.description, i.price_m AS item_price_m, i.price_l AS item_price_l, i.image_path, i.deleted ";
	/** 注文トッピングテーブルの要素を全て取得するSELECT */
	private final static String ORDER_TOPPINGS_SELECT = " ot.id AS order_topping_id, ot.topping_id AS order_topping_topping_id, ot.order_item_id AS order_topping_order_item_id ";
	/** トッピングテーブルの要素を全て取得するSELECT */
	private final static String TOPPINGS_SELECT = " t.id AS topping_id, t.name AS topping_name, t.price_m AS topping_price_m, t.price_l AS topping_price_l ";
	/** 別名付き注文商品テーブル */
	private final static String TABLE_ORDER_ITEMS_WITH_ALIAS = "order_items oi";
	/** 別名付き商品テーブル名 */
	private final static String TABLE_ITEMS_WITH_ALIAS = "items i";
	/** 別名付き注文商品のトッピングテーブル名 */
	private final static String TABLE_ORDER_TOPPINGS_WITH_ARIAS = "order_toppings ot";
	/** 別名付きトッピングテーブル名 */
	private final static String TABLE_TOPPINGS_WITH_ARIAS = "toppings t";

	/** 注文商品テーブル名 */
	private final static String TABLE_ORDER_ITEMS = "order_items";

	private final static ResultSetExtractor<List<OrderItem>> ORDER_ITEM_RESULT_SET_EXTRACTOR = (rs) -> {
		List<OrderItem> orderItemList = new ArrayList<>();
		OrderItem orderItem = null;
		Item item = null;
		List<OrderTopping> orderToppingList = null;
		OrderTopping orderTopping = null;
		Topping topping = null;

		int nowOrderItemId = 0;
		int beforeOrderItemId = -1;

		while (rs.next()) {

			if (nowOrderItemId != beforeOrderItemId) {
				if (rs.getInt("order_item_id") != 0) {
					orderItem = new OrderItem();
					orderItemList.add(orderItem);
					orderItem.setId(rs.getInt("order_item_id"));
					orderItem.setItemId(rs.getInt("item_id"));
					orderItem.setOrderId(rs.getInt("order_item_order_id"));
					orderItem.setQuantity(rs.getInt("quantity"));
					orderItem.setSize(rs.getString("size").charAt(0));
					item = new Item();
					item.setId(rs.getInt("item_id"));
					item.setName(rs.getString("item_name"));
					item.setDescription(rs.getString("description"));
					item.setPriceM(rs.getInt("item_price_m"));
					item.setPriceL(rs.getInt("item_price_l"));
					item.setImagePath(rs.getString("image_path"));
					item.setDeleted(rs.getBoolean("deleted"));
					orderItem.setItem(item);
					orderToppingList = new ArrayList<>();
					orderItem.setOrderToppingList(orderToppingList);
				} else {
					orderItemList = null;
					orderItem = null;
				}
			}

			if (rs.getInt("order_topping_id") != 0) {
				orderTopping = new OrderTopping();
				orderToppingList.add(orderTopping);
				orderTopping.setId(rs.getInt("order_topping_id"));
				orderTopping.setToppingId(rs.getInt("topping_id"));
				orderTopping.setOrderItemId(rs.getInt("order_item_id"));
				topping = new Topping();
				orderTopping.setTopping(topping);
				topping.setId(rs.getInt("topping_id"));
				topping.setName(rs.getString("topping_name"));
				topping.setPriceM(rs.getInt("topping_price_m"));
				topping.setPriceL(rs.getInt("topping_price_l"));
			} else {
				orderToppingList = null;
			}

			beforeOrderItemId = nowOrderItemId;
		}
		return orderItemList;
	};

	/**
	 * 注文商品をDBに新規登録する.
	 *
	 * @param orderItem 登録したい注文商品
	 * @return IDが自動採番済の注文商品ドメイン
	 */
	synchronized public OrderItem insert(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		String sql = "INSERT INTO " + TABLE_ORDER_ITEMS
				+ "(item_id,order_id,quantity,size) VALUES (:itemId,:orderId,:quantity,:size);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumNames = { "id" };
		template.update(sql, param, keyHolder, keyColumNames);
		orderItem.setId(keyHolder.getKey().intValue());
		return orderItem;
	}

	/**
	 * 注文IDを別の注文IDに変更する.
	 *
	 * @param beforeOrderId 変更したい注文ID
	 * @param nextOrderId   変更後の注文ID
	 */
	synchronized public void updateOrderIdByOrderId(int beforeOrderId, int nextOrderId) {
		String sql = "UPDATE " + TABLE_ORDER_ITEMS + " SET order_id=:nextOrderId WHERE order_id=:beforeOrderId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("beforeOrderId", beforeOrderId)
				.addValue("nextOrderId", nextOrderId);
		template.update(sql, param);
	}

	/**
	 * 注文商品を変更する.
	 *
	 * @param orderItem 変更したい内容の入った注文商品ドメイン
	 */
	synchronized public void update(OrderItem orderItem) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderItem);
		String sql = "UPDATE " + TABLE_ORDER_ITEMS
				+ " SET item_id=:itemId, order_id=:orderId, quantity=:quantity, size=:size WHERE id=:id;";
		template.update(sql, param);
	}

	/**
	 * idによって注文商品を取得する.
	 *
	 * @param id 取得したい注文商品のID
	 * @return 指定したIDの注文商品
	 */
	public OrderItem load(int id) {
		String sql = "SELECT " + ORDER_ITEMS_SELECT + "," + ITEMS_SELECT + "," + ORDER_TOPPINGS_SELECT + ","
				+ TOPPINGS_SELECT + " FROM " + TABLE_ORDER_ITEMS_WITH_ALIAS + " LEFT OUTER JOIN "
				+ TABLE_ITEMS_WITH_ALIAS + " ON i.id=oi.item_id " + " LEFT OUTER JOIN "
				+ TABLE_ORDER_TOPPINGS_WITH_ARIAS + " ON oi.id=ot.order_item_id LEFT OUTER JOIN "
				+ TABLE_TOPPINGS_WITH_ARIAS + " ON t.id=ot.topping_id WHERE oi.id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<OrderItem> orderItemList = template.query(sql, param, ORDER_ITEM_RESULT_SET_EXTRACTOR);
		if (orderItemList.size() == 0) {
			return null;
		}
		return orderItemList.get(0);
	}

	/**
	 * 指定したIDのデータを削除する.
	 *
	 * @param id 削除したい注文のID
	 */
	synchronized public void deleteById(int id) {
		String sql = "DELETE FROM " + TABLE_ORDER_ITEMS + " WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}
}
