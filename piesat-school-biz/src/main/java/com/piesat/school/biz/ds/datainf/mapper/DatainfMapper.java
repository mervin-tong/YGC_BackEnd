package com.piesat.school.biz.ds.datainf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.piesat.school.biz.ds.datainf.entity.Datainf;
import com.piesat.school.datainf.param.*;
import com.piesat.school.datainf.vto.DataInfDetailVTO;
import com.piesat.school.datainf.vto.DataInfListVTO;
import com.piesat.school.generationMode.vto.GenerationModeVTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 数据信息表 Mapper 接口
 * </p>
 *
 * @author 周悦尧
 * @since 2022-01-17
 */
public interface DatainfMapper extends BaseMapper<Datainf> {
    List<DataInfListVTO> getAllDatainf(Page<DataInfListVTO> page);
    List<DataInfListVTO> searchByKeyword(@Param("searchByKeyParamData")SearchByKeyParamData searchByKeyParamData,
                                         Page<DataInfListVTO> page);
    List<DataInfListVTO> searchByClass(@Param("searchByClassParamData") SearchByClassParamData searchByClassParamData,
                                           Page<DataInfListVTO> page);
    List<DataInfListVTO> searchByTime(@Param("searchByTimeParamData")SearchByTimeParamData searchByTimeParamData,
                                      Page<DataInfListVTO> page);
    List<DataInfListVTO> searchAll(@Param("searchAllParamData")SearchAllParamData searchAllParamData,Page<DataInfListVTO> page);
    DataInfDetailVTO dataInfDetail(@Param("dataInfId") Long dataInfId);

    List<DataInfListVTO> getThematicData(@Param("param") MetadataQueryParam paramData, Page<DataInfListVTO> page);

    List<DataInfDetailVTO> menuDataList(@Param("param")  MenuDataParam param, Page<DataInfListVTO> page);

    List<DataInfDetailVTO> menuDataListDetail(@Param("param") MenuDataParam param, Page<DataInfDetailVTO> page,@Param("orderStr") String orderStr);

    List<GenerationModeVTO> getGenerationModeDetail();

//    Page<DataInfDetailVTO> menuDataListDetail(@Param("param") MenuDataParam param, Page<DataInfDetailVTO> page);

//    List<MyDataInfVTO> myDataMenu(@Param("userId") Long userId, Page<MyDataInfVTO> page);

//    Boolean delDataInf(List<Long> longs);
}
