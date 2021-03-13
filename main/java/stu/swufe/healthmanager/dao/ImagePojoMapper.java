package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.ImagePojo;

public interface ImagePojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ImagePojo record);

    int insertSelective(ImagePojo record);

    ImagePojo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImagePojo record);

    int updateByPrimaryKey(ImagePojo record);
}