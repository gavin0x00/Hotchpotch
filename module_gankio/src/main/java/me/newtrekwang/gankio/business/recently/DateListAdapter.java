package me.newtrekwang.gankio.business.recently;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
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
        SpannableString spannableString = new SpannableString(dateSt);
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(0.8f);
        RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(1.2f);
        spannableString.setSpan(sizeSpan01,0,8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(sizeSpan02,8,10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tv_date.setText(spannableString);

    }

    public static class  DateItemViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_date;
        public DateItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.item_tv_date);
        }
    }
}
