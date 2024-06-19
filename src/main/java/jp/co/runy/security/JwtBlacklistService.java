package jp.co.runy.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * JWTトークンのブラックリストを管理するサービスです.
 * このサービスでは、トークンをブラックリストに追加し、ブラックリストに含まれているかどうかをチェックし、
 * 定期的にブラックリストから有効期限が切れたトークンをクリーンアップします。
 * 
 * @author igamasayuki
 */
@Service
public class JwtBlacklistService {
	private Map<String, Date> blacklist = new HashMap<>();

	/**
	 * JWTトークンをブラックリストに追加します。トークンの有効期限も一緒に保存されます.
	 *
	 * @param token          ブラックリストに追加するJWTトークン
	 * @param expirationDate トークンの有効期限
	 */
	public void addToBlacklist(String token, Date expirationDate) {
		blacklist.put(token, expirationDate);
	}

	/**
	 * JWTトークンがブラックリストに含まれているかどうかをチェックします.
	 *
	 * @param token チェックするJWTトークン
	 * @return トークンがブラックリストに含まれている場合はtrue、そうでない場合はfalse
	 */
	public boolean isBlacklisted(String token) {
		return blacklist.containsKey(token);
	}

	/**
	 * 定期的にブラックリストから有効期限が切れたトークンをクリーンアップします.
	 * このメソッドは、Springの@Scheduledアノテーションによって定期的に実行されます。
	 */
	@Scheduled(fixedRate = 3600000) // 1時間ごとに実行
	public void cleanUpBlacklist() {
		Date now = new Date();
		Iterator<Map.Entry<String, Date>> iterator = blacklist.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Date> entry = iterator.next();
			if (entry.getValue().before(now)) {
				iterator.remove();
			}
		}
	}
}
