package jp.co.runy.security;

import java.util.UUID;

public class SecurityConstants {
	// 有効期限
    public static final int EXPIRATION_HOUR = 8; // 8hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    // 暗号化に使用するキー(サーバー側のみが知る情報でかつ複雑な物)
    public static final String JWT_KEY = UUID.randomUUID().toString();
}