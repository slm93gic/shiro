package com.slm.mapper;

import com.slm.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {

    public User queryUserByName(String name);

}
