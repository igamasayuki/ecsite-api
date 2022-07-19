package jp.co.runy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ログイン認証用設定.
 * 
 * @author igamasayuki
 *
 */
@Configuration // 設定用のクラス
public class SecurityConfig implements WebMvcConfigurer {

	/**
	 * このメソッドをオーバーライドすることで、 特定のリクエストに対して「セキュリティ設定」を 無視する設定など全体にかかわる設定ができる.
	 * 具体的には静的リソースに対してセキュリティの設定を無効にする。
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.WebSecurity)
	 */
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/fonts/**");
	}

	/**
	 * このメソッドをオーバーライドすることで、認可の設定やログイン/ログアウトに関する設定ができる.
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//		http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
//		http.csrf() 
//			.ignoringAntMatchers("/login","/insert"); // ここで登録されたパスはCSRFトークンを不要にする
////			.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // cookieにCSRFトークンを入れる

		// CSRFに関する設定 今回はJsonWebToken使用のためCSRFトークンを不要にする
		http.csrf() 
				.disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		return http.build();
	}

	/**
	 * 認可用インターセプターをDIコンテナに登録
	 * 
	 * @return 認可用インターセプター
	 */
	@Bean
	AuthorizationHandlerInterceptor authorizationHandlerInterceptor() {
		return new AuthorizationHandlerInterceptor();
	}

	/**
	 *インターセプターの登録
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 全てのパスに対してインターセプターを使うように設定
		registry.addInterceptor(authorizationHandlerInterceptor()).addPathPatterns("/**");
		
		// /employeeが含まれるURLは認可が必要
//		registry.addInterceptor(authorizationHandlerInterceptor()).addPathPatterns("/employee/**");
	}

	/**
	 * <pre>
	 * bcryptアルゴリズムでハッシュ化する実装を返します.
	 * これを指定することでパスワードハッシュ化やマッチ確認する際に
	 * &#64;Autowired
	 * private PasswordEncoder passwordEncoder;
	 * と記載するとDIされるようになります。
	 * </pre>
	 * 
	 * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
