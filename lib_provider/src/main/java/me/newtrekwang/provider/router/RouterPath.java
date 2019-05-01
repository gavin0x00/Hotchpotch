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
        public static final String PATH_MAIN = "/App/Home";
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
        public static final String PATH_TECH = "/Tech/main";

/************************************************ GankIO start ***********************************************************************/
        private static final String GANKIO = "/Tech/GankIO/";
        /**
         * GankIO 首页
         */
        public static final String PATH_TECH_GANK_IO_MAIN = GANKIO+"GankIOMain";
        /**
         *  GankIO 最新
         */
        public static final String PATH_TECH_GANK_IO_RECENTLY = GANKIO+"GankIORecently";
        /**
         * GankIO 分类
         */
        public static final String PATH_TECH_GANK_IO_CLASSIFY = GANKIO+"Classify";
        /**
         * GankIO 妹纸
         */
        public static final String PATH_TECH_GANK_IO_NEIZHI = GANKIO+"Meizhi";

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
        public static final String PATH_FRAGMENT_NEWS = "/NewsFragment/";
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
        public static final String PATH_FRAGMENT_ENTER = "/EnterFragment/";
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
        public static final String PATH_FRAGMENT_OTHER = "/OtherFragment/";
    }


}
