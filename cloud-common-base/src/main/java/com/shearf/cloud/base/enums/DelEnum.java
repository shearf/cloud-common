package com.shearf.cloud.base.enums;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
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

    private int del;

    DelEnum(int del) {
        this.del = del;
    }

    public int getDel() {
        return del;
    }
}
