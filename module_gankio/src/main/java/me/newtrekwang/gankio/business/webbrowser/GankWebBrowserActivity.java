package me.newtrekwang.gankio.business.webbrowser;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import me.newtrekwang.gankio.R;
import me.newtrekwang.base.ui.activity.BaseActivity;
import me.newtrekwang.provider.router.RouterPath;

/**
 * @className GankWebBrowserActivity
 * @createDate 2019/5/26 13:51
 * @author newtrekWang
 * @email 408030208@qq.com
 * @desc web浏览器界面，展示H5界面
 *
 */
@Route(path = RouterPath.TechModule.PATH_TECH_GANK_IO_WEB_H5)
public class GankWebBrowserActivity extends BaseActivity {
    public final static String KEY_BROWSER_URL = "key_for_browser_url";
    public final static String KEY_BROWSER_TITLE = "key_for_browser_title";

    private ImageView imgBack;
    private WebView webView;
    private TextView tvTitle;
    private String  url;
    private String title;
    private ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_io_web_browser);
        initView();
        initDate();
    }

    private void initDate() {
        url = getIntent().getStringExtra(KEY_BROWSER_URL);
        if (TextUtils.isEmpty(url)){
            return;
        }

        title = getIntent().getStringExtra(KEY_BROWSER_TITLE);
        tvTitle.setText(TextUtils.isEmpty(title)?"null":title);
        // 使webView不用跳转到设备的浏览器去加载Url
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }

    private void initView() {
        imgBack = findViewById(R.id.gankio_web_browser_img_back);
        tvTitle = findViewById(R.id.gankio_web_browser_tv_title);
        webView = findViewById(R.id.gankio_web_browser_webView);
        rootView = findViewById(R.id.gankio_web_browser_rootView);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rootView.removeView(webView);
        webView.destroy();
    }
}
