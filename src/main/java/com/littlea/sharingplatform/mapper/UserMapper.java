package com.littlea.sharingplatform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.littlea.sharingplatform.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 功能描述
 * @author chenqiting
 * @date 2020/1/14 11:17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}