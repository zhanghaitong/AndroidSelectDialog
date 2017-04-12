package com.haitong.selectdialoglibrary;

/**
 * Created by zhhaitong on 2017-03-31 18:23.
 */

public class SelectIntCallback {

    public interface Select1Callback {
        void onSelectFinish(String text, int id);

        void onSelectCancle();
    }

    public interface Select2Callback {
        void onSelectFinish(String text, String text2, int id);

        void onSelectCancle();
    }

    public interface Select3Callback {
        void onSelectFinish(String text, String text2, String text3, int id);

        void onSelectCancle();
    }
}