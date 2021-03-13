package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.UserPojo;

public interface UserPojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserPojo record);

    int insertSelective(UserPojo record);

    UserPojo selectByPrimaryKey(String id);

    int selectByName(String username);

    UserPojo selectAllByName(String username);

    int updateByPrimaryKeySelective(UserPojo record);

    int updateByPrimaryKey(UserPojo record);

    UserPojo selectUserInfoByPrimaryKey(String userid);
}