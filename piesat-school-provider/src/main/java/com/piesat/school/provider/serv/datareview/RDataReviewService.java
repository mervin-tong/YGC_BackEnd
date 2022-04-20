package com.piesat.school.provider.serv.datareview;

import com.piesat.school.biz.ds.datareview.service.IDataReviewService;
import com.piesat.school.datareview.iservice.IRDataReviewService;
import com.piesat.school.datareview.param.ConditionScreenParamData;
import com.piesat.school.datareview.param.DataReviewParamData;
import com.piesat.school.datareview.param.UserDataReviewParamData;
import com.piesat.school.datareview.vto.DataReviewUserVTO;
import com.piesat.school.datareview.vto.DataReviewVTO;
import com.smartwork.api.Result;
import com.smartwork.api.support.page.TailPage;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    public Result<Boolean> firstReview(Long dataReviewId, Long reviewUserId, Integer isPass, String reason) {
        return Result.ofSuccess(iDataReviewService.firstReview(dataReviewId,reviewUserId,isPass,reason));
    }

    @Override
    public Result<Boolean> assign(Long dataReviewId, Long expertId,Long reviewUserId) {
        return Result.ofSuccess(iDataReviewService.assign(dataReviewId,expertId,reviewUserId));
    }

    @Override
    public Result<TailPage<DataReviewUserVTO>> userDataReview(UserDataReviewParamData userDataReviewParamData) {
        return Result.ofSuccess(iDataReviewService.userDataReview(userDataReviewParamData));
    }

    @Override
    public Result<Boolean> userReview(Long dataId, Long reviewUserId, int status, String reason) {
        return Result.ofSuccess(iDataReviewService.userReview(dataId,reviewUserId,status,reason));
    }

    @Override
    public Result<Boolean> recheck(Long reviewUserId, int dataId) {
        return Result.ofSuccess(iDataReviewService.recheck(reviewUserId,dataId));
    }

    @Override
    public Result<List<String>> screen(ConditionScreenParamData paramData) {
        return Result.ofSuccess(iDataReviewService.screen(paramData));
    }

    @Override
    public Result<Boolean> checkInOrOut(Long userId, List<Long> dataList, Integer checkStatus) {
        return Result.ofSuccess(iDataReviewService.checkInOrOut(userId,dataList,checkStatus));
    }
}
