package com.shearf.cloud.common.framework;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

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

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableLogic(delval = "1", value = "0")
    private Integer del;

    private Date createTime;

    private Date updateTime;
}
