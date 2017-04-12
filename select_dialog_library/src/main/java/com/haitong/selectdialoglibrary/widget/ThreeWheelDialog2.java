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
public class ThreeWheelDialog2 implements View.OnClickListener, OnWheelChangedListener {

    private String[] mProNameArray;
    private Map<Integer, String> mProMap = new HashMap<>();                // <省id,省名称>
    private Map<Integer, Map<Integer, String>> mProCityMap = new HashMap<>(); // <省id,<市id,市名称>>
    private Map<Integer, Map<Integer, String>> mCityTownMap = new HashMap<>();   // <市id,<县id,县名称>>

    private String mCurrentProName;
    private String mCurrentCityName;
    private String mCurrentTownName;
    private String mLastCurrentName;

    private int mCurrentProId;
    private int mCurrentCityId;
    private int mCurrentTownId;

    private Context mContent;
    private Dialog mDialog;
    private WheelView mFirstWheel, mSecondWheel, mThirdWheel;
    private TextView cancleBtn, finishBtn;
    private Display display;
    private SelectCallback callback;
    private String[] tmpSecondData;
    private String[] tmpThirdData;
    /**
     * 省id列表
     */
    private List<Integer> mProIdList;
    /**
     * 市id列表
     */
    private List<Integer> mCityIdList;
    /**
     * 县id列表
     */
    private List<Integer> mTownIdList;

    private Map<Integer, String> tmpCityMap;
    private Map<Integer, String> tmpTownMap;
    private int mFirstId;
    private int mSecondId;

    public ThreeWheelDialog2(Context context) {
        this.mContent = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ThreeWheelDialog2 setLeftBtnText(String text) {
        cancleBtn.setText(text);
        return this;
    }

    public ThreeWheelDialog2 builder() {
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

    public ThreeWheelDialog2 setUpItemNum(int num) {
        if (num > 0 && num < 15) {
            mFirstWheel.setVisibleItems(num);
            mSecondWheel.setVisibleItems(num);
            mThirdWheel.setVisibleItems(num);
        }
        return this;
    }

    public ThreeWheelDialog2 setUpData(Map<Integer, String> firstMap,
                                       Map<Integer, Map<Integer, String>> secondMap,
                                       Map<Integer, Map<Integer, String>> lastMap) {

        mProIdList = new ArrayList<>();
        mCityIdList = new ArrayList<>();
        mTownIdList = new ArrayList<>();

        mProMap = firstMap;
        mProCityMap = secondMap;
        mCityTownMap = lastMap;

        //显示三个wheel
        mFirstWheel.setVisibility(View.VISIBLE);
        mSecondWheel.setVisibility(View.VISIBLE);
        mThirdWheel.setVisibility(View.VISIBLE);

        // <省id,省名称>
        // <省id,<市id,市名称>>
        // <市id,<县id,县名称>>

        mProNameArray = new String[mProMap.size()];

        int i = 0;
        for (Map.Entry<Integer, String> entry : mProMap.entrySet()) {
            mProIdList.add(entry.getKey());
            mProNameArray[i++] = entry.getValue();
        }

        // 设置一列显示的item数
        setUpItemNum(7);
        mFirstWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, mProNameArray));

        updateSecond();
        updateThird();
        updateLastData();

        return this;
    }

    private void updateSecond() {
        // <省id,省名称>
        // <省id,<市id,市名称>>
        // <市id,<县id,县名称>>

        mFirstId = mFirstWheel.getCurrentItem();
        mCurrentProId = mProIdList.get(mFirstId);

        mCurrentProName = mProNameArray[mFirstId];

        tmpCityMap = mProCityMap.get(mCurrentProId);

        tmpSecondData = new String[tmpCityMap.size()];
        mCityIdList.clear();

        int i = 0;
        for (Map.Entry<Integer, String> entry : tmpCityMap.entrySet()) {
            mCityIdList.add(entry.getKey());
            tmpSecondData[i++] = entry.getValue();
        }

        if (tmpSecondData != null) {
            mSecondWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, tmpSecondData));
            mSecondWheel.setCurrentItem(0);
            updateThird();
        }
    }

    private void updateThird() {
        mSecondId = mSecondWheel.getCurrentItem();
        mCurrentCityName = tmpSecondData[mSecondId];
        mCurrentCityId = mCityIdList.get(mSecondId);

        tmpTownMap = mCityTownMap.get(mCurrentCityId);
        if (tmpTownMap != null) {
            tmpThirdData = new String[tmpTownMap.size()];
        } else {
            tmpThirdData = new String[]{""};
        }

        mTownIdList.clear();

        int i = 0;
        for (Map.Entry<Integer, String> entry : tmpTownMap.entrySet()) {
            mTownIdList.add(entry.getKey());
            tmpThirdData[i++] = entry.getValue();
        }

        if (tmpThirdData != null) {
            mThirdWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, tmpThirdData));
            mThirdWheel.setCurrentItem(0);
            updateLastData();
        }
    }

    private void updateLastData() {
        int mThirdId = mThirdWheel.getCurrentItem();
        mCurrentTownName = tmpThirdData[mThirdId];
        mLastCurrentName = String.valueOf(mTownIdList.get(mThirdId));
    }

    public ThreeWheelDialog2 setCanceledOnTouchOutside(boolean cancel) {
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

            String firstName = mCurrentProName;
            String secondName = mCurrentCityName;
            String thirdName = mCurrentTownName;

            if (secondName.contains("市辖区") || secondName.equals("县")) {
                secondName = "";
            }
            if (thirdName.contains("市辖区")) {
                thirdName = "";
            }

            callback.onSelectFinish(firstName, secondName, thirdName, mLastCurrentName);
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mFirstWheel) {
            updateSecond();
            updateThird();
            updateLastData();
        } else if (wheel == mSecondWheel) {
            updateThird();
            updateLastData();
        } else if (wheel == mThirdWheel) {
            updateLastData();
        }
    }

    public ThreeWheelDialog2 setOnSelectFinishListener(SelectCallback listener) {
        callback = listener;
        return this;
    }

    public ThreeWheelDialog2 setCurrentItem(int firstPosition, int secondPosition, int thirdPosition) {
        mFirstWheel.setCurrentItem(firstPosition);
        mSecondWheel.setCurrentItem(secondPosition);
        mThirdWheel.setCurrentItem(thirdPosition);
        return this;
    }
}