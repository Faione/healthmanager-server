package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.ArticlePojo;

import java.util.List;
import java.util.Map;

public interface ArticlePojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArticlePojo record);

    int insertSelective(ArticlePojo record);

    ArticlePojo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArticlePojo record);

    int updateByPrimaryKeyWithBLOBs(ArticlePojo record);

    int updateByPrimaryKey(ArticlePojo record);

    List<ArticlePojo> selectArticlesByPage(Map<String, Object> params);
}