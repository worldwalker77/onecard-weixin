package cn.worldwalker.onecard.weixin.constant;

import cn.worldwalker.onecard.weixin.common.utils.CustomizedPropertyConfigurer;

public class Constant {
	/***微信appid,appsecrect**/
	public final static String APPID = CustomizedPropertyConfigurer.getContextProperty("APPID");// 应用号
	public final static String APP_SECRECT = CustomizedPropertyConfigurer.getContextProperty("APP_SECRECT");// 应用密码
	
	public final static String templateId = "g_cjIPJRupCoNNj1bQcNNxczSgVJDmn9t691qtscl0Q";
	/**微信登录相关*/
	public static String getWXUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + "ACCESS_TOKEN&openid=OPENID";
	/**获取token*/
	public static String getOpenidAndAccessCode = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APPID + "&secret=" + APP_SECRECT + "&grant_type=authorization_code&code=CODE";
	/**发送模板消息*/
	public static String sendTemplateMsgUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	public static String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APP_SECRECT;
}
