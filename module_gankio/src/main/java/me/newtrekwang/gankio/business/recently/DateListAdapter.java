package me.newtrekwang.gankio.business.recently;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.newtrekwang.gankio.R;
import me.newtrekwang.lib_base.ui.adapter.BaseRecyclerViewAdapter;

public class DateListAdapter extends BaseRecyclerViewAdapter<String, DateListAdapter.DateItemViewHolder> {


    @NonNull
    @Override
    public DateItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gank_io_recently_date,viewGroup,false);
        return new DateItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateItemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String  dateSt = dataList.get(position);
        holder.tv_date.setText(dateSt);
    }

    public static class  DateItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_date;
        public DateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.item_tv_date);
        }
    }
}
