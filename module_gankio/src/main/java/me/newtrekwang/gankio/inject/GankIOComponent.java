package me.newtrekwang.gankio.inject;

import dagger.Component;
import me.newtrekwang.lib_base.injection.PerComponentScope;
import me.newtrekwang.lib_base.injection.component.ActivityComponent;
/**
 * @className GankIOComponent
 * @createDate 2019/5/1 13:34
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc gankio 模块实例注入器
 *
 */
@PerComponentScope
@Component(dependencies = {ActivityComponent.class},modules = {GankIOModule.class})
public interface GankIOComponent {

}
