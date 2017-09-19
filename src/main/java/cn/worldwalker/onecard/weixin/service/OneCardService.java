package cn.worldwalker.onecard.weixin.service;

import cn.worldwalker.onecard.weixin.domain.QueryModel;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;

public interface OneCardService{
	
	public Result doBind(String idNum, String mobilePhone);
	
	public WxBindModel selectWxBind(String idNum, String openId);
	
	public String getOpenIdFromWeiXin(String code);
	
	public Result queryPolicyList(QueryModel queryModel);
	
	public Result queryFeedbacks();
	
    public Result queryPolicyList();
}
