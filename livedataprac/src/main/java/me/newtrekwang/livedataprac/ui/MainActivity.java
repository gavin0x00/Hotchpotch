package me.newtrekwang.livedataprac.ui;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import androidx.appcompat.app.AppCompatActivity;
import me.newtrekwang.livedataprac.R;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.main_tv);
        btn = findViewById(R.id.main_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

    }

    private void getData(){
        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello world";
            }
        },AppExecutors.IO()).whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                tv.setText(s);
            }
        });
    }


    static class AppExecutors {
        public static ExecutorService IO(){
            return Executors.newCachedThreadPool();
        }

        public static Executor Main(){
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor{
            private Handler handler = new Handler(Looper.getMainLooper());
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        }
    }
}
