package cn.worldwalker.onecard.weixin.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.worldwalker.onecard.weixin.common.exception.BusinessException;
import cn.worldwalker.onecard.weixin.common.exception.ExceptionEnum;
import cn.worldwalker.onecard.weixin.common.utils.RequestUtil;
import cn.worldwalker.onecard.weixin.domain.ContactInfoModel;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.UserSession;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;
import cn.worldwalker.onecard.weixin.service.OneCardService;

@Controller
public class BindController {
	
	protected static final Logger log = LoggerFactory.getLogger(BindController.class);
	@Autowired
	private OneCardService oneCardService;
	
	@RequestMapping(value="/bind/index")
	public ModelAndView bind(String redirectUrl){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/bind");
		mv.addObject("redirectUrl", redirectUrl);
		return mv;
	}
	
	@RequestMapping("bind/doBind")
	@ResponseBody
	public Result doBind(String idNum, String mobilePhone){
		Result result = null;
		try {
			result = oneCardService.doBind(idNum, mobilePhone);
		} catch (BusinessException e) {
			log.error(e.desc + ",idNum:" + idNum  + ",mobilePhone:" + mobilePhone);
			result = new Result();
			result.setCode(e.code);
			result.setDesc(e.desc);
		} catch (Exception e) {
			log.error(ExceptionEnum.SYSTEM_ERROR.desc + ",idNum:" + idNum  + ",mobilePhone:" + mobilePhone, e);
			result = new Result();
			result.setCode(ExceptionEnum.SYSTEM_ERROR.code);
			result.setDesc(ExceptionEnum.SYSTEM_ERROR.desc);
		}
		return result;
		
	}
	
	@RequestMapping(value="/bind/reentrytip")
	public ModelAndView reentrytip(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/tip");
		return mv;
	}
	
	@RequestMapping(value="/bind/successTip")
	public ModelAndView successTipe(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/bind_success");
		return mv;
	}
	
	/**
	 * 通过菜单绑定身份证
	 */
	@RequestMapping(value="/bind/indexMenu")
	public ModelAndView bindMenu(String code){
		ModelAndView mv = new ModelAndView();
		/**如果session存在，则说明是登录后点击绑定菜单的，直接提示已经绑定*/
		if (RequestUtil.isUserSessionExist()) {
			mv.setViewName("wechat/has_bind");
			return mv;
		}
		/**如果code为空，并且没有usersession，则可能是刷数据的恶意请求*/
		if (StringUtils.isBlank(code)) {
			mv.setViewName("/wechat/tip");
			return mv;
		}
		/***如果授权code存在，则说明是打开微信菜单进行页面授权的请求,根据code从微信获取信息*/
		String openId = oneCardService.getOpenIdFromWeiXin(code);
		/**如果拿不到微信信息，则重新进菜单进行页面授权**/
		if (StringUtils.isBlank(openId)) {
			mv.setViewName("/wechat/tip");
			return mv;
		}
		
		/**根据openId去数据表查询是否绑定,如果已经绑定则设置httpSession,并返回提示已经绑定*/
		WxBindModel wxModel = oneCardService.selectWxBind(null, openId);
		if (wxModel != null) {
			/**如果已经绑定，则设置httpsession*/
			UserSession userSession = new UserSession();
			userSession.setIdNum(wxModel.getIdNum());
			userSession.setOpenId(openId);
			/**从contactInfo表里面根据身份证号获取其他的信息，放入session中，方便后面的功能使用*/
			ContactInfoModel cim = oneCardService.getContactInfoByIdNum(wxModel.getIdNum());
			if (cim != null) {
				userSession.setMobilePhone(cim.getTel());
				userSession.setfName(cim.getfName());
				userSession.setEnName(cim.getEnName());
				userSession.setGrName(cim.getGrName());
			}
			RequestUtil.setUserSession(userSession);
			mv.setViewName("/wechat/has_bind");
			return mv;
		}
		/**将openId放入httpsession中，绑定身份证的时候会从session里面取值*/
		RequestUtil.setOpenIdToSession(openId);
		mv.setViewName("wechat/bind");
		mv.addObject("redirectUrl", "/bind/successTip");
		return mv;
		
	}
	
	
}
