package jp.co.runy.form;

import java.util.List;

/**
 * 注文商品情報をを表すドメインクラス.
 * 
 * @author igamasayuki
 *
 */
public class OrderItemForm {

	// /** id */
//	private Integer id;
	/** itemId */
	private Integer itemId;
//	/** orderId */
//	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	// 注文トッピングフォーム一覧
	private List<OrderToppingForm> orderToppingFormList;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public List<OrderToppingForm> getOrderToppingFormList() {
		return orderToppingFormList;
	}

	public void setOrderToppingFormList(List<OrderToppingForm> orderToppingFormList) {
		this.orderToppingFormList = orderToppingFormList;
	}

	@Override
	public String toString() {
		return "OrderItemForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size
				+ ", orderToppingFormList=" + orderToppingFormList + "]";
	}
//	/**
//	 * 小計金額を計算.
//	 *
//	 * @return int 小計金額
//	 */
//	public int getSubTotal() {
//		int totalPrice = 0;
//		if (this.size == 'M') {
//			totalPrice += this.item.getPriceM();
//			for (OrderTopping orderTopping : orderToppingList) {
//				totalPrice += orderTopping.getTopping().getPriceM();
//			}
//		} else if (this.size == 'L') {
//			totalPrice += this.item.getPriceL();
//			for (OrderTopping orderTopping : orderToppingList) {
//				totalPrice += orderTopping.getTopping().getPriceL();
//			}
//		}
//		return totalPrice * quantity;
//	}

}
