package com.piesat.school.rest.controller.app.orderFrom;

import com.piesat.school.datainf.vto.DataInfVTO;
import com.piesat.school.orderfrom.iservice.IROrderFromService;
import com.piesat.school.orderfrom.param.OrderFromMenuPageParamData;
import com.piesat.school.orderfrom.param.OrderFromParamData;
import com.piesat.school.orderfrom.vto.OrderFromAttentionVTO;
import com.piesat.school.orderfrom.vto.OrderFromInfoVTO;
import com.piesat.school.orderfrom.vto.OrderFromVTO;
import com.smartwork.api.Result;
import com.smartwork.api.support.page.TailPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author suweipeng
 * @data 2022/2/24 10:18
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/app/orderfrom")
public class OrderFromController {
    @DubboReference
    private IROrderFromService irOrderFromService;
    @ApiOperation(value = "获取订单列表")
    @PostMapping("/menu")
    public Result<TailPage<OrderFromVTO>> orderFromMenu(@RequestBody OrderFromMenuPageParamData orderFromMenuPageParamData){
        return irOrderFromService.orderFromMenu(orderFromMenuPageParamData);
    }
    @ApiOperation(value = "新增订单")
    @PostMapping("/create")
    public Result<OrderFromVTO> orderFromCreate(@RequestBody OrderFromParamData orderFromParamData){
        return irOrderFromService.orderFromCreate(orderFromParamData);
    }
    @ApiOperation(value = "订单详情")
    @GetMapping("/info")
    public Result<OrderFromInfoVTO> orderFromInfo(Long orderFromId){
        return irOrderFromService.orderFromInfo(orderFromId);
    }
//    @ApiOperation(value = "关注列表")
//    @GetMapping("/attentionList")
//    public Result<List<OrderFromAttentionVTO>> attentionList(Long userId){
//        return irOrderFromService.attentionList(userId);
//    }


}