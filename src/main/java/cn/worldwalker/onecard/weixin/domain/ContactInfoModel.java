package cn.worldwalker.onecard.weixin.domain;

public class ContactInfoModel extends BaseModel{
	private Long id;
	private String idNum;
	private String fName;
	private String enName;
	private String grName;
	private String tel;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
