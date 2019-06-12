package me.newtrekwang.livedataprac.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import me.newtrekwang.livedataprac.R;
import me.newtrekwang.livedataprac.viewmodel.MainViewModel;

import android.os.Bundle;
import android.widget.TextView;

public class TwoActivity extends AppCompatActivity {
    private TextView tv;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        tv = findViewById(R.id.two_tv);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getmElapsedTime().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                tv.setText(aLong.toString());
            }
        });
    }
}
