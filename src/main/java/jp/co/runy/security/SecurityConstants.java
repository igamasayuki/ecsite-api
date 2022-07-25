package jp.co.runy.security;

import java.util.UUID;

public class SecurityConstants {
	// 有効期限
    public static final int EXPIRATION_HOUR = 8; // 8hours
    // リクエストヘッダ内にあるトークンの最初につけられる文字列
    public static final String TOKEN_PREFIX = "Bearer ";
    // 暗号化に使用するキー(サーバー側のみが知る情報でかつ複雑な物)
    public static final String JWT_KEY = UUID.randomUUID().toString();
}