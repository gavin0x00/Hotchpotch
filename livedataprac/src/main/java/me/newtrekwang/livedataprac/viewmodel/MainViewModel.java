package me.newtrekwang.livedataprac.viewmodel;

import android.os.SystemClock;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author newtrekWang
 * @fileName MainViewModel
 * @createDate 2019/6/12 11:38
 * @email 408030208@qq.com
 * @desc TODO
 */
public class MainViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();
    private long mInitialTime;
    private Timer timer;
    public MainViewModel(){
        Log.d(TAG, "MainViewModel: >>>>>>>>>>>>");
        mInitialTime = SystemClock.elapsedRealtime();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final  long newValue = (SystemClock.elapsedRealtime()- mInitialTime) /1000;
                mElapsedTime.postValue(newValue);
            }
        },1000,2000);

    }

    public MutableLiveData<Long> getmElapsedTime() {
        return mElapsedTime;
    }

    @Override
    protected void onCleared() {
        Log.d(TAG, "onCleared: >>>>>");
        super.onCleared();
        timer.cancel();
    }
}
