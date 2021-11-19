package jp.co.runy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.runy.domain.User;
import jp.co.runy.repository.UserRepository;

/**
 * ユーザー登録処理をするサービスクラス.
 *
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	private UserRepository repository;

	/**
	 * メールアドレスが既にDBに存在しているかを調べる.
	 *
	 * @param email 調べたいメールアドレス
	 * @return 存在していればtrue,存在していなければfalse
	 */
	public boolean checkExistEmail(String email) {
		if (repository.findByMailAddress(email) == null) {
			return false;
		}
		return true;
	}

	/**
	 * ユーザーを新規登録する.
	 *
	 * @param user 登録したいユーザーのドメイン
	 */
	public void registerUser(User user) {
		repository.insert(user);
	}

}
