package jp.co.runy.common;

import java.util.Map;

/**
 * WebAPIのレスポンス情報を扱うクラス.
 * 
 * @author igamasayuki
 *
 */
public class WebApiResponseObject {

	/** ステータス情報*/
	private String status;
	/** レスポンスメッセージ*/
	private String message;
	/** エラーコード*/
	private String errorCode;
	/** レスポンスデータ(必要な場合のみ) */
	private Map<String, Object> responseMap;

	@Override
	public String toString() {
		return "WebApiResponseObject [status=" + status + ", message=" + message + ", errorCode=" + errorCode
				+ ", responseMap=" + responseMap + "]";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Map<String, Object> getResponseMap() {
		return responseMap;
	}

	public void setResponseMap(Map<String, Object> responseMap) {
		this.responseMap = responseMap;
	}
	

}
