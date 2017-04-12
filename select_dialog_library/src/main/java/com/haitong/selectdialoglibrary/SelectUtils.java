package com.haitong.selectdialoglibrary;

import android.content.Context;

import com.haitong.selectdialoglibrary.widget.OneWheelDialog;
import com.haitong.selectdialoglibrary.widget.SelectCallback;
import com.haitong.selectdialoglibrary.widget.ThreeWheelDialog;
import com.haitong.selectdialoglibrary.widget.ThreeWheelDialog2;
import com.haitong.selectdialoglibrary.widget.TwoWheelDialog;
import com.haitong.selectdialoglibrary.widget.TwoWheelDialog2;

import java.util.Map;

/**
 * Created by zhhaitong on 2017-03-31 18:53.
 */

public class SelectUtils {

    /**
     * 选择只有一列数据
     *
     * @param context   上下文
     * @param firstData 要选择的字符串数组
     * @param lastMap   字符串与结果ID对应的map
     * @param callback  选择完成回调
     */
    public static void select1(Context context,
                               String[] firstData,
                               Map<String, String> lastMap,
                               final SelectStringCallback.Select1Callback callback) {
        new OneWheelDialog(context).builder()
                .setUpData(firstData, lastMap)
                .setOnSelectFinishListener(new SelectCallback() {
                    @Override
                    public void onSelectFinish(String... selectedStrings) {
                        callback.onSelectFinish(selectedStrings[0], selectedStrings[1]);
                    }

                    @Override
                    public void onSelectCancle() {
                        callback.onSelectCancle();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .show();
    }

    /**
     * 级联选择两列
     *
     * @param context       上下文
     * @param firstData     要选择的字符串数组
     * @param secondDataMap 第二列数据(key与第一列中的各项对应)
     * @param lastMap       字符串与结果ID对应的map
     * @param callback      选择完成回调
     */
    public static void select2(Context context,
                               String[] firstData,
                               Map<String, String[]> secondDataMap,
                               Map<String, String> lastMap,
                               final SelectStringCallback.Select2Callback callback) {
        new TwoWheelDialog(context).builder()
                .setUpData(firstData, secondDataMap, lastMap)
                .setOnSelectFinishListener(new SelectCallback() {
                    @Override
                    public void onSelectFinish(String... selectedStrings) {
                        callback.onSelectFinish(selectedStrings[0], selectedStrings[1], selectedStrings[2]);
                    }

                    @Override
                    public void onSelectCancle() {
                        callback.onSelectCancle();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .show();
    }

    /**
     * 级联选择三列
     *
     * @param context       上下文
     * @param firstData     要选择的字符串数组
     * @param secondDataMap 第二列数据(key与第一列中的各项对应)
     * @param thirdDataMap  第三列列数据(key与第二列Map中的value对应)
     * @param lastMap       字符串与结果ID对应的map
     * @param callback      选择完成回调
     */
    public static void select3(Context context,
                               String[] firstData,
                               Map<String, String[]> secondDataMap,
                               Map<String, String[]> thirdDataMap,
                               Map<String, String> lastMap,
                               final SelectStringCallback.Select3Callback callback) {
        new ThreeWheelDialog(context).builder()
                .setUpData(firstData, secondDataMap, thirdDataMap, lastMap)
                .setOnSelectFinishListener(new SelectCallback() {
                    @Override
                    public void onSelectFinish(String... selectedStrings) {
                        callback.onSelectFinish(selectedStrings[0], selectedStrings[1],
                                selectedStrings[2], selectedStrings[3]);
                    }

                    @Override
                    public void onSelectCancle() {
                        callback.onSelectCancle();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .show();
    }

    /**
     * 此方法是为避免级联选择时值重复的情况
     *
     * @param context  上下文
     * @param firstMap 第一列数据<字符串对应ID， 字符串值>
     * @param lastMap  第二列数据<第一列字符串ID， <第二列字符串对应ID， 第二列字符串值>>
     * @param callback 选择完成回调
     */
    public static void select2(Context context,
                               Map<Integer, String> firstMap,
                               Map<Integer, Map<Integer, String>> lastMap,
                               final SelectStringCallback.Select2Callback callback) {
        new TwoWheelDialog2(context).builder()
                .setUpData(firstMap, lastMap)
                .setOnSelectFinishListener(new SelectCallback() {
                    @Override
                    public void onSelectFinish(String... selectedStrings) {
                        callback.onSelectFinish(selectedStrings[0], selectedStrings[1], selectedStrings[2]);
                    }

                    @Override
                    public void onSelectCancle() {
                        callback.onSelectCancle();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .show();
    }

    /**
     * 此方法是为避免级联选择时值重复的情况
     *
     * @param context   上下文
     * @param firstMap  第一列数据<字符串对应ID， 字符串值>
     * @param secondMap 第二列数据<第一列字符串ID， <第二列字符串对应ID， 第二列字符串值>>
     * @param lastMap   第三列数据<第二列字符串ID， <第三列字符串对应ID， 第三列字符串值>>
     * @param callback  选择完成回调
     */
    public static void select3(Context context,
                               Map<Integer, String> firstMap,
                               Map<Integer, Map<Integer, String>> secondMap,
                               Map<Integer, Map<Integer, String>> lastMap,
                               final SelectStringCallback.Select3Callback callback) {
        new ThreeWheelDialog2(context).builder()
                .setUpData(firstMap, secondMap, lastMap)
                .setOnSelectFinishListener(new SelectCallback() {
                    @Override
                    public void onSelectFinish(String... selectedStrings) {
                        callback.onSelectFinish(selectedStrings[0], selectedStrings[1],
                                selectedStrings[2], selectedStrings[3]);
                    }

                    @Override
                    public void onSelectCancle() {
                        callback.onSelectCancle();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .show();
    }


    /**
     * 选择只有一列数据
     *
     * @param context   上下文
     * @param firstData 要选择的字符串数组
     * @param lastMap   字符串与结果ID对应的map
     * @param callback  选择完成回调
     */
    public static void select1(Context context,
                               String[] firstData,
                               Map<String, String> lastMap,
                               final SelectIntCallback.Select1Callback callback) {
        select1(context, firstData, lastMap, new SelectStringCallback.Select1Callback() {
            @Override
            public void onSelectFinish(String text, String id) {
                callback.onSelectFinish(text, Integer.parseInt(id));
            }

            @Override
            public void onSelectCancle() {

            }
        });
    }

    /**
     * 级联选择两列
     *
     * @param context       上下文
     * @param firstData     要选择的字符串数组
     * @param secondDataMap 第二列数据(key与第一列中的各项对应)
     * @param lastMap       字符串与结果ID对应的map
     * @param callback      选择完成回调
     */
    public static void select2(Context context,
                               String[] firstData,
                               Map<String, String[]> secondDataMap,
                               Map<String, String> lastMap,
                               final SelectIntCallback.Select2Callback callback) {
        select2(context, firstData, secondDataMap, lastMap, new SelectStringCallback.Select2Callback() {
            @Override
            public void onSelectFinish(String text, String text2, String id) {
                callback.onSelectFinish(text, text2, Integer.parseInt(id));
            }

            @Override
            public void onSelectCancle() {

            }
        });
    }

    /**
     * 级联选择三列
     *
     * @param context       上下文
     * @param firstData     要选择的字符串数组
     * @param secondDataMap 第二列数据(key与第一列中的各项对应)
     * @param thirdDataMap  第三列列数据(key与第二列Map中的value对应)
     * @param lastMap       字符串与结果ID对应的map
     * @param callback      选择完成回调
     */
    public static void select3(Context context,
                               String[] firstData,
                               Map<String, String[]> secondDataMap,
                               Map<String, String[]> thirdDataMap,
                               Map<String, String> lastMap,
                               final SelectIntCallback.Select3Callback callback) {
        select3(context, firstData, secondDataMap, thirdDataMap, lastMap, new SelectStringCallback.Select3Callback() {
            @Override
            public void onSelectFinish(String text, String text2, String text3, String id) {
                callback.onSelectFinish(text, text2, text3, Integer.parseInt(id));
            }

            @Override
            public void onSelectCancle() {

            }
        });
    }

    /**
     * 此方法是为避免级联选择时值重复的情况
     *
     * @param context  上下文
     * @param firstMap 第一列数据<字符串对应ID， 字符串值>
     * @param lastMap  第二列数据<第一列字符串ID， <第二列字符串对应ID， 第二列字符串值>>
     * @param callback 选择完成回调
     */
    public static void select2(Context context,
                               Map<Integer, String> firstMap,
                               Map<Integer, Map<Integer, String>> lastMap,
                               final SelectIntCallback.Select2Callback callback) {
        select2(context, firstMap, lastMap, new SelectStringCallback.Select2Callback() {
            @Override
            public void onSelectFinish(String firstString, String secondString, String id) {
                callback.onSelectFinish(firstString, secondString, Integer.parseInt(id));
            }

            @Override
            public void onSelectCancle() {

            }
        });
    }

    /**
     * 此方法是为避免级联选择时值重复的情况
     *
     * @param context   上下文
     * @param firstMap  第一列数据<字符串对应ID， 字符串值>
     * @param secondMap 第二列数据<第一列字符串ID， <第二列字符串对应ID， 第二列字符串值>>
     * @param lastMap   第三列数据<第二列字符串ID， <第三列字符串对应ID， 第三列字符串值>>
     * @param callback  选择完成回调
     */
    public static void select3(Context context,
                               Map<Integer, String> firstMap,
                               Map<Integer, Map<Integer, String>> secondMap,
                               Map<Integer, Map<Integer, String>> lastMap,
                               final SelectIntCallback.Select3Callback callback) {
        select3(context, firstMap, secondMap, lastMap, new SelectStringCallback.Select3Callback() {
            @Override
            public void onSelectFinish(String firstString, String secondString, String thirdString, String id) {
                callback.onSelectFinish(firstString, secondString, thirdString, Integer.parseInt(id));
            }

            @Override
            public void onSelectCancle() {

            }
        });
    }
}