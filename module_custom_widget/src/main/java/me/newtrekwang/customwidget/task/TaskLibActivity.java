package me.newtrekwang.customwidget.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import me.newtrekwang.customwidget.R;
/**
 * @className TaskLibActivity
 * @createDate 2019/6/20 18:34
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc 多线程相关的类封装展示界面
 *
 */
public class TaskLibActivity extends AppCompatActivity {
    private static final String TAG = "TaskLibActivity";

    private Button btnStart;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_lib);
        initView();
    }

    private void initView() {
        btnStart = findViewById(R.id.task_btn_start);
        tvContent = findViewById(R.id.task_tv_content);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testAsyncTask();
            }
        });
    }

    private void testAsyncTask(){
        // 三个泛型参数分别代表传入的参数类型，任务执行过程需要更新的数据类型，任务执行结束返回的结果类型，如果无类型则可以用Void类
        AsyncTask<Integer,Integer,Void> asyncTask = new AsyncTask<Integer, Integer, Void>() {
            /**
             * 得到结果，在主线程执行
             * @param aVoid
             */
            @Override
            protected void onPostExecute(Void aVoid) {
                Log.d(TAG, "onPostExecute: >>>");
                super.onPostExecute(aVoid);
            }

            /**
             * 任务内容，在工作线程执行
             * @param integers
             * @return
             */
            @Override
            protected Void doInBackground(Integer... integers) {
                Log.d(TAG, "doInBackground: >>>params: "+Arrays.toString(integers));
                return null;
            }

            /**
             * 任务执行前，在主线程执行
             */
            @Override
            protected void onPreExecute() {
                Log.d(TAG, "onPreExecute: >>");
                super.onPreExecute();
            }

            /**
             * 任务已取消（带结果），在主线程执行
             * @param aVoid
             */
            @Override
            protected void onCancelled(Void aVoid) {
                super.onCancelled(aVoid);
                Log.d(TAG, "onCancelled（有参）: >>>");
            }

            /**
             * 任务已取消，在主线程执行
             */
            @Override
            protected void onCancelled() {
                super.onCancelled();
                Log.d(TAG, "onCancelled: >>>");
            }

            /**
             * 指定过程更新的数据，在主线程执行
             * @param values
             */
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                Log.d(TAG, "onProgressUpdate: "+ Arrays.toString(values));
            }
        };

        asyncTask.execute(2,3,1);
    }
}
