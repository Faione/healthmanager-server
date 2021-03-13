package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.HealthRecordPojo;

public interface HealthRecordPojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(HealthRecordPojo record);

    int insertSelective(HealthRecordPojo record);

    HealthRecordPojo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HealthRecordPojo record);

    int updateByPrimaryKey(HealthRecordPojo record);
}