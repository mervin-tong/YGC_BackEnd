package com.piesat.school.datainf.vto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "审核申请列表模型")
@Data
public class AuditApplyListVTO implements Serializable{

    @ApiModelProperty(value = "申请人名字")
    private String name;
    @ApiModelProperty(value = "不通过原因")
    private String mark;
    @ApiModelProperty(value = "申请状态 0未通过，1已通过，2待审核")
    private Integer applyStatus;
    @ApiModelProperty(value = "用户表的id")
    private Long id;
    @ApiModelProperty(value = "申请表的id")
    private Long bid;
    @ApiModelProperty(value = "工作单位")
    private String workUnit;
    @ApiModelProperty(value = "申请时间")
    @JsonFormat(timezone="GMT+8")
    private Date createdAt;
    @ApiModelProperty(value = "更新后的时间")
    @JsonFormat(timezone="GMT+8")
    private Date updatedAt;
    @ApiModelProperty(value = "手机号")
    private Long phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "从事专业")
    private String profession;
    @ApiModelProperty(value = "单位地址")
    private String unitAddress;
    @ApiModelProperty(value = "最高学历")
    private String highEducation;
    @ApiModelProperty(value = "用途说明")
    private String applyExplain;

    @ApiModelProperty(value = "签入者")
    private String checkMan;
    @ApiModelProperty(value = "签入者id")
    private Long checkManId;

}
