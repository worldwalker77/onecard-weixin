package cn.worldwalker.onecard.weixin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.worldwalker.onecard.weixin.common.exception.BusinessException;
import cn.worldwalker.onecard.weixin.common.exception.ExceptionEnum;
import cn.worldwalker.onecard.weixin.domain.Result;
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
	public ModelAndView tip(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/tip");
		return mv;
	}
	
	
}
