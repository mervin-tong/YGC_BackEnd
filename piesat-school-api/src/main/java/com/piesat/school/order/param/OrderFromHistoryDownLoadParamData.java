package com.piesat.school.order.param;

import com.piesat.school.base.PageQueryParamData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author suweipeng
 * @data 2022/3/8 13:58
 */
@Data
public class OrderFromHistoryDownLoadParamData extends PageQueryParamData {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long historyUserId;

}
