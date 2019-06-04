package me.newtrekwang.base.imageloader;

import android.widget.ImageView;

import javax.annotation.Nonnull;

import me.newtrekwang.base.R;

/**
 * @className ImageLoader
 * @createDate 2019/6/4 1:07
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 负责保存加载参数的ImageLoader类，采用Builder模式,因为这些参数在图片加载框架里是通用的，所以把他们抽离出来
 *
 */
public class ImageLoader {
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
     * 需要解析的图片url
     */
    private String imageUrl;
    /**
     * 类型（大图，中图，小图）
     */
    private LoadSizeType type;
    /**
     * 当没有成功加载的时候显示的图片
     */
    private int placeHolder;
    /**
     * imageView对象
     */
    private ImageView imageView;
    /**
     * 加载策略，是否在wifi下加载
     */
    private NetLoadType wifiStrategy;

    private ImageLoader(Builder builder){
        this.imageUrl=builder.imageUrl;
        this.imageView=builder.imageView;
        this.type=builder.type;
        this.placeHolder=builder.placeHolder;
        this.wifiStrategy=builder.wifiStrategy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    public LoadSizeType getType() {
        return type;
    }

    public void setType(LoadSizeType type) {
        this.type = type;
    }

    public NetLoadType getWifiStrategy() {
        return wifiStrategy;
    }

    public void setWifiStrategy(NetLoadType wifiStrategy) {
        this.wifiStrategy = wifiStrategy;
    }

    public static class Builder {
        private String imageUrl;
        private LoadSizeType type;
        private int placeHolder;
        private ImageView imageView;
        private NetLoadType wifiStrategy;

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder type(LoadSizeType type) {
            this.type = type;
            return this;
        }

        public Builder placeHolderID(int placeHolderID) {
            this.placeHolder = placeHolderID;
            return this;
        }

        public Builder imageView(@Nonnull ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder wifiStrategy(NetLoadType wifiStrategy) {
            this.wifiStrategy = wifiStrategy;
            return this;
        }

        /**
         *  初始化默认参数
         */
        public Builder(){
            this.type=LoadSizeType.PIC_NORMAL;
            this.imageUrl="";
            this.imageView=null;
            this.placeHolder = R.drawable.default_place_holder;
            this.wifiStrategy= NetLoadType.LOAD_STRATEGY_NORMAL;
        }

        /**
         * 构建ImageLoader并返回
         */
        public ImageLoader create(){
            return new ImageLoader(this);
        }

    }
}
