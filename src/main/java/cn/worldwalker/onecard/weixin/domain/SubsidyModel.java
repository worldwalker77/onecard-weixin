package cn.worldwalker.onecard.weixin.domain;

import java.util.Date;

public class SubsidyModel extends BaseModel{
	private String idNum;
	private String fName;
	private String enName;
	private String grName;
	private String proName;
	private Date grantDate;
	private Integer bankStatus;
	
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
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public Date getGrantDate() {
		return grantDate;
	}
	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}
	public Integer getBankStatus() {
		return bankStatus;
	}
	public void setBankStatus(Integer bankStatus) {
		this.bankStatus = bankStatus;
	}
	
}
