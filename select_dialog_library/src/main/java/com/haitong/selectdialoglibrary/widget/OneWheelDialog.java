package com.haitong.selectdialoglibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender;
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
public class OneWheelDialog extends IntentSender.SendIntentException implements View.OnClickListener, OnWheelChangedListener {

    private String[] mFirstWheelData;
    private Map<String, String> mLastData;

    private String mFirstCurrentName;
    private String mLastCurrentName;

    private Context mContent;
    private Dialog mDialog;
    private WheelView mFirstWheel;
    private TextView cancleBtn, finishBtn;
    private Display display;
    private SelectCallback callback;

    public OneWheelDialog(Context context) {

        this.mContent = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public OneWheelDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(mContent).inflate(
                R.layout.dialog_wheel, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        mFirstWheel = (WheelView) view.findViewById(R.id.wheel_first);
        cancleBtn = (TextView) view.findViewById(R.id.btn_cancle);
        finishBtn = (TextView) view.findViewById(R.id.btn_finish);

        mFirstWheel.addChangingListener(this);

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

    public OneWheelDialog setLeftBtnText(String text) {
        cancleBtn.setText(text);
        return this;
    }

    public OneWheelDialog setUpItemNum(int num) {
        if (num > 0 && num < 15) {
            mFirstWheel.setVisibleItems(num);
        }
        return this;
    }

    public OneWheelDialog setUpData(String[] firstWheelData, Map<String, String> lastData) {
        mFirstWheelData = firstWheelData;
        mLastData = lastData;

        mFirstWheel.setVisibility(View.VISIBLE);
        setUpItemNum(7);
        mFirstWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, mFirstWheelData));

        updateLastData();

        return this;
    }


    private void updateLastData() {
        int current = mFirstWheel.getCurrentItem();
        mFirstCurrentName = mFirstWheelData[current];
        mLastCurrentName = mLastData.get(mFirstCurrentName);
    }

    public OneWheelDialog setCanceledOnTouchOutside(boolean cancel) {
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
            callback.onSelectFinish(mFirstCurrentName, mLastCurrentName);

        }
    }

    public OneWheelDialog setOnSelectFinishListener(SelectCallback listener) {
        callback = listener;
        return this;
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mFirstWheel) {
            updateLastData();
        }
    }

    public OneWheelDialog setCurrentItem(int position) {
        mFirstWheel.setCurrentItem(position);
        return this;
    }

    public OneWheelDialog setCurrentItem(String currentItem) {

        int position = Arrays.binarySearch(mFirstWheelData, currentItem);

        mFirstWheel.setCurrentItem(position);
        return this;
    }
}