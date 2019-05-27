package me.newtrekwang.hotchpotch.business.main.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.newtrekwang.lib_base.ui.adapter.BaseRecyclerViewAdapter;
import me.newtrekwang.hotchpotch.R;

/**
 * @className TechnologyListAdapter
 * @createDate 2019/4/8 1:09
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 技术干货条目适配器
 *
 */
public class TechnologyListAdapter extends BaseRecyclerViewAdapter<String, TechnologyListAdapter.TechItemViewHolder> {

    @NonNull
    @Override
    public TechItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tech_project,viewGroup,false);
        return new TechItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechItemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String title = dataList.get(position);

        holder.tvTitle.setText(title);
    }

    public static class TechItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TechItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_tv_title);
        }
    }
}
