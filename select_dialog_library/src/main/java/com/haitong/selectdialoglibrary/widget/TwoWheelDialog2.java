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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhhaitong on 2016/9/12 09:38.
 */
public class TwoWheelDialog2 implements View.OnClickListener, OnWheelChangedListener {

    private Map<Integer, String> mBaseMap = new HashMap<>();                    // <基地id,基地名称>
    private Map<Integer, Map<Integer, String>> mBaseGhMap = new HashMap<>();    // <基地id,<大棚id,大棚名称>>

    private int mCurrentBaseId;
    private int mCurrentGhId;

    private String mCurrentBaseName;
    private String mCurrentGhName;

    private List<Integer> mBaseIdList = new ArrayList<>();
    private List<Integer> mGhIdList = new ArrayList<>();

    private Map<Integer, String> mTmpGhMap = new HashMap<>();

    private String[] mBaseNameArray;
    private String[] mGhNameArray;

    private Context mContent;
    private Dialog mDialog;
    private WheelView mFirstWheel, mSecondWheel, mThirdWheel;
    private TextView cancleBtn, finishBtn;
    private Display display;
    private SelectCallback callback;

    public TwoWheelDialog2(Context context) {
        this.mContent = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public TwoWheelDialog2 builder() {
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

    public TwoWheelDialog2 setUpItemNum(int num) {
        if (num > 0 && num < 15) {
            mFirstWheel.setVisibleItems(num);
            mSecondWheel.setVisibleItems(num);
            mThirdWheel.setVisibleItems(num);
        }
        return this;
    }

    public TwoWheelDialog2 setUpData(Map<Integer, String> mBaseMap, Map<Integer, Map<Integer, String>> mBaseGhMap) {
        this.mBaseMap = mBaseMap;
        this.mBaseGhMap = mBaseGhMap;

        mBaseNameArray = new String[mBaseGhMap.size()];

        mFirstWheel.setVisibility(View.VISIBLE);
        setUpItemNum(7);

        mBaseIdList.clear();
        int i = 0;
        for (Map.Entry<Integer, String> entry : mBaseMap.entrySet()) {
            mBaseNameArray[i++] = entry.getValue();
            mBaseIdList.add(entry.getKey());
        }

        mFirstWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, mBaseNameArray));
        if (mBaseGhMap != null && mBaseGhMap.size() > 0) {
            mSecondWheel.setVisibility(View.VISIBLE);
            updateSecond();
            updateLastData();
        }

        return this;
    }

    private void updateSecond() {
        int mFirstId = mFirstWheel.getCurrentItem();
        mCurrentBaseName = mBaseNameArray[mFirstId];
        mCurrentBaseId = mBaseIdList.get(mFirstId);

        mTmpGhMap = mBaseGhMap.get(mCurrentBaseId);

        mGhNameArray = new String[mTmpGhMap.size()];
        int i = 0;
        mGhIdList.clear();
        for (Map.Entry<Integer, String> entry : mTmpGhMap.entrySet()) {
            mGhIdList.add(entry.getKey());
            mGhNameArray[i++] = entry.getValue();
        }

        mSecondWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, mGhNameArray));
        mSecondWheel.setCurrentItem(0);
        updateLastData();
    }

    private void updateLastData() {
        int current = mSecondWheel.getCurrentItem();
        mCurrentGhName = mGhNameArray[current];
        mCurrentGhId = mGhIdList.get(current);
    }

    public TwoWheelDialog2 setCanceledOnTouchOutside(boolean cancel) {
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
            callback.onSelectFinish(mCurrentBaseName, mCurrentGhName, String.valueOf(mCurrentGhId));
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

    public TwoWheelDialog2 setOnSelectFinishListener(SelectCallback listener) {
        callback = listener;
        return this;
    }

    public TwoWheelDialog2 setLeftBtnText(String text) {
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

    public TwoWheelDialog2 setCurrentItem(int firstPosition, int secondPosition) {
        mFirstWheel.setCurrentItem(firstPosition);
        mSecondWheel.setCurrentItem(secondPosition);
        return this;
    }
}
