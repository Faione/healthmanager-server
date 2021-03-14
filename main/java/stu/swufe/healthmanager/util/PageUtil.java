package stu.swufe.healthmanager.util;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {

    // 输入为当前所要求的页(从0开始)，以及页的大小size
    // 输出为，数据库中，起始的序号（从0开始），以及读取的数量
    public static Map<String ,Object> getQueryMap(int page, int size){
        Map<String, Object> rlt = new HashMap<>();

        rlt.put("start", page * size);
        rlt.put("size", size);

        return rlt;
    }
}
