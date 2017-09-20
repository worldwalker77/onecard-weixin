package cn.worldwalker.onecard.weixin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.worldwalker.onecard.weixin.common.exception.ExceptionEnum;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.service.OneCardService;

@Controller
@RequestMapping(value="/rule")
public class PolicyController {
	
	protected static final Logger log = LoggerFactory.getLogger(PolicyController.class);
	
	@Autowired
	private OneCardService oneCardService;
	
	/**
	 * 政策法规展示页
	 * @return
	 */
	@RequestMapping(value="/policy")
	public ModelAndView news(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/policy");
		return mv;
	}
	/**
	 * 获取政策法规列表
	 * @param pageSize
	 * @param pageNum
	 * @param title
	 * @return
	 */
	@RequestMapping(value="/queryPolicyList")
	@ResponseBody
	public Result queryPolicyList(Integer pageSize, Integer pageNum, String title){
		Result result = null;
		try {
			result = oneCardService.queryPolicyList(pageSize, pageNum, title);
		} catch (Exception e) {
			log.error("queryPolicyList异常", e);
			result = new Result();
			result.setCode(ExceptionEnum.SYSTEM_ERROR.code);
			result.setDesc(ExceptionEnum.SYSTEM_ERROR.desc);
		}
		return result;
	}
	
}
