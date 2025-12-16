package com.zhangjiluo.app.demo.info_viewer;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showInfo(getInfoList());
    }

    List<Pair<String, String>> getInfoList() {

        List<Pair<String, String>> list = new ArrayList<>();
        list.add(Pair.create("安卓版本号", getHighlightString(Build.VERSION.RELEASE)));
        list.add(Pair.create("安卓SDK版本号", getHighlightString(Build.VERSION.SDK_INT)));
        list.add(Pair.create("设备品牌", Build.BRAND));
        list.add(Pair.create("设备型号", Build.MODEL));
        list.add(Pair.create("设备制造商", Build.MANUFACTURER));
        list.add(Pair.create("设备ID", Build.ID));
        list.add(Pair.create("设备名称", Build.DEVICE));
        list.add(Pair.create("设备指纹", Build.FINGERPRINT));
        list.add(Pair.create("设备硬件名称", Build.HARDWARE));
        list.add(Pair.create("设备主机", Build.HOST));
        list.add(Pair.create("设备用户", Build.USER));
        list.add(Pair.create("系统架构", getHighlightString(getDeviceArchitecture())));
        list.add(Pair.create("总存储空间", getInternalStorageInfo().totalSize));
        list.add(Pair.create("剩余存储空间", getInternalStorageInfo().availableSize));
        list.add(Pair.create("总运行内存", getMemoryInfo().totalMemory));
        list.add(Pair.create("剩余运行内存", getMemoryInfo().availMemory));
        list.add(Pair.create("屏幕宽度总px", getHighlightString(getDisplayInfo().widthPixels)));
        list.add(Pair.create("屏幕高度总px", getHighlightString(getDisplayInfo().heightPixels)));
        list.add(Pair.create("屏幕宽度总dp", getHighlightString(getDisplayInfo().widthDp)));
        list.add(Pair.create("屏幕高度总dp", getHighlightString(getDisplayInfo().heightDp)));
        list.add(Pair.create("屏幕宽度总sp", String.valueOf(getDisplayInfo().widthSp)));
        list.add(Pair.create("屏幕高度总sp", String.valueOf(getDisplayInfo().heightSp)));
        list.add(Pair.create("屏幕密度DPI", String.valueOf(getDisplayInfo().densityDpi)));
        list.add(Pair.create("屏幕尺寸(inch)", getHighlightString((float) getDisplayInfo().size)));
        list.add(Pair.create("方向", getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? "竖屏" : "横屏"));
        list.add(Pair.create("是否有硬件键盘", getResources().getConfiguration().keyboard == Configuration.KEYBOARD_NOKEYS ? "无硬件键盘" : "有硬件键盘"));

        // TODO: 网络信息
        // TODO: 传感器信息
        // TODO: 应用信息
        // TODO: 应用版本信息
        // TODO: 应用权限信息
        // TODO: 应用组件信息
        // TODO: 应用服务信息
        // TODO: 是否开启root权限
        // TODO: 是否开启ADB调试
        // TODO: 是否开启开发者选项
        // TODO: 是否开启USB调试
        // TODO: 是否开启USB安装
        // TODO: WEBView信息
        // TODO: 浏览器信息
        // TODO: 浏览器版本信息
        // TODO: 蓝牙版本
        // TODO: 刘海屏信息
        // TODO: 指纹识别信息

        return list;
    }

    String getInfoListString(List<Pair<String, String>> list) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i).first).append(": ").append(list.get(i).second).append("<br/>");
        }

        return stringBuilder.toString();
    }

    void showInfo(List<Pair<String, String>> list) {
        TextView textView = findViewById(R.id.info);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(getInfoListString(list), Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(getInfoListString(list)));
        }
    }

    String getDeviceArchitecture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] supportedAbis = Build.SUPPORTED_ABIS;
            if (supportedAbis.length > 0) {
                return supportedAbis[0];
            }
        }
        return Build.CPU_ABI;
    }

    MemoryInfo getMemoryInfo() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return new MemoryInfo(Formatter.formatFileSize(this, memoryInfo.totalMem), Formatter.formatFileSize(this, memoryInfo.availMem));
    }

    class MemoryInfo {
        String totalMemory;
        String availMemory;

        MemoryInfo(String totalMemory, String availMemory) {
            this.totalMemory = totalMemory;
            this.availMemory = availMemory;
        }
    }

    DisplayInfo getDisplayInfo() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density * 160;
        return new DisplayInfo(displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.widthPixels / displayMetrics.density, displayMetrics.heightPixels / displayMetrics.density, displayMetrics.widthPixels / displayMetrics.scaledDensity, displayMetrics.heightPixels / displayMetrics.scaledDensity, displayMetrics.densityDpi, Math.sqrt(Math.pow(displayMetrics.widthPixels, 2) + Math.pow(displayMetrics.heightPixels, 2)) / (displayMetrics.density * 160));
    }

    class DisplayInfo {
        int widthPixels;
        int heightPixels;
        float widthDp;
        float heightDp;
        float widthSp;
        float heightSp;
        int densityDpi;
        double size;

        DisplayInfo(int widthPixels, int heightPixels, float widthDp, float heightDp, float widthSp, float heightSp, int densityDpi, double size) {
            this.widthPixels = widthPixels;
            this.heightPixels = heightPixels;
            this.widthDp = widthDp;
            this.heightDp = heightDp;
            this.widthSp = widthSp;
            this.heightSp = heightSp;
            this.densityDpi = densityDpi;
            this.size = size;
        }
    }

    StorageInfo getInternalStorageInfo() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize;
        long totalBlocks;
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            totalBlocks = statFs.getBlockCountLong();
            availableBlocks = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
            availableBlocks = statFs.getAvailableBlocks();
        }
        long totalSize = totalBlocks * blockSize;
        long availableSize = availableBlocks * blockSize;
        return new StorageInfo(Formatter.formatFileSize(this, totalSize), Formatter.formatFileSize(this, availableSize));
    }

    class StorageInfo {
        String totalSize;
        String availableSize;

        StorageInfo(String totalSize, String availableSize) {
            this.totalSize = totalSize;
            this.availableSize = availableSize;
        }
    }

    String getHighlightString(String str) {
        return "<font color='#FF0000'>" + str + "</font>";
    }

    String getHighlightString(int str) {
        return "<font color='#FF0000'>" + str + "</font>";
    }

    String getHighlightString(float str) {
        return "<font color='#FF0000'>" + str + "</font>";
    }
}
