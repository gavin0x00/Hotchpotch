package me.newtrekwang.gankio.inject;

import dagger.Module;
import dagger.Provides;
import me.newtrekwang.gankio.data.service.GankIODataService;
import me.newtrekwang.gankio.data.service.GankIODataServiceImp;

/**
 * @className GankIOModule
 * @createDate 2019/5/1 13:33
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc GankIo module
 *
 */
@Module
public class GankIOModule {
    /**
     * 提供GankIO 数据服务实例
     * @param dataServiceImp
     * @return
     */
    @Provides
    public GankIODataService provideGankIoDataService(GankIODataServiceImp dataServiceImp){
        return dataServiceImp;
    }

}
