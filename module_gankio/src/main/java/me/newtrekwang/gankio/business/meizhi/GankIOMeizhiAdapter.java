package me.newtrekwang.gankio.business.meizhi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.newtrekwang.base.imageloader.ImageLoader;
import me.newtrekwang.base.imageloader.ImageLoaderUtils;
import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.base.common.BaseApplication;
import me.newtrekwang.base.ui.adapter.BaseRecyclerViewAdapter;

/**
 * @author newtrekWang
 * @fileName GankIOMeizhiAdapter
 * @createDate 2019/5/30 13:46
 * @email 408030208@qq.com
 * @desc 福利图片适配器
 */
public class GankIOMeizhiAdapter extends BaseRecyclerViewAdapter<NewsItem, GankIOMeizhiAdapter.MeizhiItemViewHolder> {

    @NonNull
    @Override
    public MeizhiItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_io_meizhi,parent,false);
        return new MeizhiItemViewHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull MeizhiItemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String imgUrl = dataList.get(position).getUrl();
        ImageLoader imageLoader = new ImageLoader.Builder()
                .imageUrl(imgUrl)
                .placeHolderID(R.mipmap.ic_launcher)
                .type(ImageLoader.LoadSizeType.PIC_NORMAL)
                .imageView(holder.imgMeizhi)
                .create();
        ImageLoaderUtils.getInstance()
                .loadImage(BaseApplication.getBaseApplication(),imageLoader);
    }

    public static class  MeizhiItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgMeizhi;

        public MeizhiItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeizhi = itemView.findViewById(R.id.gankio_recently_item_img_meizhi);
        }
    }
}
