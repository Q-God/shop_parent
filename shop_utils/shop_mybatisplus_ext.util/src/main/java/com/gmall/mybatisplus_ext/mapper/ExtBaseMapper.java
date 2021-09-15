package com.gmall.mybatisplus_ext.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface ExtBaseMapper<T> extends BaseMapper<T> {

    int insertBatchSomeColumn(@Param("list") Collection<T> entityList);

}
