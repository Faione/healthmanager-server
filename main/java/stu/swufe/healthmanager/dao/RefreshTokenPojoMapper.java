package stu.swufe.healthmanager.dao;

import org.apache.ibatis.annotations.Param;
import stu.swufe.healthmanager.pojo.RefreshTokenPojo;

public interface RefreshTokenPojoMapper {
    int deleteByPrimaryKey(String id);

    int deleteByUserId(String userId);

    int insert(RefreshTokenPojo record);

    int insertSelective(RefreshTokenPojo record);

    RefreshTokenPojo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RefreshTokenPojo record);

    int updateByPrimaryKeyWithBLOBs(RefreshTokenPojo record);

    int updateByPrimaryKey(RefreshTokenPojo record);

    int updateByTokenKey(@Param("tokenKey")String tokenKey, @Param("dirt") boolean dirt);

    RefreshTokenPojo selectByTokenKey(String tokenKey);
}