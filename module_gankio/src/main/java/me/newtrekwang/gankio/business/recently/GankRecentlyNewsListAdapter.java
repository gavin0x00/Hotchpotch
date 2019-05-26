package me.newtrekwang.gankio.business.recently;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.newtrekwang.gankio.R;
import me.newtrekwang.gankio.data.protocal.NewsItem;

/**
 * @className GankRecentlyNewsListAdapter
 * @createDate 2019/5/13 0:17
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 二级列表适配器
 *
 */
public class GankRecentlyNewsListAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "GankRecentlyNewsListAda";
    private LayoutInflater layoutInflater;
    private List<String> groupTitles ;
    private Map<String, List<NewsItem>> subItemsMap;
    private SubItemClickListener subItemClickListener;

    public GankRecentlyNewsListAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        groupTitles = new ArrayList<>();
        subItemsMap = new HashMap<>(5);
    }

    @Override
    public int getGroupCount() {
        return groupTitles==null?0:groupTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<NewsItem>  subItems = subItemsMap.get(groupTitles.get(groupPosition));
        return subItems==null?0:subItems.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupTitles==null?null:groupTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<NewsItem>  subItems = subItemsMap.get(groupTitles.get(groupPosition));
        return subItems==null?null:subItems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupItemViewHolder groupItemViewHolder;
        if (convertView == null){
            View groupItemView = layoutInflater.inflate(R.layout.item_gank_io_news_group,parent,false);
            groupItemViewHolder = new GroupItemViewHolder(groupItemView);
            convertView = groupItemView;
            groupItemView.setTag(groupItemViewHolder);
        }else {
            groupItemViewHolder = (GroupItemViewHolder) convertView.getTag();
        }
        // 组名
        String title = groupTitles.get(groupPosition);
        groupItemViewHolder.tvGroupTitle.setText(TextUtils.isEmpty(title)?"null":title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubItemViewHolder subItemViewHolder;
        if (convertView == null){
            View subItemView = layoutInflater.inflate(R.layout.item_gank_io_news_sub,parent,false);
            subItemViewHolder = new SubItemViewHolder(subItemView);
            convertView = subItemView;
            convertView.setTag(subItemViewHolder);
        }else {
            subItemViewHolder = (SubItemViewHolder) convertView.getTag();
        }

        List<NewsItem> newsItems = subItemsMap.get(groupTitles.get(groupPosition));
        final NewsItem item = newsItems.get(childPosition);
        subItemViewHolder.tvAuther.setText(item.getWho());
        subItemViewHolder.tvSubTitle.setText(item.getDesc());
        subItemViewHolder.tvTime.setText(item.getPublishedAt());
        subItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subItemClickListener != null){
                    subItemClickListener.onSubItemClick(item);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public Map<String, List<NewsItem>> getSubItemsMap() {
        return subItemsMap;
    }

    public List<String> getGroupTitles() {
        return groupTitles;
    }

    /**
     * 设置列表数据
     * @param groupTitles 组名列表
     * @param subItemsMap 小组Map
     */
    public void setData(List<String> groupTitles,Map<String,List<NewsItem>> subItemsMap){
        this.groupTitles.clear();
        this.subItemsMap.clear();
        this.groupTitles.addAll(groupTitles);
        this.subItemsMap.putAll(subItemsMap);
        this.notifyDataSetChanged();
    }

    public void setSubItemClickListener(SubItemClickListener subItemClickListener) {
        this.subItemClickListener = subItemClickListener;
    }


    public static class GroupItemViewHolder {
        public View itemView;
        public TextView tvGroupTitle;

        public GroupItemViewHolder(View itemView) {
            itemView = itemView;
            tvGroupTitle = itemView.findViewById(R.id.gankio_recently_item_tv_group_title);
        }
    }

    public static class SubItemViewHolder {
        public View itemView;
        public TextView tvSubTitle,tvAuther,tvTime;
        public SubItemViewHolder(View itemView) {
            this.itemView = itemView;
            tvSubTitle = itemView.findViewById(R.id.gankio_recently_item_tv_title);
            tvAuther = itemView.findViewById(R.id.gankio_recently_item_tv_auther);
            tvTime = itemView.findViewById(R.id.gankio_recently_item_tv_date);
        }
    }


    public interface SubItemClickListener {
        /**
         * 点击了Item
         * @param newsItem
         */
        void onSubItemClick(NewsItem newsItem);
    }
}
