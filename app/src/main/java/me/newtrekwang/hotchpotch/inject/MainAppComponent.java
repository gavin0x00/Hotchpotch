package me.newtrekwang.hotchpotch.inject;


import dagger.Component;
import me.newtrekwang.lib_base.injection.PerComponentScope;
import me.newtrekwang.lib_base.injection.component.ActivityComponent;
import me.newtrekwang.hotchpotch.business.main.MainActivity;

/**
 * @author newtrekWang
 * @fileName MainAppComponent
 * @createDate 2018/9/3 15:07
 * @email 408030208@qq.com
 * @desc 主模块注入器
 */
@PerComponentScope
@Component(dependencies = {ActivityComponent.class},modules = {MainModule.class})
public interface MainAppComponent {
    /**
     * 注入到MainActivity
     * @param mainActivity
     */
    void inject(MainActivity mainActivity);
}
