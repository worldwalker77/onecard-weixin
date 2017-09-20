package cn.worldwalker.onecard.weixin.service;

import cn.worldwalker.onecard.weixin.domain.FeedBackModel;
import cn.worldwalker.onecard.weixin.domain.QueryModel;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;

public interface OneCardService{
	
	public Result doBind(String idNum, String mobilePhone);
	
	public WxBindModel selectWxBind(String idNum, String openId);
	
	public String getOpenIdFromWeiXin(String code);
	
	public Result queryPolicyList(Integer pageSize, Integer pageNum, String title);
	
	public Result addFeedback(FeedBackModel model);
	
	public Result queryFeedbacks(Integer pageSize, Integer pageNum);
	
	public FeedBackModel queryFeedbackDetail(Long id);
	
	public Result querySubsidys(Integer pageSize, Integer pageNum, String startTime, String endTime);
	
}
