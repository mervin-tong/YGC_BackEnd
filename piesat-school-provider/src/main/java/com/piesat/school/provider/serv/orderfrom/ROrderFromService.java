package com.piesat.school.provider.serv.orderfrom;

import com.piesat.school.biz.ds.orderfrom.service.IOrderFromService;
import com.piesat.school.orderfrom.iservice.IROrderFromService;
import com.piesat.school.orderfrom.param.OrderFromAttentionParamData;
import com.piesat.school.orderfrom.param.OrderFromMenuPageParamData;
import com.piesat.school.orderfrom.param.OrderFromParamData;
import com.piesat.school.orderfrom.vto.OrderFromAttentionVTO;
import com.piesat.school.orderfrom.vto.OrderFromInfoVTO;
import com.piesat.school.orderfrom.vto.OrderFromVTO;
import com.piesat.school.user.iservice.IRUserService;
import com.smartwork.api.Result;
import com.smartwork.api.support.page.TailPage;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author suweipeng
 * @data 2022/2/24 10:47
 */
@DubboService(interfaceClass = IROrderFromService.class)
public class ROrderFromService implements IROrderFromService{

    @Autowired
    private IOrderFromService iOrderFromService;
    //获取订单列表
    @Override
    public Result<TailPage<OrderFromVTO>> orderFromMenu(OrderFromMenuPageParamData orderFromMenuPageParamData) {
        return Result.ofSuccess(iOrderFromService.orderFromMenu(orderFromMenuPageParamData));
    }
    //创建订单
    @Override
    public Result<OrderFromVTO> orderFromCreate(OrderFromParamData orderFromParamData) {
        return Result.ofSuccess(iOrderFromService.orderFromCreate(orderFromParamData));
    }
    //订单详情
    @Override
    public Result<OrderFromInfoVTO> orderFromInfo(Long orderFromId) {
        return Result.ofSuccess(iOrderFromService.orderFromInfo(orderFromId));
    }
    //关注列表
    @Override
    public Result<TailPage<OrderFromAttentionVTO>> attentionList(OrderFromAttentionParamData orderFromAttentionParamData) {
        return Result.ofSuccess(iOrderFromService.attentionList(orderFromAttentionParamData));
    }
}
