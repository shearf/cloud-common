package com.shearf.cloud.common.framework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库实体对象基类
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2019/12/2 20:21
 */
abstract public class BasePO implements Serializable {

    private static final long serialVersionUID = 4417108425573551635L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    protected Long id;

    /**
     * 逻辑删除
     */
    @TableLogic(delval = "1", value = "0")
    protected Integer del;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
