//package stu.swufe.healthmanager.controller.portal;
//
//
//import lombok.extern.log4j.Log4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import stu.swufe.healthmanager.pojo.ArticlePojo;
//import stu.swufe.healthmanager.response.ResponseResult;
//import stu.swufe.healthmanager.service.IArticelService;
//
//@RestController(value = "/portal/article")
//@Log4j
//public class ArticlePortalAPI {
//    @Autowired
//    IArticelService articelService;
//
//    /**
//     * 录入用户内容
//     * @param tokenKey
//     * @param articlePojo
//     * @return
//     */
//    @PostMapping
//    public ResponseResult createArticle(@RequestParam("tokenKey") StringBuffer tokenKey,
//                                        @RequestBody ArticlePojo articlePojo){
//
//        return articelService.postArticle(tokenKey, articlePojo);
//    }
//
//    @DeleteMapping
//    public ResponseResult deleteArticle(@RequestParam("article_id") String articleId,
//                                        @RequestParam("token_key") StringBuffer tokenKey){
//
//        return articelService.deleteArticle(tokenKey, articleId);
//    }
//
//    @PutMapping
//    public ResponseResult updateArticle(@RequestParam("article_id") String articleId,
//                                        @RequestParam("token_key") StringBuffer tokenKey,
//                                        @RequestBody ArticlePojo articlePojo){
//
//        return articelService.updateArticle(tokenKey, articleId, articlePojo);
//
//    }
//
//    @GetMapping("/{article_id}")
//    public ResponseResult retrieveArticle(@PathVariable String article_id,
//                                          @RequestParam("token_key") StringBuffer tokenKey){
//        return articelService.retrieveArticle(article_id, tokenKey);
//    }
//
//    @GetMapping("/list")
//    public ResponseResult getArticleList(@RequestParam("token_key") StringBuffer tokenKey){
////        return articelService.getArticleList(tokenKey);
//        return null;
//    }
//
//
//
//
//}
