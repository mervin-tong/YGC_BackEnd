package com.piesat.school.rest.controller.app.topic;

import com.piesat.school.datainf.vto.MyDataInfVTO;
import com.piesat.school.topic.iservice.IRTopicService;
import com.piesat.school.topic.param.*;
import com.piesat.school.topic.vto.TopicVTO;
import com.smartwork.api.Result;
import com.smartwork.api.support.page.TailPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "专题数据模块")
@RestController
@RequestMapping("/app/topic")
public class TopicController {
    @DubboReference
    private IRTopicService irTopicService;
    @ApiOperation(value = "新增/修改数据专题")
    @PostMapping("/saveOrUpdate")
    public Result<TopicVTO> saveOrUpdate(TopicSaveParamData topicSaveParamData) {
        return irTopicService.saveOrUpdate(topicSaveParamData);
    }

    @ApiOperation(value = "删除专题")
    @PostMapping("/del")
    public Result<Boolean> del(TopicDelParamData paramData) {
        return irTopicService.del(paramData);
    }

    @ApiOperation(value = "添加专题数据")
    @PostMapping("/addTopicData")
    public Result<Boolean> addTopicData( TopicDataAddParamData paramData){
        return irTopicService.addTopicData(paramData);
    }

    @ApiOperation(value = "删除专题数据")
    @PostMapping("/delTopicData")
    public Result<Boolean> delTopicData( TopicDataDelParamData paramData){
        return irTopicService.delTopicData(paramData);
    }
    @ApiOperation(value = "专题列表")
    @PostMapping("/list")
    public Result<List<TopicVTO>> topicList(TopicQueryParamData paramData){
        return irTopicService.list(paramData);
    }

    @ApiOperation(value = "专题数据列表")
    @PostMapping("/topicDatalist")
    public Result<TailPage<MyDataInfVTO>> topicDatalist(TopicQueryParamData paramData){
        return irTopicService.topicDatalist(paramData);
    }
    @ApiOperation(value = "专题详情")
    @PostMapping("/detail")
    public Result<TopicVTO> detail(TopicQueryParamData paramData){
        return irTopicService.detail(paramData);
    }


}
