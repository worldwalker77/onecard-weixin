package cn.worldwalker.onecard.weixin.dao;

import cn.worldwalker.onecard.weixin.domain.ContactInfoModel;

public interface ContactInfoDao {
	
	public ContactInfoModel selectContactInfoByIdNum(String idNum);
}
