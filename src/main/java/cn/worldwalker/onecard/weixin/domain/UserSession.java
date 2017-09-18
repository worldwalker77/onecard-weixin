package cn.worldwalker.onecard.weixin.domain;

import java.io.Serializable;

public class UserSession implements Serializable{
	private static final long serialVersionUID = 7478917352637885504L;
	private String openId;
	private String mobilePhone;
	private String idNum;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
}
