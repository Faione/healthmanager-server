package stu.swufe.healthmanager.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stu.swufe.healthmanager.pojo.CommentPojoWithBLOBs;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.service.ICommentService;

@RequestMapping("user/comment")
@RestController
public class CommentApi {

    @Autowired
    ICommentService commentService;

    @PostMapping
    public ResponseResult uploadComment(@RequestBody CommentPojoWithBLOBs commentPojoWithBLOBs,
                                        @RequestParam("token_key") String tokenKey){

        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);

        return commentService.uploadComment(commentPojoWithBLOBs, tokenKeyBuffer);
    }

    @GetMapping("/list")
    public ResponseResult getCommentList(@RequestParam("article_id")String articleId){
        return null;
    }

}
