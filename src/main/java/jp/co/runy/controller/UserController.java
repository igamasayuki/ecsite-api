package jp.co.runy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.runy.common.WebApiResponseObject;
import jp.co.runy.domain.User;
import jp.co.runy.form.LoginForm;
import jp.co.runy.form.RegisterUserForm;
import jp.co.runy.service.LoginService;
import jp.co.runy.service.RegisterUserService;

/**
 * ユーザー情報登録に関する制御を行うRESTコントローラー.
 *
 * @author igamasayuki
 *
 */
@RestController
@RequestMapping("/user")
//CrossOrigin対応(異なるサーバーからの呼び出しを許可)
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class UserController {

	@Autowired
	private RegisterUserService service;
	
	@Autowired
	private HttpSession session;

	@Autowired
	private LoginService loginService;

	/**
	 * ユーザーの新規登録をする.
	 *
	 * 動作確認用curlコマンド <br>
	 * 
	 * <pre>
	 * curl -X POST -H "Content-Type: application/json" -d '{"name":"テスト太郎","email":"test@gmail.com", "password":"TestTest123-", "checkPassword":"TestTest123-", "zipcode":"111-1111", "address":"東京都新宿区１−１−１", "telephone":"000-0000-0000"}' "http://localhost:8080/ecsite-api/user"
	 * </pre>
	 * 
	 * @param registerUserForm ユーザーを新規登録する際に利用されるフォーム
	 * @param result           入力値チェックのエラー群
	 * @return レスポンスメッセージオブジェクト
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public WebApiResponseObject registerUser(@RequestBody @Validated RegisterUserForm registerUserForm,
			BindingResult result) {
		System.out.println("RegisterUserForm：" + registerUserForm);
		WebApiResponseObject webApiResponseObject = new WebApiResponseObject();

//		// パスワード不一致チェック
//		if (!registerUserForm.getCheckPassword().equals("")) {
//			if (!registerUserForm.getPassword().equals(registerUserForm.getCheckPassword())) {
//				webApiResponseObject.setStatus("error");
//				webApiResponseObject.setMessage("パスワードと確認用パスワードが不一致です。");
//				webApiResponseObject.setErrorCode("E-01");
//				System.out.println("WebApiResponseMessage:" + webApiResponseObject);
//				return webApiResponseObject;
//			}
//		}
		// メールアドレス重複チェック
		if (service.checkExistEmail(registerUserForm.getEmail())) {
			webApiResponseObject.setStatus("error");
			webApiResponseObject.setMessage("そのメールアドレスはすでに使われています。");
			webApiResponseObject.setErrorCode("E-01");
			System.out.println("WebApiResponseMessage:" + webApiResponseObject);
			return webApiResponseObject;
		}
		// バリデートチェック
		if (result.hasErrors()) {
			webApiResponseObject.setStatus("error");
			webApiResponseObject.setMessage(result.toString());
			webApiResponseObject.setErrorCode("E-02");
			System.out.println("WebApiResponseMessage:" + webApiResponseObject);
			return webApiResponseObject;
		}

		User user = new User();
		BeanUtils.copyProperties(registerUserForm, user);

		service.registerUser(user);

		// 成功情報をレスポンス
		webApiResponseObject.setStatus("success");
		webApiResponseObject.setMessage("OK.");
		webApiResponseObject.setErrorCode("E-00");
		System.out.println(webApiResponseObject);
		return webApiResponseObject;
	}
	

	/**
	 * ログインをする.
	 *
	 * <pre>
	 * curl -X POST -H "Content-Type: application/json" -d '{"email":"test@gmail.com", "password":"TestTest123-"}' "http://localhost:8080/ecsite-api/user/login"
	 * </pre>
	 * 
	 * @return レスポンスメッセージオブジェクト
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public WebApiResponseObject login(@RequestBody LoginForm form, BindingResult result, Model model) {
		System.out.println("form:" + form);
		WebApiResponseObject webApiResponseObject = new WebApiResponseObject();
		Map<String,Object> responseMap = new HashMap<>();
		
		if("admin@xxx.com".equals(form.getEmail()) && "adminadmin".equals(form.getPassword())) {
			// 管理者ユーザーの情報なら管理者情報(isAdmin==trueの情報)を返す
			User user = new User();
			user.setId(999999999);
			user.setName("管理者ユーザー");
			user.setEmail("admin@xxx.com");
			user.setPassword("**********");
			user.setZipcode("2208136");
			user.setAddress("東京都新宿区1-1-1");
			user.setTelephone("090-0000-0000");
			user.setAdmin(true);
			// 成功情報をレスポンス
			webApiResponseObject.setStatus("success");
			webApiResponseObject.setMessage("OK.");
			webApiResponseObject.setErrorCode("E-00");
			responseMap.put("user", user);
			webApiResponseObject.setResponseMap(responseMap);
			System.out.println(webApiResponseObject);
			return webApiResponseObject;
		}

		User user = loginService.login(form.getEmail(), form.getPassword());

		if (user == null) {
			webApiResponseObject.setStatus("error");
			webApiResponseObject.setMessage("メールアドレス、またはパスワードが間違っています。");
			webApiResponseObject.setErrorCode("E-01");
			System.out.println("WebApiResponseMessage:" + webApiResponseObject);
			return webApiResponseObject;
		}
		
		user.setPassword("**********");
		session.setAttribute("user", user);

		// 成功情報をレスポンス
		webApiResponseObject.setStatus("success");
		webApiResponseObject.setMessage("OK.");
		webApiResponseObject.setErrorCode("E-00");
		responseMap.put("user", user);
		webApiResponseObject.setResponseMap(responseMap);
		System.out.println(webApiResponseObject);
		return webApiResponseObject;
	}

	/**
	 * ログアウトをします.
	 * 
	 * <pre>
	 * curl -X POST -H "Content-Type: application/json" "http://localhost:8080/ecsite-api/user/logout"
	 * </pre>
	 * 
	 * @return WebAPIのレスポンス情報
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public WebApiResponseObject logout() {
		session.invalidate();
		// 成功情報をレスポンス
		WebApiResponseObject webApiResponseObject = new WebApiResponseObject();
		webApiResponseObject.setStatus("success");
		webApiResponseObject.setMessage("OK.");
		webApiResponseObject.setErrorCode("E-00");
		System.out.println(webApiResponseObject);
		return webApiResponseObject;
	}
}
