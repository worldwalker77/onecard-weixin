package cn.worldwalker.onecard.weixin.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.worldwalker.onecard.weixin.common.exception.BusinessException;
import cn.worldwalker.onecard.weixin.common.exception.ExceptionEnum;
import cn.worldwalker.onecard.weixin.common.utils.RequestUtil;
import cn.worldwalker.onecard.weixin.dao.ContactInfoDao;
import cn.worldwalker.onecard.weixin.dao.FeedbackDao;
import cn.worldwalker.onecard.weixin.dao.PolicyDao;
import cn.worldwalker.onecard.weixin.dao.SubsidyDao;
import cn.worldwalker.onecard.weixin.dao.WxBindDao;
import cn.worldwalker.onecard.weixin.domain.ContactInfoModel;
import cn.worldwalker.onecard.weixin.domain.FeedBackModel;
import cn.worldwalker.onecard.weixin.domain.PolicyModel;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.SubsidyModel;
import cn.worldwalker.onecard.weixin.domain.UserSession;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;
import cn.worldwalker.onecard.weixin.rpc.WeiXinRpc;

@Service
public class OneCardServiceImpl implements OneCardService{
	protected static final Logger log = LoggerFactory.getLogger(OneCardServiceImpl.class);
	@Autowired
	private WeiXinRpc weiXinRpc;
	@Autowired
	private WxBindDao wxBindDao;
	@Autowired
	private PolicyDao policyDao;
	@Autowired
	private FeedbackDao feedbackDao;
	@Autowired
	private SubsidyDao subsidyDao;
	@Autowired
	private ContactInfoDao contactInfoDao;
	/**
	 * 绑定
	 * @param idNum
	 * @param mobilePhone
	 * @return
	 * 2017年9月19日
	 */
	@Override
	public Result doBind(String idNum, String mobilePhone) {
		String openId = RequestUtil.getOpenIdFromSession();
		if (StringUtils.isBlank(openId) || StringUtils.isBlank(idNum)|| StringUtils.isBlank(mobilePhone)) {
			throw new BusinessException(ExceptionEnum.PARAMS_ERROR);
		}
		ContactInfoModel cim = contactInfoDao.selectContactInfoByIdNum(idNum);
		if (cim == null) {
			throw new BusinessException(ExceptionEnum.ID_NUM_NOT_EXIST);
		}
		WxBindModel model = new WxBindModel();
		model.setOpenId(openId);
		model.setIdNum(idNum);
		model.setMobilePhone(mobilePhone);
		wxBindDao.insertWxBind(model);
		
		UserSession userSession = new UserSession();
		userSession.setIdNum(idNum);
		userSession.setMobilePhone(cim.getTel());
		userSession.setOpenId(openId);
		userSession.setfName(cim.getfName());
		userSession.setEnName(cim.getEnName());
		userSession.setGrName(cim.getGrName());
		RequestUtil.setUserSession(userSession);
		Result result = new Result();
		return result;
	}

	/**
	 * 获取微信绑定信息
	 * @param idNum
	 * @param openId
	 * @return
	 * 2017年9月19日
	 */
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

	/**
	 * 获取微信信息
	 * @param code
	 * @return
	 * 2017年9月19日
	 */
	@Override
	public String getOpenIdFromWeiXin(String code) {
//		return weiXinRpc.getOpenId(code);
		return "owOENwWf_yn3l0JXu-u8BYzoxOfw";
	}

	/**
	 * 获取政策列表（分页）
	 * @param queryModel
	 * @return
	 * 2017年9月19日
	 */
	@Override
	public Result queryPolicyList(Integer pageSize, Integer pageNum, String title) {
		Result result = new Result();
		PolicyModel pmodel = new PolicyModel();
		pmodel.setStart(pageSize*pageNum);
		pmodel.setLimit(pageSize);
		pmodel.setTitle(title);
		List<PolicyModel> policyList = policyDao.selectPolicyByTitle(pmodel);
		result.setData(policyList);
		return result;
	}
	/**
	 * 添加反馈建议
	 * @param model
	 * @return
	 * 2017年9月19日
	 */
	@Override
	public Result addFeedback(FeedBackModel model) {
		model.setOpenId(RequestUtil.getOpenIdFromSession());
		model.setTitle(model.getTitle());
		model.setContent(model.getContent());
		model.setTel(model.getTel());
		model.setfName(model.getfName());
		model.setEnName(model.getEnName());
		model.setGrName(model.getGrName());
		feedbackDao.insertFeedback(model);
		Result result = new Result();
		return result;
	}
	/**
	 * 查询反馈建议（分页）
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * 2017年9月19日
	 */
	@Override
	public Result queryFeedbacks(Integer pageSize, Integer pageNum) {
		Result result = new Result();
		FeedBackModel fbmodel = new FeedBackModel();
		fbmodel.setOpenId(RequestUtil.getOpenIdFromSession());
		fbmodel.setStart(pageSize*pageNum);
		fbmodel.setLimit(pageSize);
		List<FeedBackModel> newsList;
		newsList = feedbackDao.selectFeedbacks(fbmodel);
		result.setData(newsList);
		return result;
	}
	/**
	 * 查询反馈建议详情
	 * @param id
	 * @return
	 * 2017年9月19日
	 */
	@Override
	public FeedBackModel queryFeedbackDetail(Long id) {
		FeedBackModel model = new FeedBackModel();
		try {
			model = feedbackDao.selectFeedbackDetail(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * 查询补助列表（分页）
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * 2017年9月19日
	 */
	@Override
	public Result querySubsidys(Integer pageSize, Integer pageNum, String startTime, String endTime) {
		SubsidyModel model = new SubsidyModel();
		model.setStart(pageSize*pageNum);
		model.setLimit(pageSize);
		model.setIdNum(RequestUtil.getIdNumFromSession());
		if (StringUtils.isNotBlank(startTime)) {
			model.setStartTime(startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			model.setEndTime(endTime);
		}
		List<SubsidyModel> list = subsidyDao.selectSubsidys(model);
		Result result = new Result();
		result.setData(list);
		return result;
	}


}
