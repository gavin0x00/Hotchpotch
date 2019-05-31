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
        /**
         * 技术干货导航碎片页
         */
        public static final String PATH_TECH = "/App/Tect/Navigation";
        /**
         * 新闻类导航碎片页
         */
        public static final String PATH_NEWS = "/App/News/Navigation";
        /**
         * 影音娱乐导航页
         */
        public static final String PATH_ENTER = "/App/Enter/Navigation";
        /**
         * 其它导航页
         */
        public static final String PATH_OTHER = "/App/Other/Navigation";
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
        /**
         * web H5浏览
         */
        public static final String PATH_TECH_GANK_IO_WEB_H5 = GANKIO+"WebBrowser";

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

        /********************************************* av start ************************************************************************/
        public static final String AV = "/Enter/Av/";
       /**
        * 音视频处理导航页
        */
       public static final String AV_MAIN = AV+"Main";
       /**
        * 图片处理页
        */
       public static final String AV_IMAGE_PROCESS = AV+"ImageProcess";
       /********************************************* av end ************************************************************************/


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

        /***************************************** custom widget start ***************************************************************/
        public static final String CUSTOM_WIDGET = "/Other/CustomWidget/";
       /**
        * 定义控件模块首页
        */
       public static final String CUSTOM_WIDGET_MAIN = CUSTOM_WIDGET+"Main";

       /***************************************** custom widget end ***************************************************************/

   }


}
