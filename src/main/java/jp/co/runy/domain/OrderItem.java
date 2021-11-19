package jp.co.runy.domain;

import java.util.List;

/**
 * 注文商品情報をを表すドメインクラス.
 * 
 * @author igamasayuki
 *
 */
public class OrderItem {
	/** id */
	private Integer id;
	/** itemId */
	private Integer itemId;
	/** orderId */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** 商品 */
	private Item item;
	/** トッピングリスト */
	private List<OrderTopping> orderToppingList;

	/**
	 * 小計金額を計算.
	 *
	 * @return int 小計金額
	 */
	public int getSubTotal() {
		int totalPrice = 0;
		if (this.size == 'M') {
			totalPrice += this.item.getPriceM();
			for (OrderTopping orderTopping : orderToppingList) {
				totalPrice += orderTopping.getTopping().getPriceM();
			}
		} else if (this.size == 'L') {
			totalPrice += this.item.getPriceL();
			for (OrderTopping orderTopping : orderToppingList) {
				totalPrice += orderTopping.getTopping().getPriceL();
			}
		}
		return totalPrice * quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}

}
