package me.newtrekwang.customwidget.main;

import android.app.Activity;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import me.newtrekwang.customwidget.main.strategy.TaskStrategy;
import me.newtrekwang.customwidget.main.strategy.ToastStrategy;

/**
 * @className DealItemStrategyFactory
 * @createDate 2019/6/10 0:26
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 处理item策略工厂
 *
 */
public final class DealItemStrategyFactory {
    /**
     * 单例
     */
    private DealItemStrategyFactory(){ }

    public static DealItemStrategyFactory instance;

    public static DealItemStrategyFactory getInstance(){
        if (instance == null){
            synchronized (DealItemStrategyFactory.class){
                if (instance == null){
                    instance = new DealItemStrategyFactory();
                }
            }
        }
        return instance;
    }


    public static final String TITLE_PULLREFRESHLAYOUT = "PullRefreshLayout";
    public static final String TITLE_TOAST = "CustomToast";
    public static final String TITLE_BUTTON = "Button";
    public static final String TITLE_IMAGEVIEW = "ImageView";
    public static final String TITLE_CARDVIEW = "CardView";
    public static final String TITLE_DIALOD = "Dialog";
    public static final String TITLE_RECYCLERVIEW = "RecyclerView";
    public static final String TITLE_TASK = "Task";


    public interface DealItem {
        /**
         * item处理
         * @param activity
         */
        void deal(Activity activity);
    }

    public static final Map<String,String> itemStrategyMap = new HashMap<>();

    static {
        itemStrategyMap.put(TITLE_TOAST, ToastStrategy.class.getCanonicalName());
        itemStrategyMap.put(TITLE_TASK, TaskStrategy.class.getCanonicalName());
    }

    /**
     * 产出对应的策略实例
     * @param title
     * @return
     */
    public DealItem createDealItemStrategy(String title){
        String strategyClassName = itemStrategyMap.get(title);
        if (TextUtils.isEmpty(strategyClassName)){
            return null;
        }
        DealItem dealItem = null;
        try {
            dealItem = (DealItem) Class.forName(strategyClassName).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dealItem;
    }

}
