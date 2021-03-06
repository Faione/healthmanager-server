package stu.swufe.healthmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.swufe.healthmanager.common.Constants;
import stu.swufe.healthmanager.dao.CommentPojoMapper;
import stu.swufe.healthmanager.pojo.ArticlePojo;
import stu.swufe.healthmanager.pojo.CommentPojoWithBLOBs;
import stu.swufe.healthmanager.pojo.UserPojo;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.response.ResponseState;
import stu.swufe.healthmanager.service.ICommentService;
import stu.swufe.healthmanager.service.IUserService;
import stu.swufe.healthmanager.util.IDWorker;
import stu.swufe.healthmanager.util.PageUtil;
import stu.swufe.healthmanager.util.TextUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    IUserService userService;

    @Autowired
    CommentPojoMapper commentPojoMapper;

    @Autowired
    IDWorker idWorker;

    @Override
    public ResponseResult uploadComment(CommentPojoWithBLOBs commentPojoWithBLOBs, StringBuffer tokenKeyBuffer) {
        // 1. 检测用户权限
//        UserPojo userPojo =  userService.checkUser(tokenKeyBuffer);
//        if(userPojo == null){
//            return ResponseResult.creatFailed(ResponseState.NOT_LOGIN_IN);
//        }

        // 2. 空值检测: content
        if(TextUtils.isEmpty(commentPojoWithBLOBs.getContent())){
            return ResponseResult.creatFailed("内容不能为空");
        }

        // 3. 初始化数据
        //        private String id;
//
//        private String uccId;（前端给出）
//
//        private String userId;（前端给出）
//
//        private String userAvatar;（前端给出）
//
//        private String userName;（前端给出）
//
//        private String state;
//
//        private Date createTime;
//
//        private Date updateTime;
//
//        private String content;


        commentPojoWithBLOBs.setId(String.valueOf(idWorker.nextId()));
        commentPojoWithBLOBs.setState("0");
        // 表示父内容为当前问题
        commentPojoWithBLOBs.setParentContent("0");
        commentPojoWithBLOBs.setCreateTime(new Date());
        commentPojoWithBLOBs.setUpdateTime(new Date());




        // 4. 存入数据库
        commentPojoMapper.insert(commentPojoWithBLOBs);

        return ResponseResult.createSuccess("上传成功").setMsg(String.valueOf(tokenKeyBuffer));
    }

    @Override
    public ResponseResult listComment(String articleId, int page, int size) {
        // 分页查询
        page = Math.max(page, Constants.Page.START_PAGE);

        size = Math.max(size, Constants.Page.PAGE_SIZE);

        Map<String, Object> params = PageUtil.getQueryMap(page, size);
        // 指定文章
        params.put("uccId", articleId);

        List<CommentPojoWithBLOBs> list =  commentPojoMapper.selectCommentsByAriticle(params);


        return ResponseResult.createSuccess(ResponseState.SUCCESS, list);
    }
}
