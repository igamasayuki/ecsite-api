package jp.co.runy.form;

/**
 * 注文トッピング情報をを表すフォームクラス.
 * 
 * @author igamasayuki
 *
 */
public class OrderToppingForm {
	// /** id */
//	private Integer id;
	/** toppngId */
	private Integer toppingId;

	/** orderItemId */
	private Integer orderItemId;

	public Integer getToppingId() {
		return toppingId;
	}

	public void setToppingId(Integer toppingId) {
		this.toppingId = toppingId;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	@Override
	public String toString() {
		return "OrderToppingForm [orderItemId=" + orderItemId + "]";
	}

}
