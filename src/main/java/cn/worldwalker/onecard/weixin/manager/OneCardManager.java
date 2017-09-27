package cn.worldwalker.onecard.weixin.manager;

import cn.worldwalker.onecard.weixin.domain.Result;


public interface OneCardManager {
	public Result modifyIdNum(String oldIdNum, String oldTel, String newIdNum, String newTel);
}
