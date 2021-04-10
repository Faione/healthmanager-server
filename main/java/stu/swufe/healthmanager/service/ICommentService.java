package stu.swufe.healthmanager.service;

import stu.swufe.healthmanager.pojo.CommentPojoWithBLOBs;
import stu.swufe.healthmanager.response.ResponseResult;

public interface ICommentService {
    /**
     * 上传用户评论（要求token）
     * @param commentPojoWithBLOBs
     * @param tokenKeyBuffer
     * @return
     */
    ResponseResult uploadComment(CommentPojoWithBLOBs commentPojoWithBLOBs, StringBuffer tokenKeyBuffer);

    /**
     * 查询回复列表
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    ResponseResult listComment(String articleId, int page, int size);
}
