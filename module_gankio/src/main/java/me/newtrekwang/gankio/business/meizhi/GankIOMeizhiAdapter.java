package me.newtrekwang.gankio.business.meizhi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.lib_base.common.BaseApplication;
import me.newtrekwang.lib_base.ui.adapter.BaseRecyclerViewAdapter;
import me.newtrekwang.lib_base.utils.ImageLoaderUtils;

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
        ImageLoaderUtils.loadImage(BaseApplication.getBaseApplication(),imgUrl,R.mipmap.ic_launcher,holder.imgMeizhi);
    }

    public static class  MeizhiItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgMeizhi;

        public MeizhiItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeizhi = itemView.findViewById(R.id.gankio_recently_item_img_meizhi);
        }
    }
}
