package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.ArticlePojo;

public interface ArticlePojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArticlePojo record);

    int insertSelective(ArticlePojo record);

    ArticlePojo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArticlePojo record);

    int updateByPrimaryKeyWithBLOBs(ArticlePojo record);

    int updateByPrimaryKey(ArticlePojo record);
}