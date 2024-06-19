package jp.co.runy.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * ユーザーのログイン情報を格納するエンティティ.
 * 
 * @author igamasayuki
 *
 */
public class LoginUser  extends org.springframework.security.core.userdetails.User{

	private static final long serialVersionUID = -582708602920437011L;
	
	/** ユーザー情報 */
	private final User user;
	
	/**
	 * 通常のユーザー情報に加え、認可用ロールを設定する.
	 * 
	 * @param user ユーザー情報
	 * @param authorityList 権限情報が入ったリスト
	 */
	public LoginUser(User user, Collection<GrantedAuthority> authorityList) {
		super(user.getEmail(), user.getPassword(), authorityList);
		this.user = user;
	}

	/**
	 * ユーザー情報を返します.
	 * 
	 * @return ユーザー情報
	 */
	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "LoginUser [user=" + user + "]";
	}
	
	
	
}
