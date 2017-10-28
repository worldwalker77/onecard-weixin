package cn.worldwalker.onecard.weixin.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.worldwalker.onecard.weixin.common.utils.DateUtil;
import cn.worldwalker.onecard.weixin.common.utils.JsonUtil;
import cn.worldwalker.onecard.weixin.common.utils.httpclient.HttpClientUtils;
import cn.worldwalker.onecard.weixin.constant.Constant;
import cn.worldwalker.onecard.weixin.dao.SubsidyDao;
import cn.worldwalker.onecard.weixin.dao.WxBindDao;
import cn.worldwalker.onecard.weixin.domain.SubsidyModel;
import cn.worldwalker.onecard.weixin.domain.WxBindModel;
import cn.worldwalker.onecard.weixin.domain.wxapi.TemplateMsg;
import cn.worldwalker.onecard.weixin.domain.wxapi.TemplateMsgResponse;
import cn.worldwalker.onecard.weixin.service.OneCardService;

@Component(value="subsidyNoticeMsgJob")
public class SubsidyNoticeMsgJob {
	
	protected static final Logger log = LoggerFactory.getLogger(SubsidyNoticeMsgJob.class);
	
	@Autowired
	private SubsidyDao subsidyDao;
	@Autowired
	private OneCardService oneCardService;
	@Autowired
	private WxBindDao wxBindDao;
	@Autowired
	private AccessTokenRefreshJob accessTokenRefreshJob;
	
	public void doTask(){
		String today = DateUtil.format(new Date(), "yyyy-MM-dd");
		SubsidyModel model = new SubsidyModel();
		model.setStartTime(today + " 00:00:00");
		model.setEndTime(today + " 23:59:59");
	}
	
	public void doSendMsg(String startTime, String endTime){
		SubsidyModel model = new SubsidyModel();
		model.setStartTime(startTime);
		model.setEndTime(endTime);
		List<SubsidyModel> noticeList = subsidyDao.selectNeedWeixinNoticeSubsidys(model);
		oneCardService.queryGrantsByType(noticeList);
		for(SubsidyModel noticeModel : noticeList){
			WxBindModel wbModel = new WxBindModel();
			wbModel.setIdNum(noticeModel.getIdNum());
			/**查询对应的openId*/
			wbModel = wxBindDao.selectWxBind(wbModel);
			if (wbModel == null) {
				continue;
			}
			TemplateMsg msg = new TemplateMsg();
			msg.setTemplate_id(Constant.templateId);
			msg.setTouser(wbModel.getOpenId());
			msg.setUrl("");
			msg.getData().getFirst().setValue(noticeModel.getProName());
			msg.getData().getKeyword1().setValue(noticeModel.getfName());
			msg.getData().getKeyword2().setValue(noticeModel.getTotal());
			msg.getData().getKeyword3().setValue(noticeModel.getBankName());
			msg.getData().getKeyword4().setValue(noticeModel.getBankNo());
			msg.getData().getKeyword5().setValue(DateUtil.formatDefaultPattern(noticeModel.getGrantDate()));
			msg.getData().getRemark().setValue(noticeModel.getDetail());
			String params = JsonUtil.toJson(msg);
			String url = Constant.sendTemplateMsgUrl;
			String token = AccessTokenRefreshJob.getAccessCode();
			if (StringUtils.isEmpty(token)) {
				token = accessTokenRefreshJob.doTask();
			}
			url = url.replace("ACCESS_TOKEN", AccessTokenRefreshJob.getAccessCode());
			String result = "";
			try {
				result = HttpClientUtils.postParameters(url, params);
				TemplateMsgResponse response = JsonUtil.toObject(result, TemplateMsgResponse.class);
				if (response == null || response.getErrcode() != 0) {
					log.error("发送模板消息接口返回异常,request:" + params + ",response:" + result);
					continue;
				}
				/**更新发送状态*/
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("uuid", noticeModel.getUuid());
				subsidyDao.updateWeixinSendStatus(map);
			} catch (Exception e) {
				log.error("发送模板消息接口返回异常,request:" + params + ",response:" + result, e);
			}
		}
	}
	
	public static void main(String[] args) {
		String token = "4B0RfKkGjGxS3ZUUwd5pQED2TxRI4iKgcaDG5zECsYnkjVMBzWRdPN0M9d_x8frqJLklc9oRYWMkQ-m_uNBBZpU2VU_r7UwmB8Sv-s_uioJNNHxlriiYJx3bNac0JkQcEGLhAFAVXC";
		TemplateMsg msg = new TemplateMsg();
		msg.setTemplate_id(Constant.templateId);
		msg.setTouser("owOENwWf_yn3l0JXu-u8BYzoxOfw");
		msg.setUrl("");
		msg.getData().getFirst().setValue("粮食补贴");
		msg.getData().getKeyword1().setValue("张三");
		msg.getData().getKeyword2().setValue("100.00");
		msg.getData().getKeyword3().setValue("农村信用社");
		msg.getData().getKeyword4().setValue("111111111111");
		msg.getData().getKeyword5().setValue("2017-10-25 15:27:00");
		msg.getData().getRemark().setValue("粮食补贴100.00元");
		String params = JsonUtil.toJson(msg);
		System.out.println(params);
	}
}
