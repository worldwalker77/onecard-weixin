package cn.worldwalker.onecard.weixin.dao;

import cn.worldwalker.onecard.weixin.domain.WxBindModel;

public interface WxBindDao {
	
	public Integer insertWxBind(WxBindModel model);
	
	public WxBindModel selectWxBind(WxBindModel model);
}
