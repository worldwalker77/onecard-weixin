package cn.worldwalker.onecard.weixin.rpc;

import cn.worldwalker.onecard.weixin.domain.WeiXinUserInfo;


public interface WeiXinRpc {
	
	public String getOpenId(String code);
	
	
}
