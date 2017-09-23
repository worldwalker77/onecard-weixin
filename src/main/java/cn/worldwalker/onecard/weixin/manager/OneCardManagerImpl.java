package cn.worldwalker.onecard.weixin.manager;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.worldwalker.onecard.weixin.common.exception.BusinessException;
import cn.worldwalker.onecard.weixin.common.exception.ExceptionEnum;
import cn.worldwalker.onecard.weixin.common.utils.RequestUtil;
import cn.worldwalker.onecard.weixin.dao.ContactInfoDao;
import cn.worldwalker.onecard.weixin.dao.SubsidyDao;
import cn.worldwalker.onecard.weixin.dao.WxBindDao;
import cn.worldwalker.onecard.weixin.domain.ContactInfoModel;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;

/**一卡通更新事务控制类*/
@Component
public class OneCardManagerImpl implements OneCardManager{
	
	@Autowired
	private ContactInfoDao contactInfoDao;
	@Autowired
	private WxBindDao wxBindDao;
	@Autowired
	private SubsidyDao subsidyDao;

	@Override
	public Result modifyIdNum(Long contactId, String oldIdNum, String newIdNum, String newTel) {
		/**修改*/
		ContactInfoModel updateModel = new ContactInfoModel();
		updateModel.setId(contactId);
		updateModel.setIdNum(newIdNum);
		updateModel.setTel(newTel);
		Integer res = contactInfoDao.updateContactInfoById(updateModel);
		if(res < 1){
			throw new BusinessException(ExceptionEnum.MODIFY_FAIL);
		}
		WxBindModel wxModel = new WxBindModel();
		wxModel.setOpenId(RequestUtil.getOpenIdFromSession());
		wxModel.setIdNum(newIdNum);
		wxModel.setMobilePhone(newTel);
		res = wxBindDao.updateWxBindIdNum(wxModel);
		if(res < 1){
			throw new BusinessException(ExceptionEnum.MODIFY_FAIL);
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("oldIdNum", oldIdNum);
		map.put("newIdNum", newIdNum);
		subsidyDao.updateIdNum(map);
		return new Result();
	}
	
}
