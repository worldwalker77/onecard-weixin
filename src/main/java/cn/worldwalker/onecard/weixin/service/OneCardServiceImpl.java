package cn.worldwalker.onecard.weixin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.worldwalker.onecard.weixin.common.exception.BusinessException;
import cn.worldwalker.onecard.weixin.common.exception.ExceptionEnum;
import cn.worldwalker.onecard.weixin.common.utils.RequestUtil;
import cn.worldwalker.onecard.weixin.dao.PolicyDao;
import cn.worldwalker.onecard.weixin.dao.WxBindDao;
import cn.worldwalker.onecard.weixin.domain.FeedBackModel;
import cn.worldwalker.onecard.weixin.domain.PolicyModel;
import cn.worldwalker.onecard.weixin.domain.QueryModel;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.UserSession;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;
import cn.worldwalker.onecard.weixin.rpc.WeiXinRpc;

@Service
public class OneCardServiceImpl implements OneCardService{
	
	@Autowired
	private WeiXinRpc weiXinRpc;
	@Autowired
	private WxBindDao wxBindDao;
	@Autowired
	private PolicyDao policyDao;
	

	@Override
	public Result doBind(String idNum, String mobilePhone) {
		String openId = RequestUtil.getOpenIdFromSession();
		if (StringUtils.isBlank(openId) || StringUtils.isBlank(idNum)|| StringUtils.isBlank(mobilePhone)) {
			throw new BusinessException(ExceptionEnum.PARAMS_ERROR);
		}
		WxBindModel model = new WxBindModel();
		model.setOpenId(openId);
		model.setIdNum(idNum);
		model.setMobilePhone(mobilePhone);
		wxBindDao.insertWxBind(model);
		
		UserSession userSession = new UserSession();
		userSession.setIdNum(idNum);
		userSession.setMobilePhone(mobilePhone);
		userSession.setOpenId(openId);
		RequestUtil.setUserSession(userSession);
		Result result = new Result();
		return result;
	}


	@Override
	public WxBindModel selectWxBind(String idNum, String openId) {
		WxBindModel model = new WxBindModel();
		model.setOpenId(openId);
		model.setIdNum(idNum);
		WxBindModel resModel = null;
		try {
			resModel = wxBindDao.selectWxBind(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resModel;
	}


	@Override
	public String getOpenIdFromWeiXin(String code) {
//		WeiXinUserInfo wxUserInfo = weiXinRpc.getWeiXinUserInfo(code);
//		if (wxUserInfo == null) {
//			return null;
//		}
//		return wxUserInfo.getOpenId();
		return "testopenid";
	}


	@Override
	public Result queryPolicyList(QueryModel queryModel) {
		Result result = new Result();
		PolicyModel pmodel = new PolicyModel();
		pmodel.setStart(queryModel.getStart());
		pmodel.setLimit(queryModel.getLimit());
		pmodel.setTitle(queryModel.getTitle());
		List<PolicyModel> policyList = policyDao.selectPolicyByTitle(pmodel);
		result.setData(policyList);
		return result;
	}


	@Override
	public Result queryFeedbacks() {
		Result result = new Result();
		List<FeedBackModel> newsList = new ArrayList<FeedBackModel>();
		FeedBackModel newsModel1 = new FeedBackModel();
		newsModel1.setTitle("标题1");
		FeedBackModel newsModel2 = new FeedBackModel();
		newsModel2.setTitle("标题2");
		newsList.add(newsModel1);
		newsList.add(newsModel2);
		result.setData(newsList);
		return result;
	}


	@Override
	public Result queryPolicyList() {
		
		return null;
	}

}
