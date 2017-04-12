package com.haitong.selectdialoglibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.haitong.selectdialoglibrary.R;
import com.haitong.selectdialoglibrary.adapters.ArrayWheelAdapter;

import java.util.Arrays;
import java.util.Map;


/**
 * Created by zhhaitong on 2016/9/12 09:38.
 */
public class TwoWheelDialog implements View.OnClickListener, OnWheelChangedListener {

    private String[] mFirstWheelData;
    private Map<String, String[]> mSecondWheelData;
    private Map<String, String> mLastData;

    private String mFirstCurrentName;
    private String mSecondCurrentName;
    private String mLastCurrentName;

    private Context mContent;
    private Dialog mDialog;
    private WheelView mFirstWheel, mSecondWheel, mThirdWheel;
    private TextView cancleBtn, finishBtn;
    private Display display;
    private SelectCallback callback;

    public TwoWheelDialog(Context context) {
        this.mContent = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public TwoWheelDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContent).inflate(
                R.layout.dialog_wheel, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        mFirstWheel = (WheelView) view.findViewById(R.id.wheel_first);
        mSecondWheel = (WheelView) view.findViewById(R.id.wheel_second);
        mThirdWheel = (WheelView) view.findViewById(R.id.wheel_third);
        cancleBtn = (TextView) view.findViewById(R.id.btn_cancle);
        finishBtn = (TextView) view.findViewById(R.id.btn_finish);

        mFirstWheel.addChangingListener(this);
        mSecondWheel.addChangingListener(this);
        mThirdWheel.addChangingListener(this);


        cancleBtn.setOnClickListener(this);

        finishBtn.setOnClickListener(this);

        // 定义Dialog布局和参数
        mDialog = new Dialog(mContent, R.style.WheelSelectDialogStyle);
        mDialog.setContentView(view);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

//        setUpItemNum(7);

        return this;
    }

    public TwoWheelDialog setUpItemNum(int num) {
        if (num > 0 && num < 15) {
            mFirstWheel.setVisibleItems(num);
            mSecondWheel.setVisibleItems(num);
            mThirdWheel.setVisibleItems(num);
        }
        return this;
    }

    public TwoWheelDialog setUpData(String[] firstWheelData, Map<String,
            String[]> secondWheelData,
                                    Map<String, String> lastData) {
        mFirstWheelData = firstWheelData;
        mSecondWheelData = secondWheelData;
        mLastData = lastData;

        mFirstWheel.setVisibility(View.VISIBLE);
        setUpItemNum(7);
        mFirstWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, mFirstWheelData));
        if (mSecondWheelData != null && mSecondWheelData.size() > 0) {
            mSecondWheel.setVisibility(View.VISIBLE);
            updateSecond();
            updateLastData();
        }

        return this;
    }

    private void updateSecond() {
        int firstCurrent = mFirstWheel.getCurrentItem();
        mFirstCurrentName = mFirstWheelData[firstCurrent];
        String[] senondData = mSecondWheelData.get(mFirstCurrentName);
        if (senondData == null) {
            senondData = new String[]{""};
        }
        mSecondWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, senondData));
        mSecondWheel.setCurrentItem(0);
        updateLastData();
    }


    private void updateLastData() {
        int current = mSecondWheel.getCurrentItem();
        mSecondCurrentName = mSecondWheelData.get(mFirstCurrentName)[current];
        mLastCurrentName = mLastData.get(mSecondCurrentName);
    }

    public TwoWheelDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        mDialog.show();
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btn_cancle) {
            mDialog.dismiss();
            callback.onSelectCancle();
        } else if (i == R.id.btn_finish) {
            mDialog.dismiss();
            callback.onSelectFinish(mFirstCurrentName, mSecondCurrentName, mLastCurrentName);
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mFirstWheel) {
            updateSecond();
            updateLastData();
        } else if (wheel == mSecondWheel) {
            updateLastData();
        }
    }

    public TwoWheelDialog setOnSelectFinishListener(SelectCallback listener) {
        callback = listener;
        return this;
    }

    public TwoWheelDialog setLeftBtnText(String text) {
        cancleBtn.setText(text);
        return this;
    }

    public enum WheelColor {
        Blue("#037BFF"), Red("#FD4A2E");

        private String name;

        private WheelColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public TwoWheelDialog setCurrentItem(int firstPosition, int secondPosition) {
        mFirstWheel.setCurrentItem(firstPosition);
        mSecondWheel.setCurrentItem(secondPosition);
        return this;
    }

    public TwoWheelDialog setCurrentItem(String currentItem1, String currentItem2) {

        int position = Arrays.binarySearch(mFirstWheelData, currentItem1);
        mFirstWheel.setCurrentItem(position);

        int position2 = Arrays.binarySearch(mSecondWheelData.get(currentItem1), currentItem2);
        mSecondWheel.setCurrentItem(position2);

        return this;
    }
}
