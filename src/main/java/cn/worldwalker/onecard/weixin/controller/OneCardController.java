package cn.worldwalker.onecard.weixin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.worldwalker.onecard.weixin.domain.QueryModel;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.service.OneCardService;

@Controller
@RequestMapping(value="/onecard")
public class OneCardController {
	
	protected static final Logger logger = LoggerFactory.getLogger(OneCardController.class);
	
	@Autowired
	private OneCardService oneCardService;
	
	@RequestMapping(value="/queryNews")
	@ResponseBody
	public Result queryNews(Integer pageSize, Integer pageNum, String title){
		QueryModel queryModel = new QueryModel();
		queryModel.setPageNum(pageNum);
		queryModel.setPageSize(pageSize);
		queryModel.setTitle(title);
		return oneCardService.queryNews(queryModel);
	}
	
	
	@RequestMapping(value="/news")
	public ModelAndView news(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/news");
		return mv;
	}
	
}
