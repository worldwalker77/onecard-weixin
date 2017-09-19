package cn.worldwalker.onecard.weixin.dao;

import java.util.List;

import cn.worldwalker.onecard.weixin.domain.PolicyModel;

public interface PolicyDao {
	
	public List<PolicyModel> selectPolicyByTitle(PolicyModel model);
}
