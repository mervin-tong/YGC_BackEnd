package com.piesat.school.dataclass.param;

import com.smartwork.api.param.UIDParamData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class DataClassSaveParamData extends UIDParamData {

    @ApiModelProperty(value = "第一类别")
    @NotBlank(message = "第一类别不能为空")
    private String firstClass;

    @ApiModelProperty(value = "第二类别")
    private String secClass;
}