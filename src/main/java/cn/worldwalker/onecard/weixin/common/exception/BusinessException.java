package cn.worldwalker.onecard.weixin.common.exception;


/**
 * 异常父类，所有业务异常继承该类
 *
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 6511369321028049305L;
    public Integer          code;
    public String          desc;
    
    public BusinessException(String desc) {
        this.desc = desc;
    }

    public BusinessException(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    public BusinessException(ExceptionEnum exceptionEnum) {
        if(exceptionEnum!=null){
            this.code = exceptionEnum.code;
            this.desc = exceptionEnum.desc;
        }
    }
    

}
