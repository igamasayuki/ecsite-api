package jp.co.runy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.runy.domain.Item;
import jp.co.runy.domain.Topping;
import jp.co.runy.service.ItemService;

/**
 * 商品一覧表示を操作するリポジトリクラス.
 * 
 * @author igamasayuki
 *
 */
@RestController
@RequestMapping("/item")
//CrossOrigin対応(異なるサーバーからの呼び出しを許可)
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class ItemController {
	@Autowired
	private ItemService itemService;

	/**
	 * 商品一覧情報を取得します.
	 *
	 * <pre>
	 * curl -X GET -H "Content-Type: application/json" "http://localhost:8080/ecsite-api/item/items/pizza"
	 * </pre>
	 * @param type  商品タイプ (例：aloha=ハワイアン料理、pizza=ピザ)
	 * @return 全件検索結果
	 */
	@RequestMapping(value = "/items/{type}", method = RequestMethod.GET)
	public Map<String, Object> items(@PathVariable("type") String type) {

		List<Item> items = itemService.getItemList(type);

		Map<String, Object> itemsMap = new HashMap<>();
		itemsMap.put("totalItemCount", items.size());
		itemsMap.put("items", items);

		System.out.println("items : " + itemsMap);
		return itemsMap;
	}
	
	/**
	 * トッピングリストを含めた商品情報を１件取得します.
	 *
	 * <pre>
	 * curl -X GET -H "Content-Type: application/json" "http://localhost:8080/ecsite-api/item/1"
	 * </pre>
	 * @param type  商品タイプ (例：aloha=ハワイアン料理、pizza=ピザ)
	 * @return 全件検索結果
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Map<String, Object> toppings(@PathVariable("id") int id) {
		
		Item item = itemService.getItem(id);
		
		List<Topping> toppings = itemService.getToppingList(item.getType());
		
		item.setToppingList(toppings);

		Map<String, Object> itemMap = new HashMap<>();
		itemMap.put("itemToppingCount", toppings.size());
		itemMap.put("item", item);

		System.out.println("item : " + itemMap);
		return itemMap;
	}
	
	
	/**
	 * トッピング一覧情報を取得します.
	 *
	 * <pre>
	 * curl -X GET -H "Content-Type: application/json" "http://localhost:8080/ecsite-api/item/toppings/pizza"
	 * </pre>
	 * @param type  商品タイプ (例：aloha=ハワイアン料理、pizza=ピザ)
	 * @return 全件検索結果
	 */
	@RequestMapping(value = "/toppings/{type}", method = RequestMethod.GET)
	public Map<String, Object> toppings(@PathVariable("type") String type) {

		List<Topping> toppings = itemService.getToppingList(type);

		Map<String, Object> toppingsMap = new HashMap<>();
		toppingsMap.put("totalToppingCount", toppings.size());
		toppingsMap.put("toppings", toppings);

		System.out.println("toppings : " + toppingsMap);
		return toppingsMap;
	}

}
