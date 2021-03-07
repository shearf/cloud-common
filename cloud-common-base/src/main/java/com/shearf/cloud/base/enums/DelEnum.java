package com.shearf.cloud.base.enums;

/**
 * 是否删除
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2020/5/28 15:47
 */
public enum DelEnum {

    /**
     * 已删除
     */
    YES(1),

    /**
     * 未删除
     */
    NO(0),

    ;

    private final int del;

    DelEnum(int del) {
        this.del = del;
    }

    public int getDel() {
        return del;
    }
}
