package jp.co.runy.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.runy.domain.Order;
import jp.co.runy.domain.OrderItem;
import jp.co.runy.domain.OrderTopping;
import jp.co.runy.form.OrderForm;
import jp.co.runy.form.OrderItemForm;
import jp.co.runy.form.OrderToppingForm;
import jp.co.runy.repository.OrderItemRepository;
import jp.co.runy.repository.OrderRepository;
import jp.co.runy.repository.OrderToppingRepository;

/**
 * 注文に携わる機能を処理する.
 *
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;

	/**
	 * 注文一覧を検索する.
	 * 
	 * @param type   商品タイプ
	 * @param userId ユーザID
	 * @return 注文一覧
	 */
	public List<Order> getAllFullOrderByType(String type, int userId) {
		return orderRepository.findAllFullOrderByType(type, userId);
	}

	/**
	 * 注文をする.
	 *
	 * @param order 注文情報
	 */
	public void order(OrderForm orderForm) {
		Order order = new Order();
		BeanUtils.copyProperties(orderForm, order);
		// 注文日を今の時刻にする
		order.setOrderDate(new Date(new java.util.Date().getTime()));
		// 配達希望日時をStringからTimestampへ変換
		order.setDeliveryTime(orderForm.getTimeStampDeliveryTime());

		Order newOrder = orderRepository.insert(order);
		System.out.println("newOrder:" + newOrder);
		if (orderForm.getOrderItemFormList() == null) {
			throw new RuntimeException("注文情報の中に注文商品一覧が存在しません");
		}
		for (OrderItemForm orderItemForm : orderForm.getOrderItemFormList()) {
			OrderItem orderItem = new OrderItem();
			BeanUtils.copyProperties(orderItemForm, orderItem);
			orderItem.setOrderId(newOrder.getId());

			OrderItem newOrderItem = orderItemRepository.insert(orderItem);

			// トッピングがなければ次の商品の処理へ移る
			if (orderItemForm.getOrderToppingFormList() == null) {
				continue;
			}

			for (OrderToppingForm orderToppingForm : orderItemForm.getOrderToppingFormList()) {
				OrderTopping orderTopping = new OrderTopping();
				BeanUtils.copyProperties(orderToppingForm, orderTopping);
				orderTopping.setOrderItemId(newOrderItem.getId());

				orderToppingRepository.insert(orderTopping);
			}

		}
	}

//
//	public CheckCreditCard checkCredit(Map<String, String> map) throws Exception {
//
//		URI url = new URI("http://153.127.48.168:8080/sample-credit-card-web-api/credit-card/payment");
//		CheckCreditCard checkCreditCard = restTemplate.postForObject(url, map, CheckCreditCard.class);
//		return checkCreditCard;
//	}
}
