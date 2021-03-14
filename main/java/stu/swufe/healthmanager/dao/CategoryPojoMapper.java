package stu.swufe.healthmanager.dao;

import org.springframework.data.domain.Page;
import stu.swufe.healthmanager.pojo.CategoryPojo;

import java.util.List;
import java.util.Map;

public interface CategoryPojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CategoryPojo record);

    int insertSelective(CategoryPojo record);

    CategoryPojo selectByPrimaryKey(String id);

    int countSelectByName(String name);

    int updateByPrimaryKeySelective(CategoryPojo record);

    int updateByPrimaryKeyWithBLOBs(CategoryPojo record);

    List<CategoryPojo> selectCategoryList(Map<String, Object> map);

    int updateByPrimaryKey(CategoryPojo record);
}