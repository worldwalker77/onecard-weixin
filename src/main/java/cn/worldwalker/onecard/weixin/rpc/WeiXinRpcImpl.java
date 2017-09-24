package cn.worldwalker.onecard.weixin.rpc;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import cn.worldwalker.onecard.weixin.common.utils.httpclient.HttpClientUtils;
import cn.worldwalker.onecard.weixin.constant.Constant;

@Service
public class WeiXinRpcImpl implements WeiXinRpc{
	
	@Override
	public String getOpenId(String code) {
		String url = Constant.getOpenidAndAccessCode;
		url = url.replace("CODE", code);
		JSONObject obj = null;
		try {
			String str = HttpClientUtils.get(url);
			obj = JSONObject.fromObject(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (obj != null && obj.containsKey("errcode"))
			return null;
		else{
			String openid = obj.getString("openid");
			return openid;
		}
	}

}
