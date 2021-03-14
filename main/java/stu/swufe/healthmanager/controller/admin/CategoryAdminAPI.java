package stu.swufe.healthmanager.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import stu.swufe.healthmanager.pojo.CategoryPojo;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.service.ICategoryService;

@RestController
@RequestMapping("/admin/category")
public class CategoryAdminAPI {

    @Autowired
    ICategoryService categoryService;

    // 权限检查？
    @PostMapping
    public ResponseResult addCategory(@RequestBody CategoryPojo categoryPojo){
        return categoryService.addCategory(categoryPojo);
    }

    @DeleteMapping
    public ResponseResult delCategory(@RequestParam("category_id") String categoryId){
        return categoryService.delCategory(categoryId);
    }

    @PutMapping
    public ResponseResult alterCategory(@RequestParam("category_id") String categoryId,
                         @RequestBody CategoryPojo categoryPojo){
        return categoryService.alterCategory(categoryId, categoryPojo);
    }

    @GetMapping("/{category_id}")
    public ResponseResult getCategory(@PathVariable("category_id") String categoryId){

        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/list/{page}/{size}")
    public ResponseResult getCategoryList(@PathVariable("page") int page,
                                          @PathVariable("size") int size){
        return categoryService.getCategoryList(page, size);
    }

}
