package cn.worldwalker.onecard.weixin.rpc;

import cn.worldwalker.onecard.weixin.domain.WeiXinUserInfo;


public interface WeiXinRpc {
	public WeiXinUserInfo getWeiXinUserInfo(String code);
	
	
}
