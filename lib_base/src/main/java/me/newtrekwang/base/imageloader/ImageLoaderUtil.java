package me.newtrekwang.base.imageloader;

import android.content.Context;

import javax.annotation.Nonnull;

/**
 * @className ImageLoaderUtil
 * @createDate 2019/6/4 1:29
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc ImageLoaderUtil 图片加载工具类
 *
 */
public class ImageLoaderUtil {
    /**
     * 加载图片大小类型声明
     */
    public enum LoadSizeType{
        PIC_SMALL,
        PIC_NORMAL,
        PIC_BIG
    }
    /**
     * 网络加载类型声明
     */
    public enum NetLoadType {
        LOAD_STRATEGY_NORMAL,
        LOAD_STRATEGY_ONLY_WIFI
    }

    /**
     * 图片加载策略
     */
    private IImageLoaderStrategy imageLoaderStrategy;

    /**
     * 构造私有
     */
    private ImageLoaderUtil() {
        this.imageLoaderStrategy = new GlideImageLoaderProvider();
    }

    /**
     * 获取单例
     */
    public static ImageLoaderUtil getInstance() {
        return ImageLoaderUtilHolder.INSTANCE;
    }

    /**
     * 提供单例
     */
    private static class ImageLoaderUtilHolder {
        private static ImageLoaderUtil INSTANCE = new ImageLoaderUtil();
    }

    /**
     * 设置全局图片加载策略
     */
    public void setLoadImgStrategy(@Nonnull  IImageLoaderStrategy imageLoaderStrategy) {
        this.imageLoaderStrategy = imageLoaderStrategy;
    }

    /**
     * 加载图片
     * @param context
     * @param imageLoader
     */
    public void loadImage(@Nonnull Context context,@Nonnull ImageLoader imageLoader){
        if (this.imageLoaderStrategy == null){
            return;
        }
        this.imageLoaderStrategy.loadImage(context,imageLoader);
    }
}
