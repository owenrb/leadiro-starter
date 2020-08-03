package com.leadiro.starter.service.starter.dao

import com.leadiro.starter.service.starter.dto.Starter
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

/**
 * An example Mapper that makes a query to get a Starter DTO
 */
@Mapper
@Repository
interface StarterMapper {

    @Select('''
        SELECT * 
        FROM starter
        WHERE id = #{starterId}
    ''')
    Starter getStarter(@Param("starterId") int starterId)

}
