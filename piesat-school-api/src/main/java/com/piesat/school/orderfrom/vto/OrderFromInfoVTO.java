package com.piesat.school.orderfrom.vto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author suweipeng
 * @data 2022/2/25 11:13
 */
@Data
public class OrderFromInfoVTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据审核状态（1.申请下载  2.同意下载 3.拒绝下载）
     */
    private Long dataType;

    /**
     * 下载用户id
     */
    private Long downloadUserId;

    /**
     * 对应数据名称
     */
    private String dataName;


    /**
     * 公开状态（1.完全公开 2.申请获取 3.保护期内）
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createdAt;

    /**
     * 订单说明
     */
    private String explain;
}
