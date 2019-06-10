package me.newtrekwang.base.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

import me.newtrekwang.base.utils.NetWorkUtils;
import me.newtrekwang.base.utils.SettingUtils;

/**
 * @className GlideImageLoaderProvider
 * @createDate 2019/6/4 1:32
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc Glide提供的加载策略
 *
 */
public class GlideImageLoaderProvider implements IImageLoaderStrategy {
    private static final String TAG = "GlideImageLoaderProvide>>>>>>";

    @Override
    public void loadImage(Context context, ImageLoader imageLoader) {
        boolean flag= SettingUtils.isOnlyWifiLoadImg();
//       如果没有设置wifi下才加载图片，则直接加载
            if (!flag){
                loadNormal(context,imageLoader);
                return;
            }
//       如果没有设置wifi下才加载图片，则具体再判断
        ImageLoader.NetLoadType strategy = imageLoader.getWifiStrategy();
        if (strategy == ImageLoader.NetLoadType.LOAD_STRATEGY_NORMAL){
            loadNormal(context,imageLoader);
        }else if (strategy == ImageLoader.NetLoadType.LOAD_STRATEGY_ONLY_WIFI){
            if (NetWorkUtils.getAPNType(context) == NetWorkUtils.NETSTATE_WIFI){
//                有wifi,正常加载
                loadNormal(context,imageLoader);
            }else {
//                无wifi,加载缓存
                loadCache(context,imageLoader);
            }
        }else {
            loadNormal(context,imageLoader);
        }
    }
    /**
     * load image with Glide
     */
    private void loadNormal(Context context, ImageLoader imageLoader){
        if (imageLoader.getPlaceHolder() == 0){
            Glide.with(context).load(imageLoader.getImageUrl())
                    .dontAnimate()
                    .centerCrop()
                    .into(imageLoader.getImageView());
        }else {
            Glide.with(context).load(imageLoader.getImageUrl())
                    .placeholder(imageLoader.getPlaceHolder())
                    .dontAnimate()
                    .centerCrop()
                    .into(imageLoader.getImageView());
        }

    }
    /**
     * load cache with Glide
     */
    private void loadCache(Context context, ImageLoader imageLoader){
        StreamModelLoader<String>  stringStreamModelLoader = new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
                return null;
            }
        };
        if (imageLoader.getPlaceHolder() == 0){
            Glide.with(context).using(stringStreamModelLoader)
                    .load(imageLoader.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(imageLoader.getImageView()
                    );
        }else {
            Glide.with(context).using(stringStreamModelLoader)
                    .load(imageLoader.getImageUrl())
                    .placeholder(imageLoader.getPlaceHolder())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(imageLoader.getImageView()
                    );
        }

    }
}
