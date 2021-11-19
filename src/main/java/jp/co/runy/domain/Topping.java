package jp.co.runy.domain;

/**
 * トッピング情報をを表すドメインクラス.
 * 
 * @author igamasayuki
 *
 */
public class Topping {
	/** id */
	private Integer id;
	/** 商品タイプ */
	private String type;
	/** トッピング名 */
	private String name;
	/** Mサイズ値段 */
	private Integer priceM;
	/** Lサイズ値段 */
	private Integer priceL;

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

	@Override
	public String toString() {
		return "Topping [id=" + id + ", name=" + name + ", priceM=" + priceM + ", priceL=" + priceL + "]";
	}

}
