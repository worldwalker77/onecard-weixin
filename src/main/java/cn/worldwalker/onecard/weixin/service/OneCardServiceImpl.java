package cn.worldwalker.onecard.weixin.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
import cn.worldwalker.onecard.weixin.dao.ProjectDetailDao;
import cn.worldwalker.onecard.weixin.dao.SubsidyDao;
import cn.worldwalker.onecard.weixin.dao.WxBindDao;
import cn.worldwalker.onecard.weixin.domain.ContactInfoModel;
import cn.worldwalker.onecard.weixin.domain.FeedBackModel;
import cn.worldwalker.onecard.weixin.domain.ModifyParam;
import cn.worldwalker.onecard.weixin.domain.PolicyModel;
import cn.worldwalker.onecard.weixin.domain.ProjectDetail;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.domain.SubsidyModel;
import cn.worldwalker.onecard.weixin.domain.UserSession;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;
import cn.worldwalker.onecard.weixin.manager.OneCardManager;
import cn.worldwalker.onecard.weixin.rpc.WeiXinRpc;

@Service
public class OneCardServiceImpl implements OneCardService{
	protected static final Logger log = LoggerFactory.getLogger(OneCardServiceImpl.class);
	private HashMap<String, ProjectDetail> map = new HashMap<String, ProjectDetail>();
	private static DecimalFormat NUM_FORMAT = new DecimalFormat("0.00");
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
	@Autowired
	private OneCardManager oneCardManager;
	@Autowired
	private ProjectDetailDao projectDetailDao;
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
//		if (cim == null) {
//			throw new BusinessException(ExceptionEnum.ID_NUM_NOT_EXIST);
//		}
		WxBindModel model = new WxBindModel();
		model.setOpenId(openId);
		model.setIdNum(idNum);
		model.setMobilePhone(mobilePhone);
		wxBindDao.insertWxBind(model);
		
		UserSession userSession = new UserSession();
		userSession.setIdNum(idNum);
		userSession.setMobilePhone(mobilePhone);
		userSession.setOpenId(openId);
		if (cim != null) {
			userSession.setfName(cim.getfName());
			userSession.setEnName(cim.getEnName());
			userSession.setGrName(cim.getGrName());
		}
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
		return weiXinRpc.getOpenId(code);
//		return "owOENwaaII9RANdtNiIzyTa69_Rc";
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
		queryGrantsByType(list);
		Result result = new Result();
		result.setData(list);
		return result;
	}
	
	private void queryGrantsByType(List<SubsidyModel> list) {
		
		ProjectDetail tmp;
		StringBuilder sb;
		for (SubsidyModel g : list) {
			if (g.getRgCode() == null)
				continue;

			// countNum = 0.0d;
			sb = new StringBuilder();

			String pro_id = g.getProId();
			
			// num2_1 - 30
			if (pro_id.equalsIgnoreCase("03") || pro_id.equalsIgnoreCase("09")
					|| pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("18")
					|| pro_id.equalsIgnoreCase("19")
					|| pro_id.equalsIgnoreCase("20")
					|| pro_id.equalsIgnoreCase("22")
					|| pro_id.equalsIgnoreCase("23")
					|| pro_id.equalsIgnoreCase("24")
					|| pro_id.equalsIgnoreCase("25")
					|| pro_id.equalsIgnoreCase("16")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum21() != null && g.getNum21().doubleValue() > 0.0d) {

					tmp = map.get(g.getRgCode() + g.getProId() + "num2_1");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_1", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_1", tmp);
					}

					if (tmp != null) {
						g.setNum21Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum21()));
						sb.append(g.getNum21Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("02") || pro_id.equalsIgnoreCase("03")
					|| pro_id.equalsIgnoreCase("04")
					|| pro_id.equalsIgnoreCase("07")
					|| pro_id.equalsIgnoreCase("08")
					|| pro_id.equalsIgnoreCase("10")
					|| pro_id.equalsIgnoreCase("11")
					|| pro_id.equalsIgnoreCase("12")
					|| pro_id.equalsIgnoreCase("13")
					|| pro_id.equalsIgnoreCase("14")
					|| pro_id.equalsIgnoreCase("15")
					|| pro_id.equalsIgnoreCase("16")
					|| pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("19")
					|| pro_id.equalsIgnoreCase("21")
					|| pro_id.equalsIgnoreCase("22")
					|| pro_id.equalsIgnoreCase("24")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum22() != null && g.getNum22().doubleValue() > 0.0d) {
					tmp = map.get(g.getRgCode() + g.getProId() + "num2_2");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_2", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_2", tmp);
					}

					if (tmp != null) {
						g.setNum22Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum22()));
						sb.append(g.getNum22Str()).append(',');
					}
				}
			}

			if (pro_id.equalsIgnoreCase("01") || pro_id.equalsIgnoreCase("03")
					|| pro_id.equalsIgnoreCase("06")
					|| pro_id.equalsIgnoreCase("13")
					|| pro_id.equalsIgnoreCase("14")
					|| pro_id.equalsIgnoreCase("15")
					|| pro_id.equalsIgnoreCase("16")
					|| pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum23() != null && g.getNum23().doubleValue() > 0.0d) {
					tmp = map.get(g.getRgCode() + g.getProId() + "num2_3");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_3", g.getRgCode(), g.getProId()));
						map.put(g.getRgCode() + g.getProId() + "num2_3", tmp);
					}

					if (tmp != null) {
						g.setNum23Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum23()));
						sb.append(g.getNum23Str()).append(',');
					}
				}
			}

			if (pro_id.equalsIgnoreCase("03") || pro_id.equalsIgnoreCase("04")
					|| pro_id.equalsIgnoreCase("06")
					|| pro_id.equalsIgnoreCase("14")
					|| pro_id.equalsIgnoreCase("16")
					|| pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum24() != null && g.getNum24().doubleValue() > 0.0d) {
					tmp = map.get(g.getRgCode() + g.getProId() + "num2_4");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_4", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_4", tmp);
					}

					if (tmp != null) {
						g.setNum24Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum24()));
						sb.append(g.getNum24Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("03") || pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("16")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum25() != null && g.getNum25().doubleValue() > 0.0d) {
					tmp = map.get(g.getRgCode() + g.getProId() + "num2_5");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_5", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_5", tmp);
					}

					if (tmp != null) {
						g.setNum25Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum25()));
						sb.append(g.getNum25Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("03") || pro_id.equalsIgnoreCase("04")
					|| pro_id.equalsIgnoreCase("16")
					|| pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum26() != null && g.getNum26().doubleValue() > 0.0d) {
					tmp = map.get(g.getRgCode() + g.getProId() + "num2_6");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_6", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_6", tmp);
					}

					if (tmp != null) {
						g.setNum26Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum26()));
						sb.append(g.getNum26Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("03") || pro_id.equalsIgnoreCase("16")
					|| pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum27() != null && g.getNum27().doubleValue() > 0.0d) {
					tmp = map.get(g.getRgCode() + g.getProId() + "num2_7");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_7", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_7", tmp);
					}

					if (tmp != null) {
						g.setNum27Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum27()));
						sb.append(g.getNum27Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("03") || pro_id.equalsIgnoreCase("16")
					|| pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum28() != null && g.getNum28().doubleValue() > 0.0d) {

					tmp = map.get(g.getRgCode() + g.getProId() + "num2_8");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_8", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_8", tmp);
					}

					if (tmp != null) {
						g.setNum28Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum28()));
						sb.append(g.getNum28Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("16") || pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum29() != null && g.getNum29().doubleValue() > 0.0d) {

					tmp = map.get(g.getRgCode() + g.getProId() + "num2_9");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_9", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_9", tmp);
					}

					if (tmp != null) {
						g.setNum29Str(tmp.getDetailName() + ":" + NUM_FORMAT.format(g.getNum29()));
						sb.append(g.getNum29Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("16") || pro_id.equalsIgnoreCase("17")
					|| pro_id.equalsIgnoreCase("60")) {
				if (g.getNum210() != null && g.getNum210().doubleValue() > 0.0d) {

					tmp = map.get(g.getRgCode() + g.getProId() + "num2_10");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_10", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_10", tmp);
					}

					if (tmp != null) {
						g.setNum210Str(tmp.getDetailName() + ":"
								+ g.getNum210());
						sb.append(g.getNum210Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("16")) {
				if (g.getNum211() != null && g.getNum211().doubleValue() > 0.0d) {

					tmp = map.get(g.getRgCode() + g.getProId() + "num2_11");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_11", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_11", tmp);
					}

					if (tmp != null) {
						g.setNum211Str(tmp.getDetailName() + ":"
								+ NUM_FORMAT.format(g.getNum211()));
						sb.append(g.getNum211Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("16")) {
				if (g.getNum212() != null && g.getNum212().doubleValue() > 0.0d) {

					tmp = map.get(g.getRgCode() + g.getProId() + "num2_12");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_12", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_12", tmp);
					}

					if (tmp != null) {
						g.setNum212Str(tmp.getDetailName() + ":"
								+ NUM_FORMAT.format(g.getNum212()));
						sb.append(g.getNum212Str()).append(',');
					}
				}
			}
			if (pro_id.equalsIgnoreCase("16")) {
				if (g.getNum213() != null && g.getNum213().doubleValue() > 0.0d) {
					tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_13", g.getRgCode(), g.getProId())
);
					if (tmp != null) {
						g.setNum213Str(tmp.getDetailName() + ":"
								+ g.getNum213());
						sb.append(g.getNum213Str()).append(',');
					}
				}
			}
			
			if (pro_id.equalsIgnoreCase("03")) {
				if (g.getNum217() != null && g.getNum217().doubleValue() > 0.0d) {

					tmp = map.get(g.getRgCode() + g.getProId() + "num2_17");
					if (tmp == null) {
						tmp = projectDetailDao.getByfieldNameAndRgCodeAndProCode(new ProjectDetail("num2_17", g.getRgCode(), g.getProId())
);
						map.put(g.getRgCode() + g.getProId() + "num2_17", tmp);
					}

					if (tmp != null) {
						g.setNum217Str(tmp.getDetailName() + ":"
								+ NUM_FORMAT.format(g.getNum217()));
						sb.append(g.getNum217Str()).append(',');
					}
				}
			}			
			
			String details = "";
			if(sb.length()>0){
				details= sb.substring(0,sb.length()-1);
			}

			g.setDetail(details);
			g.setTotal(String.valueOf(NUM_FORMAT.format(g.getNum224())));
		}

	}

	@Override
	public ContactInfoModel getContactInfoByIdNum(String idNum) {
		return contactInfoDao.selectContactInfoByIdNum(idNum);
	}

	@Override
	public Result modifyIdNum(ModifyParam param) {
		if (param == null 
		    || StringUtils.isBlank(param.getOldIdNum()) 
		    || StringUtils.isBlank(param.getOldTel()) 
		    || StringUtils.isBlank(param.getNewIdNum()) 
		    || StringUtils.isBlank(param.getNewTel())) {
			throw new BusinessException(ExceptionEnum.PARAMS_ERROR);
		}
		/**校验老身份证和手机号是否正确*/
//		ContactInfoModel seModel = contactInfoDao.selectContactInfoByIdNum(param.getOldIdNum());
//		if (seModel == null) {
//			throw new BusinessException(ExceptionEnum.ID_NUM_NOT_EXIST);
//		}
//		if (!param.getOldTel().equals(seModel.getTel())) {
//			throw new BusinessException(ExceptionEnum.TEL_NOT_MATCH);
//		}
		return oneCardManager.modifyIdNum(param.getOldIdNum(), param.getOldTel(), param.getNewIdNum(), param.getNewTel());
	}


}
