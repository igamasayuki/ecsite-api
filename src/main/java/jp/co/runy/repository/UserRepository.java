package jp.co.runy.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.runy.domain.User;

/**
 * ユーザーテーブルを操作するリポジトリクラス.
 *
 * @author igamasayuki
 *
 */
@Repository
public class UserRepository {

	/** ユーザーの情報をもつテーブル名 */
	private static final String TABLE_USERS = "users";

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

	/**
	 * ユーザーをDBに新規登録する.
	 *
	 * @param user 登録したいユーザーのドメイン
	 */
	synchronized public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO " + TABLE_USERS
				+ " (name,email,password,zipcode,address,telephone) VALUES(:name,:email,:password,:zipcode,:address,:telephone);";
		template.update(sql, param);
	}

	/**
	 * メールアドレスとパスワードからユーザーを取得する.
	 *
	 * @param email    取得したいユーザーのメールアドレス
	 * @return ユーザーが存在すれば1つだけユーザードメインの入ったリスト、なければnull
	 */
	public User findByMailAddress(String email) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone FROM " + TABLE_USERS
				+ " WHERE email=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
	
	/**
	 * メールアドレスとパスワードからユーザーを取得する.
	 *
	 * @param email    取得したいユーザーのメールアドレス
	 * @param password 取得したいユーザーのパスワード
	 * @return ユーザーが存在すれば1つだけユーザードメインの入ったリスト、なければnull
	 */
	public User findByMailAddressAndPassword(String email, String password) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone FROM " + TABLE_USERS
				+ " WHERE email=:email AND password=:password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

}
