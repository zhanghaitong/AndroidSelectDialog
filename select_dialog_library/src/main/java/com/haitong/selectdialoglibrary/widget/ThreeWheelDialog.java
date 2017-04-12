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
public class ThreeWheelDialog implements View.OnClickListener, OnWheelChangedListener {

    private String[] mFirstWheelData;
    private Map<String, String[]> mSecondWheelData;
    private Map<String, String[]> mThirdWheelData;
    private Map<String, String> mLastData;

    private String mFirstCurrentName;
    private String mSecondCurrentName;
    private String mThirdCurrentName;
    private String mLastCurrentName;

    private Context mContent;
    private Dialog mDialog;
    private WheelView mFirstWheel, mSecondWheel, mThirdWheel;
    private TextView cancleBtn, finishBtn;
    private Display display;
    private SelectCallback callback;
    private String[] tmpSecondData;
    private String[] tmpThirdData;

    public ThreeWheelDialog(Context context) {
        this.mContent = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ThreeWheelDialog setLeftBtnText(String text) {
        cancleBtn.setText(text);
        return this;
    }


    public ThreeWheelDialog builder() {
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

    public ThreeWheelDialog setUpItemNum(int num) {
        if (num > 0 && num < 15) {
            mFirstWheel.setVisibleItems(num);
            mSecondWheel.setVisibleItems(num);
            mThirdWheel.setVisibleItems(num);
        }
        return this;
    }

    public ThreeWheelDialog setUpData(String[] firstWheelData, Map<String,
            String[]> secondWheelData,
                                      Map<String, String[]> thirdWheelDaat,
                                      Map<String, String> lastData) {
        mFirstWheelData = firstWheelData;
        mSecondWheelData = secondWheelData;
        mThirdWheelData = thirdWheelDaat;
        mLastData = lastData;

        //显示三个wheel
        mFirstWheel.setVisibility(View.VISIBLE);
        mSecondWheel.setVisibility(View.VISIBLE);
        mThirdWheel.setVisibility(View.VISIBLE);
        // 设置一列显示的item数
        setUpItemNum(7);
        mFirstWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, mFirstWheelData));

        updateSecond();
        updateThird();
        updateLastData();

        return this;
    }

    private void updateSecond() {

        int firstCurrent = mFirstWheel.getCurrentItem();

        mFirstCurrentName = mFirstWheelData[firstCurrent];
        tmpSecondData = mSecondWheelData.get(mFirstCurrentName);
        if (tmpSecondData == null) {
            tmpSecondData = new String[]{""};
        }
        mSecondWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, tmpSecondData));
        mSecondWheel.setCurrentItem(0);
        if (mThirdWheelData != null && mThirdWheelData.size() > 0) {
            updateThird();
        }
    }

    private void updateThird() {
        int second = mSecondWheel.getCurrentItem();
        mSecondCurrentName = mSecondWheelData.get(mFirstCurrentName)[second];
        tmpThirdData = mThirdWheelData.get(mSecondCurrentName);
        if (tmpThirdData == null) {
            tmpThirdData = new String[]{""};
        }
        mThirdWheel.setViewAdapter(new ArrayWheelAdapter<>(mContent, tmpThirdData));
        mThirdWheel.setCurrentItem(0);
        updateLastData();
    }

    private void updateLastData() {
        int current = mThirdWheel.getCurrentItem();
        mThirdCurrentName = mThirdWheelData.get(mSecondCurrentName)[current];
        mLastCurrentName = mLastData.get(mThirdCurrentName);
    }

    public ThreeWheelDialog setCanceledOnTouchOutside(boolean cancel) {
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

            String firstName = mFirstCurrentName;
            String secondName = mSecondCurrentName;
            String thirdName = mThirdCurrentName;

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

    public ThreeWheelDialog setOnSelectFinishListener(SelectCallback listener) {
        callback = listener;
        return this;
    }

    public String getLastData() {
        return mLastCurrentName;
    }

    public ThreeWheelDialog setCurrentItem(int firstPosition, int secondPosition, int thirdPosition) {
        mFirstWheel.setCurrentItem(firstPosition);
        mSecondWheel.setCurrentItem(secondPosition);
        mThirdWheel.setCurrentItem(thirdPosition);
        return this;
    }

    public ThreeWheelDialog setCurrentItem(String currentItem1, String currentItem2, String currentItem3) {

        int position = Arrays.binarySearch(mFirstWheelData, currentItem1);
        int position2, position3;

        mFirstWheel.setCurrentItem(position);

//        if (Arrays.binarySearch(tmpSecondData, currentItem2) != -1) {
//            position2 = Arrays.binarySearch(tmpSecondData, currentItem2);
//            position3 = Arrays.binarySearch(tmpThirdData, currentItem3)
//                    == -1 ?
//                    Arrays.binarySearch(tmpThirdData, currentItem2 + currentItem3)
//                    :
//                    Arrays.binarySearch(tmpThirdData, currentItem3);
//        } else {
//            position2 = Arrays.binarySearch(mSecondWheelData.get(currentItem1), currentItem1 + currentItem2);
//            position3 = Arrays.binarySearch(tmpThirdData, currentItem3)
//                    == -1 ?
//                    Arrays.binarySearch(tmpThirdData, currentItem2 + currentItem3)
//                    :
//                    Arrays.binarySearch(tmpThirdData, currentItem3);
//        }

        mSecondWheel.setCurrentItem(Arrays.binarySearch(tmpSecondData, currentItem2));
        mThirdWheel.setCurrentItem(Arrays.binarySearch(tmpThirdData, currentItem3));

        return this;
    }

}
