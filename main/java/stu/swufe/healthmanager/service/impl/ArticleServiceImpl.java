package stu.swufe.healthmanager.service.impl;


import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.swufe.healthmanager.common.Constants;
import stu.swufe.healthmanager.common.ResponseMsg;
import stu.swufe.healthmanager.dao.ArticlePojoMapper;
import stu.swufe.healthmanager.pojo.ArticlePojo;
import stu.swufe.healthmanager.pojo.UserPojo;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.response.ResponseState;
import stu.swufe.healthmanager.service.IArticelService;
import stu.swufe.healthmanager.service.IUserService;
import stu.swufe.healthmanager.util.IDWorker;
import stu.swufe.healthmanager.util.TextUtils;

import java.util.Date;

@Service
@Log4j
public class ArticleServiceImpl implements IArticelService {
    @Autowired
    IUserService iUserService;

    @Autowired
    IDWorker idWorker;

    @Autowired
    ArticlePojoMapper articlePojoMapper;

    @Override
    public ResponseResult postArticle(StringBuffer tokenKey, ArticlePojo articlePojo) {
        // 1. 检查用户
        UserPojo userPojo = iUserService.checkUser(tokenKey);
        if(userPojo == null){
            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
        }

        // 2. 检查数据:  categoryId, labels, content
            if(articlePojo == null) return ResponseResult.creatFailed();

            if(TextUtils.isEmpty(articlePojo.getCategoryId())){
                return ResponseResult.creatFailed().setMsg(ResponseMsg.CATEGORY_EMPTY);
            }

            if(TextUtils.isEmpty(articlePojo.getLabels())){
                articlePojo.setLabels(Constants.Article.DEFAULT_LABEL);
            }

            if(TextUtils.isEmpty(articlePojo.getContent())){
                return ResponseResult.creatFailed().setMsg(ResponseMsg.CONTENT_EMPTY);
            }


        // 3. 补充数据: id, userId, userName, userAvatar, state, viewCount(初始为0), createTime, updateTime
        articlePojo.setId(String.valueOf(idWorker.nextId()));
        articlePojo.setUserId(userPojo.getId());
        articlePojo.setUserName(userPojo.getUserName());
        articlePojo.setUserAvatar(userPojo.getAvatar());

        articlePojo.setState(Constants.Article.DEFAULT_STATE);
        articlePojo.setViewCount(0);

        articlePojo.setCreateTime(new Date());
        articlePojo.setUpdateTime(new Date());


        // 4. 存入数据库
        articlePojoMapper.insert(articlePojo);

        // 5. 响应用户，并更新token
        return ResponseResult.createSuccess().setToken(String.valueOf(tokenKey));
    }

    @Override
    public ResponseResult deleteArticle(StringBuffer tokenKey, String articleId) {
        // 1. 检查用户
        UserPojo userPojo = iUserService.checkUser(tokenKey);
        if(userPojo == null){
            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
        }

        // 2. 删除文章
        articlePojoMapper.deleteByPrimaryKey(articleId);

        return ResponseResult.createSuccess().setToken(String.valueOf(tokenKey));
    }

    @Override
    public ResponseResult updateArticle(StringBuffer tokenKey, String articleId, ArticlePojo articlePojo) {
        // 1. 检查用户
        UserPojo userPojo = iUserService.checkUser(tokenKey);
        if(userPojo == null){
            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
        }

        // 2. 空值检测, 检查数据: labels, content

        if(TextUtils.isEmpty(articleId) || articlePojo == null){
            return ResponseResult.creatFailed();
        }

        if(TextUtils.isEmpty(articlePojo.getContent())){
            return ResponseResult.creatFailed().setMsg(ResponseMsg.CONTENT_EMPTY);
        }

        if(TextUtils.isEmpty(articlePojo.getLabels())){
            articlePojo.setLabels(Constants.Article.DEFAULT_LABEL);
        }

        // 3. 允许修改的：labels, content
        articlePojo.setId(articleId);
        articlePojo.setUpdateTime(new Date());

        articlePojoMapper.updateByPrimaryKey(articlePojo);

        return ResponseResult.createSuccess().setToken(String.valueOf(tokenKey));
    }

    @Override
    public ResponseResult retrieveArticle(String article_id, StringBuffer tokenKey) {
        // 1. 检查用户
        UserPojo userPojo = iUserService.checkUser(tokenKey);
        if(userPojo == null){
            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
        }

        // 2. 根据id查询

        if(TextUtils.isEmpty(article_id)) return ResponseResult.creatFailed();

        ArticlePojo articlePojo = articlePojoMapper.selectByPrimaryKey(article_id);

        return ResponseResult.createSuccess(ResponseState.SUCCESS, articlePojo).setToken(String.valueOf(tokenKey));
    }

    @Override
    public ResponseResult getArticleList(int page, int size, StringBuffer tokenKey) {
        // 1. 检查用户
        UserPojo userPojo = iUserService.checkUser(tokenKey);
        if(userPojo == null){
            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
        }

        // 分页查询


        return null;
    }


}
