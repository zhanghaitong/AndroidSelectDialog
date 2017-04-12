package com.haitong.selectdialoglibrary;

/**
 * Created by zhhaitong on 2017-03-31 18:23.
 */

public class SelectStringCallback {

    public interface Select1Callback {
        void onSelectFinish(String text, String id);

        void onSelectCancle();
    }

    public interface Select2Callback {
        void onSelectFinish(String firstString, String secondString, String id);

        void onSelectCancle();
    }

    public interface Select3Callback {
        void onSelectFinish(String firstString, String secondString, String thirdString, String id);

        void onSelectCancle();
    }
}