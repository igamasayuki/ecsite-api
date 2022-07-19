package jp.co.runy.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import jp.co.runy.domain.Order;
import jp.co.runy.domain.OrderItem;
import jp.co.runy.domain.OrderTopping;
import jp.co.runy.domain.Topping;

/**
 * 注文テーブルを操作するリポジトリ.
 *
 * @author igamasayuki
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

//	/** 注文テーブル名 */
//	private final static String TABLE_ORDER = "orders";
	/** 注文商品テーブル名 */
//	private final static String TABLE_ORDER_ITEMS = "order_items";
	/** 商品テーブル名 */
//	private final static String TABLE_ITEMS = "items";
	/** 注文商品のトッピングテーブル名 */
//	private final static String TABLE_ORDER_TOPPINGS = "order_toppings";
	/** トッピングテーブル名 */
//	private final static String TABLE_TOPPINGS = "toppings";
	/** 別名付き注文テーブル名 */
	private final static String TABLE_ORDER_WITH_ALIAS = "orders o";
	/** 別名付き注文商品テーブル名 */
	private final static String TABLE_ORDER_ITEMS_WITH_ALIAS = "order_items oi";
	/** 別名付き商品テーブル名 */
	private final static String TABLE_ITEMS_WITH_ALIAS = "items i";
	/** 別名付き注文商品のトッピングテーブル名 */
	private final static String TABLE_ORDER_TOPPINGS_WITH_ARIAS = "order_toppings ot";
	/** 別名付きトッピングテーブル名 */
	private final static String TABLE_TOPPINGS_WITH_ARIAS = "toppings t";
	/** ユーザーテーブル名 */
//	private final static String TABLE_USERS = "users";

	private final static String INSERT_ORDER_COLUMNS = "user_id,status,total_price,order_date,destination_name,destination_email,destination_zipcode,destination_address,destination_tel,delivery_time,payment_method";
	/** 注文テーブルの要素を全て取得するSELECT */
	private final static String ORDER_SELECT = " o.id AS order_id,o.user_id AS order_user_id, o.status, o.total_price, o.order_date, o.destination_name,o.destination_email, o.destination_zipcode, o.destination_address, o.destination_tel, o.delivery_time, o.payment_method ";
	/** 注文商品テーブルの要素を全て取得するSELECT */
	private final static String ORDER_ITEMS_SELECT = " oi.id AS order_item_id, oi.item_id AS order_item_item_id ,oi.order_id AS order_item_order_id, oi.quantity, oi.size ";
	/** 商品テーブルの要素を全て取得するSELECT */
	private final static String ITEMS_SELECT = " i.id AS item_id,i.type AS item_type, i.name AS item_name, i.description, i.price_m AS item_price_m, i.price_l AS item_price_l, i.image_path, i.deleted ";
	/** 注文トッピングテーブルの要素を全て取得するSELECT */
	private final static String ORDER_TOPPINGS_SELECT = " ot.id AS order_topping_id, ot.topping_id AS order_topping_topping_id, ot.order_item_id AS order_topping_order_item_id ";
	/** トッピングテーブルの要素を全て取得するSELECT */
	private final static String TOPPINGS_SELECT = " t.id AS topping_id,t.type AS topping_type, t.name AS topping_name, t.price_m AS topping_price_m, t.price_l AS topping_price_l ";
//	/** ユーザーテーブルの要素を全て取得するSELECT */
//	private final static String USERS_SELECT = " u.id AS user_id, u.name AS user_name, u.email AS user_email, u.zipcode AS user_zipCode, u.address AS user_address, u.telephone AS user_telephone ";

//	/** 注文ドメインのみを取得する受け皿 */
//	private final static RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
//		return setOrderFromDb(rs);
//	};

	/** 注文ドメインおよびそれに付随する全てのドメインを取得する受け皿 */
	private final static ResultSetExtractor<List<Order>> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Order> orderList = new ArrayList<>();
		Order order = null;
		List<OrderItem> orderItemList = null;
		OrderItem orderItem = null;
		Item item = null;
		List<OrderTopping> orderToppingList = null;
		OrderTopping orderTopping = null;
		Topping topping = null;

		int nowOrderId = 0;
		int beforeOrderId = -1;

		int nowOrderItemId = 0;
		int beforeOrderItemId = -1;

		while (rs.next()) {

			nowOrderId = rs.getInt("order_id");
			nowOrderItemId = rs.getInt("order_item_id");

			if (nowOrderId != beforeOrderId) {
				order = setOrderFromDb(rs);
				orderList.add(order);
				order.setId(rs.getInt("order_id"));
				order.setUserId(rs.getInt("order_user_id"));
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipcode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDestinationTel(rs.getString("destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("delivery_time"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				orderItemList = new ArrayList<>();
				order.setOrderItemList(orderItemList);

			}
			if (nowOrderItemId != beforeOrderItemId) {
				orderItem = new OrderItem();
				if (rs.getInt("order_item_id") != 0) {
					orderItemList.add(orderItem);
					orderItem.setId(rs.getInt("order_item_id"));
					orderItem.setItemId(rs.getInt("item_id"));
					orderItem.setOrderId(rs.getInt("order_id"));
					orderItem.setQuantity(rs.getInt("quantity"));
					orderItem.setSize(rs.getString("size").charAt(0));
					item = new Item();
					item.setId(rs.getInt("item_id"));
					item.setType(rs.getString("item_type"));
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
				topping.setType(rs.getString("topping_type"));
				topping.setName(rs.getString("topping_name"));
				topping.setPriceM(rs.getInt("topping_price_m"));
				topping.setPriceL(rs.getInt("topping_price_l"));
			} else {
				orderToppingList = null;
			}

			beforeOrderId = nowOrderId;
			beforeOrderItemId = nowOrderItemId;
		}
		return orderList;
	};

	/**
	 * Orderを新規登録する.
	 *
	 * @param order 登録したいOrderのドメイン
	 * @return OrderIdの割り振られたOrderドメイン
	 */
	synchronized public Order insert(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		String sql = "INSERT INTO orders (" + INSERT_ORDER_COLUMNS
				+ ") VALUES(:userId,:status,:totalPrice,:orderDate,:destinationName,:destinationEmail,:destinationZipcode,:destinationAddress,:destinationTel,:deliveryTime,:paymentMethod)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String[] keyColumNames = { "id" };
		template.update(sql, param, keyHolder, keyColumNames);
		order.setId(keyHolder.getKey().intValue());
		return order;
	}

//	/**
//	 * ユーザーIDと注文状態を指定して注文ドメインを検索する.
//	 *
//	 * @param userId 検索したいユーザーID
//	 * @return もし注文前の注文があればそのorderId,なければnull
//	 */
//	public List<Order> findByUserIdANDStatus(String userId, int status) {
//		String sql = "SELECT " + ORDER_SELECT + " FROM " + TABLE_ORDER_WITH_ALIAS
//				+ " WHERE user_id=:userId AND status=:status;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
//		List<Order> orderList = template.query(sql, param, ORDER_ROW_MAPPER);
//		return orderList;
//	}

	/**
	 * 注文一覧を検索する.
	 * 
	 * @param type   商品タイプ
	 * @param userId ユーザID
	 * @return 注文一覧
	 */
	public List<Order> findAllFullOrderByType(String type, int userId) {
		String sql = "SELECT" + ORDER_SELECT + "," + ORDER_ITEMS_SELECT + "," + ITEMS_SELECT + ","
				+ ORDER_TOPPINGS_SELECT + "," + TOPPINGS_SELECT + " FROM " + TABLE_ORDER_WITH_ALIAS
				+ " LEFT OUTER JOIN " + TABLE_ORDER_ITEMS_WITH_ALIAS + " ON o.id=oi.order_id " + "LEFT OUTER JOIN "
				+ TABLE_ITEMS_WITH_ALIAS + " ON i.id=oi.item_id " + "LEFT OUTER JOIN " + TABLE_ORDER_TOPPINGS_WITH_ARIAS
				+ "  ON oi.id=ot.order_item_id " + "LEFT OUTER JOIN " + TABLE_TOPPINGS_WITH_ARIAS
				+ " ON t.id=ot.topping_id " + " WHERE i.type=:type and o.user_id=:userId;";
//				+ " ORDER BY o.id,oi.id,ot.id;";
		System.out.println(sql);
		System.out.println("type:" + type + "/userId:" + userId);
		SqlParameterSource param = new MapSqlParameterSource().addValue("type", type).addValue("userId", userId);
		List<Order> orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
		return orderList;
	}

//	/**
//	 * idによって注文を取得する.
//	 *
//	 * @param id 取得したい注文のID
//	 * @return 指定したIDの注文
//	 */
//	public Order load(int id) {
//		String sql = "SELECT" + ORDER_SELECT + "," + ORDER_ITEMS_SELECT + "," + ITEMS_SELECT + ","
//				+ ORDER_TOPPINGS_SELECT + "," + TOPPINGS_SELECT + " FROM " + TABLE_ORDER_WITH_ALIAS
//				+ " LEFT OUTER JOIN " + TABLE_ORDER_ITEMS_WITH_ALIAS + " ON o.id=oi.order_id " + "LEFT OUTER JOIN "
//				+ TABLE_ITEMS_WITH_ALIAS + " ON i.id=oi.item_id " + "LEFT OUTER JOIN " + TABLE_ORDER_TOPPINGS_WITH_ARIAS
//				+ "  ON oi.id=ot.order_item_id " + "LEFT OUTER JOIN " + TABLE_TOPPINGS_WITH_ARIAS
//				+ " ON t.id=ot.topping_id " + " WHERE o.id=:id " + " ORDER BY o.id,oi.id,ot.id;";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
//		List<Order> orderList = template.query(sql, param, ORDER_RESULT_SET_EXTRACTOR);
//		if (orderList.size() == 0) {
//			return null;
//		}
//		return orderList.get(0);
//	}

//	/**
//	 * 注文情報をアップデートする.
//	 * 
//	 * @param order 注文情報
//	 */
//	synchronized public void updateOrder(Order order) {
//		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
//		String sql = "UPDATE " + TABLE_ORDER
//				+ " SET status=:status,total_price=:totalPrice,order_date=:orderDate,destination_name=:destinationName,destination_email=:destinationEmail,destination_Zipcode=:destinationZipcode,destination_address=:destinationAddress,destination_tel=:destinationTel,delivery_time=:deliveryTime,payment_method=:paymentMethod WHERE id=:id";
//		template.update(sql, param);
//	}

	/**
	 * DBからとってきた内容をOrderドメインにする.
	 *
	 * @param rs 検索した結果のResultSet
	 * @return 取得したOrderドメイン
	 * @throws SQLException DBに伴うエラー
	 */
	private static Order setOrderFromDb(ResultSet rs) throws SQLException {

		Order order = new Order();
		order.setId(rs.getInt("order_id"));
		order.setUserId(rs.getInt("order_user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));

		return order;
	}

}
