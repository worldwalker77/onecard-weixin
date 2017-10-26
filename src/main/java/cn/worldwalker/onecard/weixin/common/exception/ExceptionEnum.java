package cn.worldwalker.onecard.weixin.common.exception;

public enum ExceptionEnum {
	
	SYSTEM_ERROR(-1, "系统异常"),
    SUCCESS(0, "成功"),
    NEED_LOGIN(1, "需要登录"),
    PARAMS_ERROR(2, "参数为空或者错误"),
    ID_NUM_NOT_MATCH(3, "老身份证号与系统中不符合"),
    TEL_NOT_MATCH(4, "老手机号和系统中不符合"),
    MODIFY_FAIL(5, "修改身份证失败"),
    NOT_BIND(6, "你还未绑定"),
    HAS_BIND_BY_OTHER_PEOPLE(7, "此身份证号已经被别人绑定");

    public int  code;
    public String desc;

    private ExceptionEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
