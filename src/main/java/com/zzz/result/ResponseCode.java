package com.zzz.result;

public enum ResponseCode {

    // 公共请求信息
    SUCCESS(200, "请求成功"),
    TABLE_SUCCESS(0, "请求成功"),

    FAIL(500, "请求失败，请联系管理员"),

    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401,"未授权, 请登录"),
    FORBIDDEN(403, "超出权限"),

    UNASSORTED(-1, "未分类的错误"),
    ;
    private Integer code;

    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getMessage(String name) {
        for (ResponseCode item : ResponseCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        for (ResponseCode item : ResponseCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }
}
