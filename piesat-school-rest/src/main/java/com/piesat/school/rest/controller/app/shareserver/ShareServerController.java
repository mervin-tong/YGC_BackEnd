package com.piesat.school.rest.controller.app.shareserver;

import com.piesat.school.datainf.iservice.IRDataShareInfService;
import com.piesat.school.datainf.param.AuditApplyListParamData;
import com.piesat.school.datainf.param.DataShareParamData;
import com.piesat.school.datainf.param.SystemEmailParamData;
import com.piesat.school.datainf.vto.AuditApplyListVTO;
import com.piesat.school.datainf.vto.ShareInfVTO;
import com.piesat.school.datainf.vto.SystemEmailVTO;
import com.piesat.school.rest.aspect.AroundRecord;
import com.smartwork.api.Result;
import com.smartwork.api.support.page.TailPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "api服务模块")
@RestController
@RequestMapping("/app/share")
public class ShareServerController {

    @DubboReference
    private IRDataShareInfService dataInfService;
    @AroundRecord
    @ApiOperation(value = "api服务申请列表")
    @PostMapping("/dataList")
//    无需参数
    public Result<TailPage<ShareInfVTO>> datalist(DataShareParamData paramData){
//        TailPage<ShareInfVTO> shareInfVTOS=dataInfService.datalist(paramData);
        return Result.ofSuccess(dataInfService.datalist(paramData));
    }
    @ApiModelProperty(value = "申请Key")
    @PostMapping("/applyForKey")
//    传入applyExplain、applyId
    public Result<ShareInfVTO> applyForKey(DataShareParamData paramData){
        return dataInfService.applyForKey(paramData);
    }

    @ApiModelProperty(value = "查询申请状态")
    @PostMapping("/checkStatus")
//    传入参数applyId，查询最新的一条申请的状态
    public Result<ShareInfVTO> checkStatus(DataShareParamData dataShareParamData){
        return dataInfService.checkStatus(dataShareParamData);
    }

    @ApiModelProperty(value = "apiKey转url")
    @PostMapping("/keyToUrl")
//    传入apiKey,返回加密并拼接好的url
    public Result<String> keyToUrl(DataShareParamData dataShareParamData,HttpServletRequest request){
        String URL;
        URL=request.getRequestURL().toString()+"/"+dataInfService.random(dataShareParamData.getApiKey());
        return Result.ofSuccess(URL);
    }
    @ApiModelProperty(value = "申请审核信息列表")
    @PostMapping("/auditApplyList")
//    无需参数,列表
    public TailPage<AuditApplyListVTO> auditApplyList(AuditApplyListParamData auditApplyListParamData){
        return dataInfService.auditApplyList(auditApplyListParamData);
    }
    @ApiModelProperty(value = "API服务申请详情")
    @PostMapping("/detail")
//    传入参数申请表中的id，返回审核状态，用途说明，不通过原因，更新时间，applyId,
    public ShareInfVTO detail(DataShareParamData dataShareParamData){
        return dataInfService.detail(dataShareParamData);
    }
    @ApiModelProperty(value = "审核是否通过")
    @PostMapping("/pass")
//    传入参数申请表中的id,email不通过的原因mark，传入mark代表不通过，通过后发送key到传入的email
    public Result<ShareInfVTO> pass(DataShareParamData dataShareParamData){
       return dataInfService.pass(dataShareParamData);

    }
//    @ApiModelProperty(value = "发送key到邮箱")
//    @PostMapping("sendKey")
////    传入参数邮箱地址email
//    public Result<String> sendKey(DataShareParamData dataShareParamData){
//        return dataInfService.sendKey(dataShareParamData);
//    }
    @ApiModelProperty(value = "显示系统设置的邮箱")
    @GetMapping("seeEmail")
    public SystemEmailVTO seeEmail(){
        return dataInfService.seeEmail();
    }
    @ApiModelProperty(value = "设置邮箱")
    @PostMapping("setEmail")
//    传入邮箱地址和动态码,设置邮箱
    public Result<SystemEmailVTO> setEmail(SystemEmailParamData systemEmailParamData){
        return dataInfService.setEmail(systemEmailParamData);
    }


}