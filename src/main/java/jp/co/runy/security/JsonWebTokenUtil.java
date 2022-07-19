package jp.co.runy.security;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * JsonWetTokenでアクセス認証処理群をまとめたクラス.<br>
 * 
 * 参考サイト：https://b1san-blog.com/post/spring/spring-auth/
 * 
 * @author igamasayuki
 *
 */
public class JsonWebTokenUtil {

	/**
	 * 認証トークン=JWT（JSON Web Token）の生成.
	 * 
	 * @param id         ID
	 * @param key        暗号化に使用するキー(サーバー側のみが知る情報でかつ複雑な物)
	 * @return 認証トークン=JWT（JSON Web Token）
	 */
	public String generateToken(String id) {
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.plusHours(SecurityConstants.EXPIRATION_HOUR); // 有効期限は8時間に設定
		Date expirationDate = toDate(localDateTime); // 有効期限
		
		return Jwts.builder().setSubject(id).setExpiration(expirationDate)
				.signWith(Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8))).compact();
	}

	/**
	 * 認証トークン=JWT（JSON Web Token）の解析しIDを返します.<br>
	 * 
	 * @param token 認証トークン=JWT（JSON Web Token）
	 * @param key   暗号化に使用するキー(サーバー側のみが知る情報でかつ複雑な物)
	 * @return ID
	 */
	public String getIdFromToken(String token, String key) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8))).build()
				.parseClaimsJws(token).getBody().getSubject();
	}

	/**
	 * クライアントからのリクエストの情報から、認証トークンを取得して認可処理を行います.
	 * 
	 * @param request リクエスト情報
	 * @return 認可OK:true / 認可NG:false
	 */
	public boolean authorize(HttpServletRequest request) {
		// Authorizationの値を取得
		String authorization = request.getHeader("Authorization");
		if (authorization == null || authorization.isEmpty()) {
			return false;
		}
		// Bearer tokenの形式であることをチェック
		if (authorization.indexOf("Bearer ") != 0) {
			return false;
		}
		
		// Authorizationの最初に付加されている「Bearer 」を除去し、アクセストークンのみ取り出し
		String accessToken = authorization.substring(7);
		System.out.println("accessToken : " + accessToken);
		// トークンから管理者id(ログインした人のID)を取得
		String administratorId = getIdFromToken(accessToken, SecurityConstants.JWT_KEY);
		System.out.println("administratorId : " + administratorId);
		return true;
	}
	
    /*
     * LocalDateTimeからDateに変換.
     */
    private static Date toDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, zone);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

}
