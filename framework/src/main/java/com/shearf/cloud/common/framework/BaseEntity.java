package com.shearf.cloud.common.framework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description 数据库实体对象基类
 * @since 2019/12/2 20:21
 */
@Setter
@Getter
abstract public class BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 逻辑删除
     */
    @TableLogic(delval = "1", value = "0")
    private Integer del;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
