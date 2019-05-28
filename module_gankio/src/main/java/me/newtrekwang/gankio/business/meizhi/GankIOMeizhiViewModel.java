package me.newtrekwang.gankio.business.meizhi;


import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import me.newtrekwang.gankio.data.protocal.NewsItem;
import me.newtrekwang.gankio.data.service.GankIODataService;

/**
 * @author newtrekWang
 * @fileName GankIOMeizhiViewModel
 * @createDate 2019/5/27 11:06
 * @email 408030208@qq.com
 * @desc 福利图viewModel
 */
public class GankIOMeizhiViewModel extends ViewModel {
    private final MutableLiveData<List<NewsItem>> newsItems;
    private GankIODataService gankIODataService;
    GankIOMeizhiViewModel(GankIODataService gankIODataService){
        this.gankIODataService = gankIODataService;
        this.newsItems = new MutableLiveData<>();
    }


    public void getMeizhiList(){

    }

}
