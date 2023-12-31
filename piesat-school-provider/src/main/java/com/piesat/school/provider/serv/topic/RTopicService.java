package com.piesat.school.provider.serv.topic;

import com.piesat.school.biz.ds.topic.service.ITopicService;
import com.piesat.school.datainf.vto.MyDataInfVTO;
import com.piesat.school.topic.iservice.IRTopicService;
import com.piesat.school.topic.param.*;
import com.piesat.school.topic.vto.TopicDetailVTO;
import com.piesat.school.topic.vto.TopicVTO;
import com.smartwork.api.Result;
import com.smartwork.api.support.page.TailPage;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

@DubboService
public class RTopicService implements IRTopicService {
    @Resource
    ITopicService iTopicService;
    @Override
    public Result<TopicVTO> saveOrUpdate(TopicSaveParamData topicSaveParamData) {
        return Result.ofSuccess(iTopicService.saveOrUpdate(topicSaveParamData));
    }

    @Override
    public Result<Boolean> addTopicData(TopicDataAddParamData paramData) {
        return Result.ofSuccess(iTopicService.addTopicData(paramData));
    }

    @Override
    public Result<List<TopicDetailVTO>> detailTopic(Long topicId) {
        return Result.ofSuccess(iTopicService.detailTopic(topicId));
    }


    @Override
    public Result<TopicVTO> indexDetailTopic(Long topicId) {
        return Result.ofSuccess(iTopicService.indexDetailTopic(topicId));
    }

    @Override
    public Result<Boolean> del(TopicDelParamData paramData) {
        return Result.ofSuccess(iTopicService.del(paramData));
    }

    @Override
    public Result<Boolean> delTopicData(TopicDataDelParamData paramData) {
        return Result.ofSuccess(iTopicService.delTopicData(paramData));
    }

    @Override
    public Result<List<TopicVTO>> list(TopicQueryParamData paramData) {
        return Result.ofSuccess(iTopicService.topicPage(paramData));
    }

    @Override
    public Result<TailPage<MyDataInfVTO>> topicDatalist(TopicQueryParamData paramData) {
        return Result.ofSuccess(iTopicService.topicDatalist(paramData));
    }

    @Override
    public Result<TopicVTO> detail(TopicQueryParamData paramData) {
        return Result.ofSuccess(iTopicService.detail(paramData));
    }
}
