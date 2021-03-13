package stu.swufe.healthmanager.controller.user;


import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stu.swufe.healthmanager.pojo.ArticlePojo;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.service.IArticelService;

@RestController
@RequestMapping("/user/article")
@Log4j
public class ArticleAPI {
    @Autowired
    IArticelService articelService;

    /**
     * 录入用户内容
     * @param tokenKey
     * @param articlePojo
     * @return
     */
    @PostMapping
    public ResponseResult createArticle(@RequestParam("tokenKey") String tokenKey,
                                        @RequestBody ArticlePojo articlePojo){
        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);
        return articelService.postArticle(tokenKeyBuffer, articlePojo);
    }

    @DeleteMapping
    public ResponseResult deleteArticle(@RequestParam("article_id") String articleId,
                                        @RequestParam("token_key") String tokenKey){
        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);
        return articelService.deleteArticle(tokenKeyBuffer, articleId);
    }

    @PutMapping
    public ResponseResult updateArticle(@RequestParam("article_id") String articleId,
                                        @RequestParam("token_key") String tokenKey,
                                        @RequestBody ArticlePojo articlePojo){
        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);
        return articelService.updateArticle(tokenKeyBuffer, articleId, articlePojo);

    }

    @GetMapping("/{article_id}")
    public ResponseResult retrieveArticle(@PathVariable String article_id,
                                          @RequestParam("token_key") String tokenKey){

        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);
        return articelService.retrieveArticle(article_id, tokenKeyBuffer);
    }

    @GetMapping("/list")
    public ResponseResult getArticleList(@RequestParam("token_key") String tokenKey){
        StringBuffer tokenKeyBuffer = new StringBuffer(tokenKey);
//        return articelService.getArticleList(tokenKeyBuffer);
        return null;
    }




}
