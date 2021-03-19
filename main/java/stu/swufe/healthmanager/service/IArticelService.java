package stu.swufe.healthmanager.service;

import stu.swufe.healthmanager.pojo.ArticlePojo;
import stu.swufe.healthmanager.response.ResponseResult;

public interface IArticelService {

    /**
     * 录入文章
     * @param tokenKey
     * @param articlePojo
     * @return
     */
    ResponseResult postArticle(StringBuffer tokenKey, ArticlePojo articlePojo);

    /**
     * 删除文章
     * @param tokenKey
     * @param articleId
     * @return
     */
    ResponseResult deleteArticle(StringBuffer tokenKey, String articleId);

    /**
     * 更新文章
     * @param tokenKey
     * @param articleId
     * @param articlePojo
     * @return
     */
    ResponseResult updateArticle(StringBuffer tokenKey, String articleId, ArticlePojo articlePojo);


    /**
     * 查询一篇文章
     * @param article_id
     * @return
     */
    ResponseResult retrieveArticle(String article_id);


    /**
     * 查询多篇文章
     *
     * @param page
     * @param size
     * @return
     */
    ResponseResult getArticleList(int page, int size);
}
