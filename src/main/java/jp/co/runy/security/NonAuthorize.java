package jp.co.runy.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 認可を行わないコントローラのメソッドにつけるアノテーション.<br>
 * 
 * メソッドにつけることができます。<br>
 * 
 * 参考サイト：https://b1san-blog.com/post/spring/spring-auth/
 * 
 * @author igamasayuki
 *
 */
//@NonAuthorize
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonAuthorize {
}