package stu.swufe.healthmanager.service;

import stu.swufe.healthmanager.pojo.CategoryPojo;
import stu.swufe.healthmanager.response.ResponseResult;

public interface ICategoryService {
    /**
     * 新增一个分类
     * @param categoryPojo
     * @return
     */
    public ResponseResult addCategory(CategoryPojo categoryPojo);

    /**
     * 删除一个分类
     * @param categoryId
     * @return
     */
    ResponseResult delCategory(String categoryId);

    /**
     * 修改分类
     * @param categoryId
     * @param categoryPojo
     * @return
     */
    ResponseResult alterCategory(String categoryId, CategoryPojo categoryPojo);

    /**
     * 获得一个分类
     * @param categoryId
     * @return
     */
    ResponseResult getCategory(String categoryId);

    /**
     * 获取分类列表
     * @return
     * @param page
     * @param size
     */
    ResponseResult getCategoryList(int page, int size);
}
