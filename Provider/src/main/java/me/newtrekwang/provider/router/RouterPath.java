package me.newtrekwang.provider.router;

/**
 * @author newtrekWang
 * @fileName RouterPath
 * @createDate 2018/9/3 14:02
 * @email 408030208@qq.com
 * @desc 模块路由 路径定义
 */
public final class RouterPath {
    private RouterPath(){}
    /**
     * @className RouterPath
     * @createDate 2018/9/3 14:07
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 主模块
     *
     */
    public static final class MainModule {
        private MainModule(){}
        public static final String PATH_MAIN = "/app/home";
    }
    /**
     * @className RouterPath
     * @createDate 2019/4/7 20:45
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc Gank模块
     *
     */
    public static final class GankModule{
        private GankModule(){}
        public static final String PATH_FRAGMENT_GANK = "/gankModule/GankFragment";
    }
    /**
     * @className RouterPath
     * @createDate 2019/4/7 20:44
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 百思不得姐模块
     *
     */
    public static final class BaisiModule{
        private BaisiModule(){}
        public static final String PATH_FRAGMENT_BAISI = "/BaisiModule/BaisiFragment";
    }

    /**
     * 豆瓣电影模块
     */
    public static final class DoubanModule{
        private DoubanModule(){}
        public static final String PATH_FRAGMENT_DOUBAN = "/doubanModule/DoubanFragment";
    }

    /**
     * 其他模块
     */
    public static final class OtherModule{
        private OtherModule(){}
        public static final String PATH_FRAGMENT_OTHER = "/otherModule/OtherFragment";
    }


}
