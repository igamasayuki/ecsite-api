package jp.co.runy.form;

/**
 * ログイン情報を受けとるフォームクラス.
 *
 * @author igamasayuki
 *
 */
public class LoginForm {
	/** email */
	private String email;
	/** password */
	private String password;

	@Override
	public String toString() {
		return "LoginForm [email=" + email + ", password=" + password + "]";
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
}
