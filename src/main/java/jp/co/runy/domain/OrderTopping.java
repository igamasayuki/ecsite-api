package jp.co.runy.domain;

/**
 * 注文トッピング情報をを表すドメインクラス.
 * 
 * @author igamasayuki
 *
 */
public class OrderTopping {
	/** id */
	private Integer id;
	/** toppngId */
	private Integer toppingId;
	/** orderItemId */
	private Integer orderItemId;
	/** トッピング */
	private Topping topping;

	@Override
	public String toString() {
		return "OrderTopping [id=" + id + ", toppngId=" + toppingId + ", orderItemId=" + orderItemId + ", topping="
				+ topping + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getToppingId() {
		return toppingId;
	}

	public void setToppingId(Integer toppngId) {
		this.toppingId = toppngId;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Topping getTopping() {
		return topping;
	}

	public void setTopping(Topping topping) {
		this.topping = topping;
	}

}
