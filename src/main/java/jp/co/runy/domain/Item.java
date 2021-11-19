package jp.co.runy.domain;

import java.util.List;

/**
 * 商品情報を表すドメインクラス.
 * 
 * @author igamasayuki
 *
 */
public class Item {
	/** id */
	private Integer id;
	/** 商品タイプ */
	private String type;
	/** 商品名 */
	private String name;
	/** 商品説明 */
	private String description;
	/** Mサイズの値段 */
	private Integer priceM;
	/** Lサイズ値段 */
	private Integer priceL;
	/** 画像パス */
	private String imagePath;
	/** 消去フラグ */
	private Boolean deleted;
	/** トッピングリスト */
	private List<Topping> toppingList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriceM() {
		return priceM;
	}

	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}

	public Integer getPriceL() {
		return priceL;
	}

	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public List<Topping> getToppingList() {
		return toppingList;
	}

	public void setToppingList(List<Topping> toppingList) {
		this.toppingList = toppingList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", imagePath=" + imagePath + ", deleted=" + deleted + ", toppingList=" + toppingList + "]";
	}

}
