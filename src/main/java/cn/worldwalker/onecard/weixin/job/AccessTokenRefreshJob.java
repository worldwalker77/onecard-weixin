package cn.worldwalker.onecard.weixin.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.worldwalker.onecard.weixin.common.utils.JsonUtil;
import cn.worldwalker.onecard.weixin.common.utils.httpclient.HttpClientUtils;
import cn.worldwalker.onecard.weixin.constant.Constant;
import cn.worldwalker.onecard.weixin.domain.wxapi.AccessToken;

@Component(value="accessTokenRefreshJob")
public class AccessTokenRefreshJob{
	protected static final Logger log = LoggerFactory.getLogger(AccessTokenRefreshJob.class);
	private static String accessCode = null;
	
	public String doTask() {
		String accessTokenUrl = Constant.getAccessTokenUrl;
		try {
			AccessToken accessToken = JsonUtil.toObject(HttpClientUtils.get(accessTokenUrl), AccessToken.class);
			setAccessCode(accessToken.getAccess_token());
			return accessCode;
		} catch (Exception e) {
			log.error("定时任务刷新access_token异常", e);
		}
		return null;
	}
	
	public static String getAccessCode() {
		return accessCode;
	}
	public static void setAccessCode(String accessCode) {
		AccessTokenRefreshJob.accessCode = accessCode;
	}
	
}
