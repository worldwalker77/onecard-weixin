package cn.worldwalker.onecard.weixin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.worldwalker.onecard.weixin.common.exception.ExceptionEnum;
import cn.worldwalker.onecard.weixin.domain.Result;
import cn.worldwalker.onecard.weixin.job.SubsidyNoticeMsgJob;

@Controller
@RequestMapping(value="/msg")
public class SendMsgController {
	
	protected static final Logger log = LoggerFactory.getLogger(SendMsgController.class);
	
	@Autowired
	private SubsidyNoticeMsgJob sbusidyNoticeMsgJob;
	
	@RequestMapping(value="/send")
	@ResponseBody
	public Result sendTemplateMsg(String startTime, String endTime){
		Result result = null;
		try {
			sbusidyNoticeMsgJob.doSendMsg(startTime, endTime);
		} catch (Exception e) {
			log.error("sendTemplateMsg异常", e);
			result = new Result();
			result.setCode(ExceptionEnum.SYSTEM_ERROR.code);
			result.setDesc(ExceptionEnum.SYSTEM_ERROR.desc);
		}
		return result;
	}
	
}
