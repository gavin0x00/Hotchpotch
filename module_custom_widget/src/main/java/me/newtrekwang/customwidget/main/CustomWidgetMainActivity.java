package me.newtrekwang.customwidget.main;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.newtrekwang.base.ui.activity.BaseActivity;
import me.newtrekwang.customwidget.R;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className CustomWidgetMainActivity
 * @createDate 2019/5/30 15:29
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 自定义控件模块首页
 *
 */
@Route(path = RouterPath.OtherModule.CUSTOM_WIDGET_MAIN)
public final class CustomWidgetMainActivity extends BaseActivity {
    private static final String TAG = "CustomWidgetMainActivit";
    private ListView listView;
    private List<Map<String,String>> list=new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);
        initData();
        initView();
    }

    private void initData() {
        Map<String,String> map_1=new HashMap<>(2);
        map_1.put("title","PullRefreshLayout");
        list.add(map_1);
        Map<String,String> map_2=new HashMap<>(2);
        map_2.put("title","CustomToast");
        list.add(map_2);
        Map<String,String> map_3=new HashMap<>(2);
        map_3.put("title","ImageLoader");
        list.add(map_3);
        Map<String,String> map_4=new HashMap<>(2);
        map_4.put("title","ExpandTextView");
        list.add(map_4);
        Map<String,String> map_5=new HashMap<>(2);
        map_5.put("title","AutoScrollViewPager");
        list.add(map_5);
        Map<String,String> map_6=new HashMap<>(2);
        map_6.put("title","Dialog");
        list.add(map_6);
        Map<String,String> map_7=new HashMap<>(2);
        map_7.put("title","ContentProvider");
        list.add(map_7);
        Map<String,String> map_8=new HashMap<>(2);
        map_8.put("title","BottemSheetDialogFragment");
        list.add(map_8);
        Map<String,String> map_9=new HashMap<>(2);
        map_9.put("title","startServiceDownload");
        list.add(map_9);
        Map<String,String> map_10=new HashMap<>(2);
        map_10.put("title","notification");
        list.add(map_10);
    }

    private void initView() {
        listView = findViewById(R.id.custom_widget_main_lv);
        simpleAdapter=new SimpleAdapter(this,list,android.R.layout.simple_list_item_1,new String[]{"title"}, new int[]{android.R.id.text1});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> itemData = (Map<String, String>) simpleAdapter.getItem(position);
                String title = (itemData==null?null:itemData.get("title"));
                if (!TextUtils.isEmpty(title)){
                    DealItemStrategyFactory.DealItem dealItem = DealItemStrategyFactory.getInstance().createDealItemStrategy(title);
                    if (dealItem != null){
                        dealItem.deal(CustomWidgetMainActivity.this);
                    }else {
                        showToast("处理策略为空！");
                    }
                }else {
                    showToast("待实现！");
                }
            }
        });
    }
}
