package jp.co.runy.security;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 認可を行うインターセプター(フィルターのようなもの).<br>
 * 
 * コントローラの前に呼ぶようにSecurityConfigクラスにて設定済
 * 
 * @author igamasayuki
 *
 */
public class AuthorizationHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		// キャスト判定
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// 実行されるメソッドを取得
		Method method = ((HandlerMethod) handler).getMethod();
		// @NonAuthorizeが付与されているか確認
		if (AnnotationUtils.findAnnotation(method, NonAuthorize.class) != null) {
			// 付与されている場合は認可せずに終了
			System.out.println("認可しない");
			return true;
		}

		// メソッドに対応するControllerを取得
		Class<?> controller = method.getDeclaringClass();
		// Controllerまたはメソッドに@Authorizeが付与されているか確認
		if (AnnotationUtils.findAnnotation(controller, Authorize.class) != null
				|| AnnotationUtils.findAnnotation(method, Authorize.class) != null) {
			// 付与されている場合は認可処理を実行
			JsonWebTokenUtil jsonWebTokenUtil = new JsonWebTokenUtil();
			boolean isAuthorizaOK = jsonWebTokenUtil.authorize(request, response);
			System.out.println("認可する：" + isAuthorizaOK);

			if (!isAuthorizaOK) {
				// 有効期限切れなどの認可が許されない場合はException
				throw new ResponseStatusException(HttpStatus.FORBIDDEN);
			}
		}

		return true;

	}
}