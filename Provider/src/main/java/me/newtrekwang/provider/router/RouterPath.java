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
     * @desc 技术干货模块
     *
     */
    public static final class TechModule {
        private TechModule(){}

        /**
         * 技术干货首页碎片
         */
        public static final String PATH_TECH = "/TechModule/TechMain";

/************************************************ GankIO start ***********************************************************************/
        /**
         * GankIO 首页
         */
        public static final String PATH_TECH_GANK_IO_MAIN = "/TechModule/GankIOMain";
        /**
         *  GankIO 最近
         */
        public static final String PATH_TECH_GANK_IO_RECENTLY = "/TechModule/GankIORecently";

/************************************************ GankIO end ***********************************************************************/

    }
    /**
     * @className RouterPath
     * @createDate 2019/4/7 20:44
     * @author newtrekWang
     * @email 408030208@qq.com
     * @desc 新闻资讯模块
     *
     */
    public static final class NewsModule {
        private NewsModule(){}
        public static final String PATH_FRAGMENT_NEWS = "/NewsModule/NewsFragment";
    }

   /**
    * @className RouterPath
    * @createDate 2019/4/8 0:26
    * @author newtrekWang
    * @email 408030208@qq.com
    * @desc 娱乐模块
    *
    */
    public static final class EnterModule {
        private EnterModule(){}
        public static final String PATH_FRAGMENT_ENTER = "/EnterModule/EnterFragment";
    }

   /**
    * @className RouterPath
    * @createDate 2019/4/8 0:26
    * @author newtrekWang
    * @email 408030208@qq.com
    * @desc 其他模块
    *
    */
    public static final class OtherModule{
        private OtherModule(){}
        public static final String PATH_FRAGMENT_OTHER = "/OtherModule/OtherFragment";
    }


}
