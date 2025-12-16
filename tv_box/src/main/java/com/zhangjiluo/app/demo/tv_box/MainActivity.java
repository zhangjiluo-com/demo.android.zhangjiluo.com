package com.zhangjiluo.app.demo.tv_box;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLocalAssets(View view) {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show();
    }
}
