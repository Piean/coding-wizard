package com.piean.idea.plugin.coding.constants;

/**
 * @author <a href="mailto:dishuang.yang@yunhuyj.com">mogu</a>
 * @since 2020/9/29
 */
public enum EnableFunctionBit {
    ALL_SETTER(0),
    BEAN_COPY(1),
    ;

    private final int bit;

    EnableFunctionBit(int bit) {
        this.bit = bit;
    }

    public boolean isEnable(int state) {
        int flag = 1 << this.bit;
        return (state & flag) == flag;
    }

    public int getFlag() {
        return 1 << this.bit;
    }
}
