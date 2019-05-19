package me.newtrekwang.gankio.business.classify;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.lib_base.ui.adapter.BaseRecyclerViewAdapter;
/**
 * @className GankIOClassifyPagerContentAdapter
 * @createDate 2019/5/20 0:47
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 分类页内容列表适配器
 *
 */
public class GankIOClassifyPagerContentAdapter extends BaseRecyclerViewAdapter<NewsItem, GankIOClassifyPagerContentAdapter.NewsItemViewHolder> {

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gank_io_news_sub,viewGroup,false);
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        NewsItem item = dataList.get(position);
        holder.tvAuther.setText(item.getWho());
        holder.tvSubTitle.setText(item.getDesc());
        holder.tvTime.setText(item.getPublishedAt());
    }

    public static class NewsItemViewHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public TextView tvSubTitle,tvAuther,tvTime;
        public NewsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubTitle = itemView.findViewById(R.id.gankio_recently_item_tv_title);
            tvAuther = itemView.findViewById(R.id.gankio_recently_item_tv_auther);
            tvTime = itemView.findViewById(R.id.gankio_recently_item_tv_date);
        }
    }
}
