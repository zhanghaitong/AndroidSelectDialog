package com.haitong.selectdialoglibrary.widget;

/**
 * Created by zhhaitong on 2016/9/12 18:12.
 */
public interface SelectCallback {
    void onSelectFinish(String... selectedStrings);

    void onSelectCancle();
}