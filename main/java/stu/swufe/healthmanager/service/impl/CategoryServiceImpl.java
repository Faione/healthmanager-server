package stu.swufe.healthmanager.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import stu.swufe.healthmanager.common.Constants;
import stu.swufe.healthmanager.common.ResponseMsg;
import stu.swufe.healthmanager.dao.CategoryPojoMapper;
import stu.swufe.healthmanager.pojo.CategoryPojo;
import stu.swufe.healthmanager.response.ResponseResult;
import stu.swufe.healthmanager.response.ResponseState;
import stu.swufe.healthmanager.service.ICategoryService;
import stu.swufe.healthmanager.util.IDWorker;
import stu.swufe.healthmanager.util.PageUtil;
import stu.swufe.healthmanager.util.TextUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
@Log4j
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    IDWorker idWorker;

    @Autowired
    CategoryPojoMapper categoryPojoMapper;

    @Override
    public ResponseResult addCategory(CategoryPojo categoryPojo) {

        // 1. 检查数据:  name, order
        if(categoryPojo == null) return ResponseResult.creatFailed();

        if(TextUtils.isEmpty(categoryPojo.getName())){
            return ResponseResult.creatFailed().setMsg(ResponseMsg.CATEGORY_EMPTY);
        }

        if(TextUtils.isEmpty(categoryPojo.getDescription())){
            return ResponseResult.creatFailed().setMsg("描述不能为空");
        }

        // 2. 补充数据: id, states, createTime, updateTime
//        categoryPojo.setId(String.valueOf(idWorker.nextId()));
        categoryPojo.setId(String.valueOf(idWorker.nextId()));
        categoryPojo.setPinyin("无");
        categoryPojo.setStatus(Constants.Article.DEFAULT_STATE);
        categoryPojo.setOrder(0);

        categoryPojo.setCreateTime(new Date());
        categoryPojo.setUpdateTime(new Date());


        // 3. 存入数据库
        categoryPojoMapper.insert(categoryPojo);

        return ResponseResult.createSuccess();
    }

    @Override
    public ResponseResult delCategory(String categoryId) {
        // 1. 删除分类
        categoryPojoMapper.deleteByPrimaryKey(categoryId);

        return ResponseResult.createSuccess();
    }

    @Override
    public ResponseResult alterCategory(String categoryId, CategoryPojo categoryPojo) {

        // 1. 空值判断: 允许修改，name、description（可空）、pinyin（可空）
        if(TextUtils.isEmpty(categoryId) || categoryPojo == null){
            return ResponseResult.creatFailed().setMsg("参数不能为空");
        }

        CategoryPojo categoryPojoFromDb = categoryPojoMapper.selectByPrimaryKey(categoryId);
        if(categoryPojoFromDb == null){
            return ResponseResult.creatFailed().setMsg("先创建才能修改");
        }


        // 2. 分类名不为空，不同于当前且数据库中已有则为重复
        if(categoryPojo.getName() != null
                && !categoryPojo.getName().equals(categoryPojoFromDb.getName())
                && categoryPojoMapper.countSelectByName(categoryPojo.getName())>0){
            return ResponseResult.creatFailed(ResponseMsg.USER_EXIST);
        }

        // 3. 修改数据库

        categoryPojo.setUpdateTime(new Date());  // 记录修改时间

        // 4. 设置id, 否则无法更改
        categoryPojo.setId(categoryId);

        categoryPojoMapper.updateByPrimaryKeySelective(categoryPojo);

        return ResponseResult.createSuccess("修改成功");
    }

    @Override
    public ResponseResult getCategory(String categoryId) {
        if(categoryId == null){
            return ResponseResult.creatFailed();
        }

        CategoryPojo categoryPojoFromDb = categoryPojoMapper.selectByPrimaryKey(categoryId);
        if(categoryPojoFromDb == null){
            return ResponseResult.creatFailed("没有这个分类");
        }


        return ResponseResult.createSuccess(ResponseState.SUCCESS, categoryPojoFromDb);
    }

    @Override
    public ResponseResult getCategoryList(int page, int size) {

        // 设置默认值
        page = Math.max(page, Constants.Page.START_PAGE);

        size = Math.max(size, Constants.Page.PAGE_SIZE);

        Map<String, Object> map = PageUtil.getQueryMap(page, size);

        List<CategoryPojo> list = categoryPojoMapper.selectCategoryList(map);


        return ResponseResult.createSuccess(ResponseState.SUCCESS, list);
    }

}
