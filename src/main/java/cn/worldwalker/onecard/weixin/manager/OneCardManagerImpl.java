package cn.worldwalker.onecard.weixin.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.worldwalker.onecard.weixin.common.exception.BusinessException;
import cn.worldwalker.onecard.weixin.common.exception.ExceptionEnum;
import cn.worldwalker.onecard.weixin.common.utils.RequestUtil;
import cn.worldwalker.onecard.weixin.dao.ContactInfoDao;
import cn.worldwalker.onecard.weixin.dao.SubsidyDao;
import cn.worldwalker.onecard.weixin.dao.WxBindDao;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.UserSession;
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
	public Result modifyIdNum(String oldIdNum, String oldTel, String newIdNum, String newTel) {
//		ContactInfoModel updateModel = new ContactInfoModel();
//		updateModel.setId(contactId);
//		updateModel.setIdNum(newIdNum);
//		updateModel.setTel(newTel);
//		Integer res = contactInfoDao.updateContactInfoById(updateModel);
//		if(res < 1){
//			throw new BusinessException(ExceptionEnum.MODIFY_FAIL);
//		}
		WxBindModel wxModel = new WxBindModel();
		wxModel.setOpenId(RequestUtil.getOpenIdFromSession());
		WxBindModel resModel = wxBindDao.selectWxBind(wxModel);
		if (resModel == null) {
			throw new BusinessException(ExceptionEnum.NOT_BIND);
		}
		if (!resModel.getIdNum().equals(oldIdNum)) {
			throw new BusinessException(ExceptionEnum.ID_NUM_NOT_MATCH);
		}
		if (!resModel.getMobilePhone().equals(oldTel)) {
			throw new BusinessException(ExceptionEnum.TEL_NOT_MATCH);
		}
		/**查询新身份证号是否已经被别人绑定*/
		wxModel.setIdNum(newIdNum);
		wxModel.setOpenId(null);
		resModel = wxBindDao.selectWxBind(wxModel);
		if (resModel != null && !oldIdNum.equals(newIdNum)) {
			throw new BusinessException(ExceptionEnum.HAS_BIND_BY_OTHER_PEOPLE);
		}
		wxModel.setMobilePhone(newTel);
		wxModel.setOpenId(RequestUtil.getOpenIdFromSession());
		Integer res = wxBindDao.updateWxBindIdNum(wxModel);
		if(res < 1){
			throw new BusinessException(ExceptionEnum.MODIFY_FAIL);
		}
		UserSession userSession = RequestUtil.getUserSession();
		userSession.setIdNum(newIdNum);
		userSession.setMobilePhone(newTel);
		RequestUtil.setUserSession(userSession);
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("oldIdNum", oldIdNum);
//		map.put("newIdNum", newIdNum);
//		subsidyDao.updateIdNum(map);
		return new Result();
	}
	
}
