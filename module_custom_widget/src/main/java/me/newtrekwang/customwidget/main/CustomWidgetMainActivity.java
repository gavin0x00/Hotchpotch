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

    private final String KEY_TITLE = "key_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_widget);
        initData();
        initView();
    }

    private void initData() {
        String[] titles =  getResources().getStringArray(R.array.main_items);
        for (int i=0;i<titles.length;i++){
            Map<String,String> map=new HashMap<>(2);
            map.put(KEY_TITLE,titles[i]);
            list.add(map);
        }
    }

    private void initView() {
        listView = findViewById(R.id.custom_widget_main_lv);
        simpleAdapter=new SimpleAdapter(this,list,android.R.layout.simple_list_item_1,new String[]{KEY_TITLE}, new int[]{android.R.id.text1});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> itemData = (Map<String, String>) simpleAdapter.getItem(position);
                String title = (itemData==null?null:itemData.get(KEY_TITLE));
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
