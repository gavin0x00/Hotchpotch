package me.newtrekwang.gankio.utils;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {
    public static List<String>  generateTestDateList(){
        List<String> list = new ArrayList<>();
        list.add("2019-05-10");
        list.add("2019-05-10");
        list.add("2019-05-10");
        list.add("2019-05-10");
        list.add("2019-05-10");
        list.add("2019-05-10");
        list.add("2019-05-10");
        list.add("2019-05-10");
        return list;
    }

    /**
     * 返回GankIO 分类标题列表
     * @return
     */
    public static List<String> generateClassifyTitlsList(){
        List<String>  list = new ArrayList<>();
        list.add("全部");
        list.add("Android");
        list.add("Ios");
        list.add("App");
        list.add("前端");
        list.add("瞎推荐");
        list.add("扩展资源");
        list.add("休息视频");
        return list;
    }
}
