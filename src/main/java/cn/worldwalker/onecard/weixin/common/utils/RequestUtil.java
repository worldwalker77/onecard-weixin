package cn.worldwalker.onecard.weixin.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.worldwalker.onecard.weixin.domain.UserSession;

public class RequestUtil {
	
	private static ThreadLocal<HttpServletRequest> requsetThreadLocal = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();
	
	public static void setRequset(HttpServletRequest request){
		requsetThreadLocal.set(request);
	}
	
	public static void setResponse(HttpServletResponse response){
		responseThreadLocal.set(response);
	}
	
	public static HttpServletRequest getHttpServletRequest(){
		return requsetThreadLocal.get();
	}
	
	public static HttpServletResponse getHttpServletResponse(){
		return responseThreadLocal.get();
	}
	
	/**
	 * 获取请求id，每个请求都会有一个id与之对应，再跨多个系统的复杂应用时，可以很方便的定位问题
	 * @return
	 */
	public static String getRequestId(){
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletRequest request = getHttpServletRequest();
		String requestId = String.valueOf(request.getAttribute("requestId"));
		return requestId;
	}
	
	/**
	 * 设置用户登录session
	 * @param frontUser
	 */
	public static void setUserSession(UserSession userSession){
		HttpServletRequest request = getHttpServletRequest();
		HttpSession session = request.getSession();
		String userSessionStr = JsonUtil.toJson(userSession);
		session.setAttribute("userSession", userSessionStr);
	}
	
	public static void setOpenIdToSession(String openId){
		HttpServletRequest request = getHttpServletRequest();
		HttpSession session = request.getSession();
		session.setAttribute("openId", openId);
	}
	
	public static String getOpenIdFromSession(){
		HttpServletRequest request = getHttpServletRequest();
		HttpSession session = request.getSession();
		Object sessionObject = session.getAttribute("openId");
		if (null != sessionObject) {
			return (String)sessionObject;
		}
		sessionObject = session.getAttribute("userSession");
		UserSession userSession = null;
		if (null != sessionObject) {
			userSession = JsonUtil.toObject(String.valueOf(sessionObject), UserSession.class);
		}
		if (userSession != null) {
			return userSession.getOpenId();
		}
		return null;
	}
	
	public static String getIdNumFromSession(){
		HttpServletRequest request = getHttpServletRequest();
		HttpSession session = request.getSession();
		Object sessionObject = session.getAttribute("userSession");
		UserSession userSession = null;
		if (null != sessionObject) {
			userSession = JsonUtil.toObject(String.valueOf(sessionObject), UserSession.class);
		}
		if (userSession != null) {
			return userSession.getIdNum();
		}
		return null;
	}
	
	/**
	 * 获取用户session
	 * @return
	 */
	public static UserSession getUserSession(){
		HttpServletRequest request = getHttpServletRequest();
		HttpSession session = request.getSession();
		UserSession userSession = null;
		Object sessionObject = session.getAttribute("userSession");
		if (null != sessionObject) {
			userSession = JsonUtil.toObject(String.valueOf(sessionObject), UserSession.class);
		}
		return userSession;
	}
	
	/**
	 * 判断用户session是否存在
	 * @return
	 */
	public static boolean isUserSessionExist(){
		HttpServletRequest request = getHttpServletRequest();
		HttpSession session = request.getSession();
		Object sessionObject = session.getAttribute("userSession");
		if (null == sessionObject) {
			return false;
		}
		return true;
	}
	/**
	 * 添加cookie
	 * @param cookieName
	 * @param cookieValue
	 * @param maxAge
	 */
	public static void addCookie(String cookieName, String cookieValue, int maxAge){
		HttpServletResponse response = getHttpServletResponse();
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	
	/**
	 * 获取cookie值
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(String cookieName){
		HttpServletRequest request = getHttpServletRequest();
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if (cookieName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
}
