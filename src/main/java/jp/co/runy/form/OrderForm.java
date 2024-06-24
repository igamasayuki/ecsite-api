package jp.co.runy.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



/**
 * 注文時の各種情報を取得する際に使用するフォーム.
 * 
 * @author igamasayuki
 */
public class OrderForm {

//	/** 注文ID */
//	private String orderId;

	// ユーザID
	private Integer userId;

	// ステータス
	private Integer status;

	// 合計金額
	private Integer totalPrice;

//	// 注文日
//	private String orderDate;

	/** 送り先氏名 */
	@NotBlank(message = "名前を入力して下さい")
	private String destinationName;

	/** 送り先メールアドレス */
	@NotBlank(message = "メールアドレスを入力して下さい")
	@Email(message = "メールアドレスの形式が不正です")
	private String destinationEmail;

	/** 送り先郵便番号 */
	@NotBlank(message = "郵便番号を入力して下さい")
//	@Pattern(regexp = "^([0-9]{3}-[0-9]{4})?$", message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String destinationZipcode;

	/** 送り先住所 */
	@NotBlank(message = "住所を入力して下さい")
	private String destinationAddress;

	/** 送り先電話番号 */
	@NotBlank(message = "電話番号を入力して下さい")
//	@Pattern(regexp = "^(\\d{1,4}-\\d{1,4}-\\d{1,4})?$", message = "電話番号はXXXX-XXXX-XXXXの形式で入力してください")
	private String destinationTel;

//	/** 配達希望日程 */
//	@NotBlank(message = "配達日を選択してください")
//	private String deliveryDate;

	/** 配達希望日時 */
	@NotBlank(message = "配達希望日時を入力してください")
	private String deliveryTime;

	/** 支払方法 */
	private Integer paymentMethod;

	private List<OrderItemForm> orderItemFormList;

	/**
	 * 配達希望日時をTimestampに変換して返します.
	 * 
	 * @return Timestampに変換された配達希望日時
	 */
	public Timestamp getTimeStampDeliveryTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			Date date = sdf.parse(deliveryTime);
			Timestamp timestamp = new Timestamp(date.getTime());
			return timestamp;
		} catch (Exception e) {
			throw new RuntimeException("配達希望日時はyyyy/MM/dd HH:mm:ss形式にしてください。");
		}
	}

//	private String cardNumber;
//	private String cardExpMonth;
//	private String cardExpYear;
//	private String cardName;
//	private String cardCvv;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

//	public String getDeliveryDate() {
//		return deliveryDate;
//	}
//
//	public void setDeliveryDate(String deliveryDate) {
//		this.deliveryDate = deliveryDate;
//	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<OrderItemForm> getOrderItemFormList() {
		return orderItemFormList;
	}

	public void setOrderItemFormList(List<OrderItemForm> orderItemFormList) {
		this.orderItemFormList = orderItemFormList;
	}

	@Override
	public String toString() {
		return "OrderForm [userId=" + userId + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", deliveryTime=" + deliveryTime + ", paymentMethod="
				+ paymentMethod + ", orderItemFormList=" + orderItemFormList + "]";
	}

}
