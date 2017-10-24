package cn.worldwalker.onecard.weixin.service;

import java.util.List;

import cn.worldwalker.onecard.weixin.domain.ContactInfoModel;
import cn.worldwalker.onecard.weixin.domain.FeedBackModel;
import cn.worldwalker.onecard.weixin.domain.ModifyParam;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.SubsidyModel;
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
	
	public ContactInfoModel getContactInfoByIdNum(String idNum);
	
	public Result modifyIdNum(ModifyParam param);
	
	public void queryGrantsByType(List<SubsidyModel> list);
	
}
