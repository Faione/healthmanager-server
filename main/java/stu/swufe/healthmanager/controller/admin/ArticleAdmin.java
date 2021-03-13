package stu.swufe.healthmanager.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stu.swufe.healthmanager.pojo.ArticlePojo;
import stu.swufe.healthmanager.response.ResponseResult;

@RestController
@RequestMapping("/admin/article")
class ArticleAdmin {

    @PostMapping
    public ResponseResult postArticle(@RequestBody ArticlePojo articlePojo){
        return  null;
    }
}
