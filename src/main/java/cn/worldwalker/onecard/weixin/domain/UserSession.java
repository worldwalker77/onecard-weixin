package cn.worldwalker.onecard.weixin.domain;

import java.io.Serializable;

public class UserSession implements Serializable{
	private static final long serialVersionUID = 7478917352637885504L;
	private String openId;
	private String mobilePhone;
	private String idNum;
	private String fName;
	private String enName;
	private String grName;
	
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
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getGrName() {
		return grName;
	}
	public void setGrName(String grName) {
		this.grName = grName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
