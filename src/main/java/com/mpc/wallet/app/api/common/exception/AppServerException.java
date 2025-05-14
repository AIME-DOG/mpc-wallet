package com.mpc.wallet.app.api.common.exception;


import com.mpc.wallet.app.api.common.enums.AppCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppServerException extends RuntimeException {

    Object[] param;
    private Integer code = 400;

    public AppServerException(Integer code , String message) {
        super(message);
        this.code = code;
    }

    public AppServerException(AppCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public AppServerException(Integer code , String message , Object... params) {
        super(message);
        this.code = code;
        this.param = params;
    }

    public AppServerException(String message) {
        super(message);
        this.code = 400;
    }


}
