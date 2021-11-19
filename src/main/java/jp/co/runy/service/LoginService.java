package jp.co.runy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.runy.domain.User;
import jp.co.runy.repository.UserRepository;

/**
 * ログイン業務処理を行うサービスクラス.
 *
 * @author shigeki.morishita
 *
 */
@Service
@Transactional
public class LoginService {
	@Autowired
	private UserRepository userRepository;

	/**
	 * ログインの業務処理を行う.
	 *
	 * @param email    メールアドレス
	 * @param passward パスワード
	 * @return ログイン者情報 (一件もヒットしなければnullを返す)
	 */
	public User login(String email, String passward) {
		User userInfo = userRepository.findByMailAddressAndPassword(email, passward);
		if (userInfo == null) {
			return null;
		}

		return userInfo;
	}
}
