package cn.worldwalker.onecard.weixin.dao;

import java.util.HashMap;
import java.util.List;

import cn.worldwalker.onecard.weixin.domain.SubsidyModel;

public interface SubsidyDao {
	
	public List<SubsidyModel> selectSubsidys(SubsidyModel model);
	
	public Integer updateIdNum(HashMap<String, String> map);
	
	public List<SubsidyModel> selectNeedWeixinNoticeSubsidys(SubsidyModel model);
	
	public void batchUpdateWeixinSendStatus(List<SubsidyModel> list);
	
	public Integer updateWeixinSendStatus(HashMap<String, Object> map);
	
}
