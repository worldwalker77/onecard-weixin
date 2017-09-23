package cn.worldwalker.onecard.weixin.manager;

import cn.worldwalker.onecard.weixin.domain.Result;


public interface OneCardManager {
	public Result modifyIdNum(Long contactId, String oldIdNum, String newIdNum, String newTel);
}
