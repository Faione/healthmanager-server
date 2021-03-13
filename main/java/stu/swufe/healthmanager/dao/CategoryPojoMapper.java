package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.CategoryPojo;

public interface CategoryPojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CategoryPojo record);

    int insertSelective(CategoryPojo record);

    CategoryPojo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CategoryPojo record);

    int updateByPrimaryKeyWithBLOBs(CategoryPojo record);

    int updateByPrimaryKey(CategoryPojo record);
}