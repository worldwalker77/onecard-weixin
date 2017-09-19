package cn.worldwalker.onecard.weixin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.worldwalker.onecard.weixin.domain.FeedBackModel;
import cn.worldwalker.onecard.weixin.domain.QueryModel;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.service.OneCardService;

@Controller
@RequestMapping(value="/onecard")
public class OneCardController {
	
	protected static final Logger logger = LoggerFactory.getLogger(OneCardController.class);
	
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
		QueryModel queryModel = new QueryModel();
		queryModel.setPageNum(pageNum);
		queryModel.setPageSize(pageSize);
		queryModel.setTitle(title);
		queryModel.setStart(pageSize*pageNum);
		queryModel.setLimit(pageSize);
		return oneCardService.queryPolicyList(queryModel);
	}
	
	/**
	 * 新增投诉页
	 * @return
	 */
	@RequestMapping(value="/feedback")
	public ModelAndView feedback(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/feedback");
		return mv;
	}
	/**
	 * 添加投诉建议
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addFeedBack")
	@ResponseBody
	public Result addFeedBack(@RequestBody FeedBackModel model){
		Result result = new Result();
		return result;
	}
	
	/**
	 * 我的投诉建议列表页
	 * @return
	 */
	@RequestMapping(value="/feedbackList")
	public ModelAndView feedbackList(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/feedback_list");
		return mv;
	}
	/**
	 * 查询我的投诉建议列表
	 * @return
	 */
	@RequestMapping(value="/queryFeedbackList")
	@ResponseBody
	public Result queryFeedbacks(){
		return oneCardService.queryFeedbacks();
	}
	/**
	 * 某条投诉建议详情
	 * @return
	 */
	@RequestMapping(value="/feedbackDetail")
	public ModelAndView feedbackDetail(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/feedback_detail");
		return mv;
	}
	
	/**
	 * 补助列表页
	 * @return
	 */
	@RequestMapping(value="/subsidyList")
	public ModelAndView subsibyList(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("wechat/subsidy");
		return mv;
	}
	/**
	 * 查询补助列表
	 * @return
	 */
	@RequestMapping(value="/querySubsidyList")
	@ResponseBody
	public Result querySubsibyList(Integer pageSize, Integer pageNum, String startTime, String endTime){
		return oneCardService.queryFeedbacks();
	}
	
}
