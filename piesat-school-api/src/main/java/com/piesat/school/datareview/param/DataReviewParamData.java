package com.piesat.school.datareview.param;

import com.piesat.school.base.PageQueryParamData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author suweipeng
 * @data 2022/3/8 15:37
 */
@Data
public class DataReviewParamData extends PageQueryParamData {

    /**
     * 数据名称
     */
    @ApiModelProperty(value = "数据名称")
    private String dataName;

    /**
     * 提交时间
     */
    @ApiModelProperty(value = "提交时间")
    private Date createdAt;

    /**
     * 评审状态
     */
    @ApiModelProperty(value = "评审状态")
    private String status;
}
