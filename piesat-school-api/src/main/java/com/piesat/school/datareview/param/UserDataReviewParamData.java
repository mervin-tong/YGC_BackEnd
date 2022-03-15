package com.piesat.school.datareview.param;

import com.smartwork.api.param.PageQueryParamData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author suweipeng
 * @data 2022/3/8 15:37
 */
@Data
public class UserDataReviewParamData extends PageQueryParamData {
    @ApiModelProperty(value = "用户id")
    private Long userJudgeId;

}
