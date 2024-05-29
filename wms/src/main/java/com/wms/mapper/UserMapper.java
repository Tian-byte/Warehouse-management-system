package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tian
 * @since 2024-05-27
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage PageC(IPage<User> page);

    IPage PageCC(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
