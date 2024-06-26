package jp.co.runy.form;


import jakarta.validation.constraints.NotBlank;

/**
 * ユーザー新規登録の際に利用されるフォーム.
 *
 * @author igamasayuki
 *
 */
public class RegisterUserForm {

	/** ユーザー名 */
	@NotBlank(message = "名前を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
//	@Email(message = "メールアドレスの形式が不正です")
	private String email;
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
//	@Pattern(regexp = "^((?=.*[A-Z])(?=.*[.?/-])[a-zA-Z0-9.?/-]{8,16})?$", message = "パスワードは大文字、記号[.?/-]を1種類ずつ以上含んだ8文字以上16文字以下で設定してください")
	private String password;
//	/** 確認用パスワード */
//	@NotBlank(message = "確認用パスワードを入力してください")
//	private String checkPassword;
	/** 郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
//	@Pattern(regexp = "^([0-9]{3}-[0-9]{4})?$", message = "郵便番号はXXX-XXXXの形式で入力してください")
	private String zipcode;
	/** 住所 */
	@NotBlank(message = "住所を入力してください")
	private String address;
	/** 電話番号 */
	@NotBlank(message = "電話番号を入力してください")
//	@Pattern(regexp = "^([0-9]{3,4}-[0-9]{4}-[0-9]{4})?$", message = "電話番号はXXXX-XXXX-XXXXの形式で入力してください")
	private String telephone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	public String getCheckPassword() {
//		return checkPassword;
//	}
//
//	public void setCheckPassword(String checkPassword) {
//		this.checkPassword = checkPassword;
//	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "RegisterUserForm [name=" + name + ", email=" + email + ", password=" + password + ", zipcode=" + zipcode
				+ ", address=" + address + ", telephone=" + telephone + "]";
	}

}
