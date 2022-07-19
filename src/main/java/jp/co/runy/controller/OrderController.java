package jp.co.runy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.runy.common.WebApiResponseMessage;
import jp.co.runy.domain.Order;
import jp.co.runy.form.OrderForm;
import jp.co.runy.service.OrderService;

/**
 * 注文を行うコントローラ.
 * 
 * @author igamasayuki
 */
@RestController
@RequestMapping("/order")
//CrossOrigin対応(異なるサーバーからの呼び出しを許可)
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class OrderController {

	@Autowired
	private OrderService orderService;

	/**
	 * 注文一覧情報を取得します.
	 *
	 * <pre>
	 * curl -X GET -H "Content-Type: application/json" "http://localhost:8080/ecsite-api/order/orders/ahloha/1111"
	 * </pre>
	 * 
	 * @return 全件検索結果
	 */
	@RequestMapping(value = "/orders/{type}/{userId}", method = RequestMethod.GET)
	public Map<String, Object> orders(@PathVariable("type") String type, @PathVariable("userId") String userId) {

		List<Order> orders = orderService.getAllFullOrderByType(type, Integer.parseInt(userId));

		Map<String, Object> ordersMap = new HashMap<>();
		ordersMap.put("totalOrderCount", orders.size());
		ordersMap.put("orders", orders);

		System.out.println("orders : " + ordersMap);
		return ordersMap;
	}

	/**
	 * 注文情報を新規登録する.
	 * 
	 * @param orderForm 注文情報の入ったフォーム
	 * @param result    エラー処理に利用
	 * @return レスポンスメッセージオブジェクト
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public WebApiResponseMessage order(@RequestBody @Validated OrderForm orderForm, BindingResult result) {
		System.out.println("OrderForm:" + orderForm);

		WebApiResponseMessage webApiResponseMessageDomain = new WebApiResponseMessage();

		try {
			orderService.order(orderForm);
			// 成功情報をレスポンス
			webApiResponseMessageDomain.setStatus("success");
			webApiResponseMessageDomain.setMessage("OK.");
			webApiResponseMessageDomain.setError_code("E-00");
			System.out.println(webApiResponseMessageDomain);
			return webApiResponseMessageDomain;
		} catch (Exception e) {
			e.printStackTrace();
			webApiResponseMessageDomain.setStatus("error");
			webApiResponseMessageDomain.setMessage(e.getMessage());
			webApiResponseMessageDomain.setError_code("E-01");
			System.out.println("WebApiResponseMessage:" + webApiResponseMessageDomain);
			return webApiResponseMessageDomain;
		}
	}

}
