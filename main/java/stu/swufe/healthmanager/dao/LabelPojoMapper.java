package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.LabelPojo;

public interface LabelPojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(LabelPojo record);

    int insertSelective(LabelPojo record);

    LabelPojo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LabelPojo record);

    int updateByPrimaryKey(LabelPojo record);
}