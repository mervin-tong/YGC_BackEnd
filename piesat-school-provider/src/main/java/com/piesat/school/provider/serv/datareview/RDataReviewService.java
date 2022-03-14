package com.piesat.school.provider.serv.datareview;

import com.piesat.school.biz.ds.datareview.service.IDataReviewService;
import com.piesat.school.datareview.iservice.IRDataReviewService;
import com.piesat.school.datareview.param.DataReviewParamData;
import com.piesat.school.datareview.vto.DataReviewVTO;
import com.smartwork.api.Result;
import com.smartwork.api.support.page.TailPage;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author suweipeng
 * @data 2022/3/8 15:33
 */
@DubboService
public class RDataReviewService implements IRDataReviewService {
    @Autowired
    private IDataReviewService iDataReviewService;

    @Override
    public Result<TailPage<DataReviewVTO>> dataReview(DataReviewParamData dataReviewParamData) {
        return Result.ofSuccess(iDataReviewService.dataReview(dataReviewParamData));
    }

    @Override
    public Result<Boolean> firstReview(Long dataReviewId, Long reviewUserId) {
        if (dataReviewId == null || reviewUserId == null){
            return Result.ofFail("4401","参数不能为空");
        }
        return Result.ofSuccess(iDataReviewService.firstReview(dataReviewId,reviewUserId));
    }

    @Override
    public Result<Boolean> assign(Long dataReviewId, Long expertId) {
        if (dataReviewId == null || expertId == null){
            return Result.ofFail("4401","参数不能为空");
        }
        return Result.ofSuccess(iDataReviewService.assign(dataReviewId,expertId));
    }
}
