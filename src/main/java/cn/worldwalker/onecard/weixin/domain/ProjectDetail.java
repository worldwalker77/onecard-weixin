package cn.worldwalker.onecard.weixin.domain;
public class ProjectDetail extends BaseEntity {
	
	private String detailCode;
	private String detailName;
	private String proCode;
	private String proName;
	private String fieldName;
	private Integer isEnabled;
	private String rgCode;
	private String setYear;
	private String createUser;
	private String createDate;
	private String latestOpUser;
	private String latestOpDate;
	private String formulaCode;
	private String formulaName;
	private Integer isCalculate;
	private Integer formulaIndex;
	
	public ProjectDetail(String fieldName, String rgCode, String proCode){
		this.fieldName = fieldName;
		this.rgCode = rgCode;
		this.proCode = proCode;
	}
	
	public ProjectDetail(){
	}

	public String getDetailCode() {
		return detailCode;
	}

	public void setDetailCode(String detailCode) {
		this.detailCode = detailCode;
	}

	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRgCode() {
		return rgCode;
	}

	public void setRgCode(String rgCode) {
		this.rgCode = rgCode;
	}

	public String getSetYear() {
		return setYear;
	}

	public void setSetYear(String setYear) {
		this.setYear = setYear;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getLatestOpUser() {
		return latestOpUser;
	}

	public void setLatestOpUser(String latestOpUser) {
		this.latestOpUser = latestOpUser;
	}

	public String getLatestOpDate() {
		return latestOpDate;
	}

	public void setLatestOpDate(String latestOpDate) {
		this.latestOpDate = latestOpDate;
	}

	public String getFormulaCode() {
		return formulaCode;
	}

	public void setFormulaCode(String formulaCode) {
		this.formulaCode = formulaCode;
	}

	public String getFormulaName() {
		return formulaName;
	}

	public void setFormulaName(String formulaName) {
		this.formulaName = formulaName;
	}

	public Integer getIsCalculate() {
		return isCalculate;
	}

	public void setIsCalculate(Integer isCalculate) {
		this.isCalculate = isCalculate;
	}

	public Integer getFormulaIndex() {
		return formulaIndex;
	}

	public void setFormulaIndex(Integer formulaIndex) {
		this.formulaIndex = formulaIndex;
	}

}