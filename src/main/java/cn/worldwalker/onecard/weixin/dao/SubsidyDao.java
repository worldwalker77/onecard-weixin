package cn.worldwalker.onecard.weixin.dao;

import java.util.List;

import cn.worldwalker.onecard.weixin.domain.SubsidyModel;

public interface SubsidyDao {
	
	public List<SubsidyModel> selectSubsidys(SubsidyModel model);
	
}
