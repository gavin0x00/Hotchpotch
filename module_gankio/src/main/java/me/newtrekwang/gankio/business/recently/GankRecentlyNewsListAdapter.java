package me.newtrekwang.gankio.business.recently;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.newtrekwang.gankio.R;

/**
 * @className GankRecentlyNewsListAdapter
 * @createDate 2019/5/13 0:17
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 二级列表适配器
 *
 */
public class GankRecentlyNewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class GroupItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tvGroupTitle;

        public GroupItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGroupTitle = itemView.findViewById(R.id.gankio_recently_item_tv_group_title);
        }
    }

    public static class SubItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tvSubTitle,tvAuther,tvTime;
        public SubItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubTitle = itemView.findViewById(R.id.gankio_recently_item_tv_title);
            tvAuther = itemView.findViewById(R.id.gankio_recently_item_tv_auther);
            tvTime = itemView.findViewById(R.id.gankio_recently_item_tv_date);
        }
    }
}
