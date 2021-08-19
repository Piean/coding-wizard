package com.piean.idea.plugin.coding.error;

/**
 * @author <a href="mailto:yds4744@163.com">Piean</a>
 * @since 2020/9/9
 */
public enum HintMsg {
    NEED_PROJECT("Need a project"),
    NEED_LOCAL_VARIABLE("Please use it on instance variables"),
    NEED_NAME_IDENTIFIER("Need name identifier"),
    ;
    private final String msg;

    HintMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
