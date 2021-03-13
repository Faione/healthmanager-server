package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.SportsRecordPojo;

public interface SportsRecordPojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SportsRecordPojo record);

    int insertSelective(SportsRecordPojo record);

    SportsRecordPojo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SportsRecordPojo record);

    int updateByPrimaryKey(SportsRecordPojo record);
}