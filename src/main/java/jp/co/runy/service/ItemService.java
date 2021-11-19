package jp.co.runy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.runy.domain.Item;
import jp.co.runy.domain.Topping;
import jp.co.runy.repository.ItemRepository;
import jp.co.runy.repository.ToppingRepository;


/**
 * 商品一覧の業務処理を行うサービスクラス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class ItemService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ToppingRepository toppingRepository;

	/**
	 * タイプごとの商品情報一覧を取得する.
	 * 
	 * @param type 商品タイプ
	 * @return List<Item> 商品一覧
	 */
	public List<Item> getItemList(String type) {
		return itemRepository.findByType(type);
	}
	
	/**
	 * 商品情報を主キー検索する.
	 * 
	 * @param type 商品タイプ
	 * @return List<Item> 商品一覧
	 */
	public Item getItem(int id) {
		return itemRepository.load(id);
	}
	
	/**
	 * タイプごとのトッピング情報を表示する.
	 * 
	 * @param type 商品タイプ
	 * @return List<Topping> トッピング一覧
	 */
	public List<Topping> getToppingList(String type) {
		return toppingRepository.findByType(type);
	}

}
