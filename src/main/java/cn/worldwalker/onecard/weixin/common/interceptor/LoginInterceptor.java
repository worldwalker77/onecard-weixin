package cn.worldwalker.onecard.weixin.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.worldwalker.onecard.weixin.common.utils.RequestUtil;
import cn.worldwalker.onecard.weixin.domain.UserSession;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;
import cn.worldwalker.onecard.weixin.service.OneCardService;

public class LoginInterceptor  extends HandlerInterceptorAdapter {
	
	@Autowired
	private OneCardService oneCardService;
	
	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        /**如果session不存在*/
		if (!RequestUtil.isUserSessionExist()) {
			String code = httpServletRequest.getParameter("code");
			/**如果授权code也不存在，则说明是进入页面之后长时间没有操作，导致session失效，需要提示用户退出重新打开微信菜单，进行页面授权*/
			if (StringUtils.isBlank(code)) {
				httpServletResponse.sendRedirect("/bind/reentrytip");
				return false;
			}
			/***如果授权code存在，则说明是打开微信菜单进行页面授权的请求*/
			String openId = oneCardService.getOpenIdFromWeiXin(code);
			/**如果拿不到微信信息，则重新进菜单进行页面授权**/
			if (StringUtils.isBlank(openId)) {
				httpServletResponse.sendRedirect("/bind/reentrytip");
				return false;
			}
			/**根据openId去数据表查询是否绑定,如果没有绑定则跳转绑定页面*/
			WxBindModel wxModel = oneCardService.selectWxBind(null, openId);
			if (wxModel == null) {
				String uri = httpServletRequest.getRequestURI();
				/**将openId放入httpsession中，绑定身份证的时候会从session里面取值*/
				RequestUtil.setOpenIdToSession(openId);
				httpServletResponse.sendRedirect("/bind/index?redirectUrl=" + uri);
				return false;
			}
			/**如果已经绑定，则设置httpsession*/
			UserSession userSession = new UserSession();
			userSession.setIdNum(wxModel.getIdNum());
			userSession.setMobilePhone(wxModel.getMobilePhone());
			userSession.setOpenId(openId);
			RequestUtil.setUserSession(userSession);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
	
	}

	@Override
	public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handle, Exception e) {

	}
}
