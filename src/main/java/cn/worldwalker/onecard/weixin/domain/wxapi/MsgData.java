package cn.worldwalker.onecard.weixin.domain.wxapi;

public class MsgData {
	private MsgItem first = new MsgItem();
	private MsgItem keyword1 = new MsgItem();
	private MsgItem keyword2 = new MsgItem();
	private MsgItem keyword3 = new MsgItem();
	private MsgItem keyword4 = new MsgItem();
	private MsgItem keyword5 = new MsgItem();
	private MsgItem remark = new MsgItem();
	public MsgItem getFirst() {
		return first;
	}
	public void setFirst(MsgItem first) {
		this.first = first;
	}
	public MsgItem getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(MsgItem keyword1) {
		this.keyword1 = keyword1;
	}
	public MsgItem getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(MsgItem keyword2) {
		this.keyword2 = keyword2;
	}
	public MsgItem getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(MsgItem keyword3) {
		this.keyword3 = keyword3;
	}
	public MsgItem getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(MsgItem keyword4) {
		this.keyword4 = keyword4;
	}
	public MsgItem getKeyword5() {
		return keyword5;
	}
	public void setKeyword5(MsgItem keyword5) {
		this.keyword5 = keyword5;
	}
	public MsgItem getRemark() {
		return remark;
	}
	public void setRemark(MsgItem remark) {
		this.remark = remark;
	}
	
}
