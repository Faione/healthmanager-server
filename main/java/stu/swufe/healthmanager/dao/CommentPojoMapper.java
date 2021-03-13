package stu.swufe.healthmanager.dao;

import stu.swufe.healthmanager.pojo.CommentPojo;
import stu.swufe.healthmanager.pojo.CommentPojoWithBLOBs;

public interface CommentPojoMapper {
    int deleteByPrimaryKey(String id);

    int insert(CommentPojoWithBLOBs record);

    int insertSelective(CommentPojoWithBLOBs record);

    CommentPojoWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CommentPojoWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CommentPojoWithBLOBs record);

    int updateByPrimaryKey(CommentPojo record);
}