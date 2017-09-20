package cn.worldwalker.onecard.weixin.common.exception;

public enum ExceptionEnum {
	
	SYSTEM_ERROR(-1, "系统异常"),
    SUCCESS(0, "成功"),
    NEED_LOGIN(1, "需要登录"),
    PARAMS_ERROR(2, "参数为空或者错误"),
    ID_NUM_NOT_EXIST(3, "系统中没有此身份证号");

    public int  code;
    public String desc;

    private ExceptionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
