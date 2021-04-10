package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.ArticlePojo;
import stu.swufe.healthmanager.pojo.CommentPojo;
import stu.swufe.healthmanager.pojo.CommentPojoWithBLOBs;

import java.util.List;
import java.util.Map;

public interface CommentPojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommentPojoWithBLOBs record);

    int insertSelective(CommentPojoWithBLOBs record);

    CommentPojoWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommentPojoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CommentPojoWithBLOBs record);

    int updateByPrimaryKey(CommentPojo record);

    List<CommentPojoWithBLOBs> selectCommentsByAriticle(Map<String, Object> params);
}