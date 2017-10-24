package cn.worldwalker.onecard.weixin.domain.wxapi;

public class TemplateMsg {
	private String touser;
	private String template_id;
	private String url;
	private MsgData data = new MsgData();
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public MsgData getData() {
		return data;
	}
	public void setData(MsgData data) {
		this.data = data;
	}
	
}
