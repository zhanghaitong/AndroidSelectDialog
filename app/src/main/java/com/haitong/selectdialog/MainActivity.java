package com.haitong.selectdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.haitong.selectdialoglibrary.SelectIntCallback;
import com.haitong.selectdialoglibrary.SelectStringCallback;
import com.haitong.selectdialoglibrary.SelectUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1;
    private Button btn2;
    private Button btn3;

    private String[] firstStrings = {
            "条目1",
            "条目2",
            "条目3",
            "条目4",
            "条目5",
            "条目6",
            "条目7",
            "条目8"
    };

    private Map<String, String> idMap = new HashMap<String, String>() {{
        put("条目1", "1");
        put("条目2", "2");
        put("条目3", "3");
        put("条目4", "4");
        put("条目5", "5");
        put("条目6", "6");
        put("条目7", "7");
        put("条目8", "8");
    }};

    private String[] secondFirstStrings = {
            "一级条目1",
            "一级条目2",
            "一级条目3",
            "一级条目4"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();
        initView();

    }

    private void assignViews() {
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
    }

    private void initView() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                SelectUtils.select1(this, firstStrings, idMap, new SelectStringCallback.Select1Callback() {
                    @Override
                    public void onSelectFinish(String text, String id) {
                        Toast.makeText(MainActivity.this, "选择了" + text + ", id为" + id, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSelectCancle() {
                        Toast.makeText(MainActivity.this, "取消选择", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_2:
                Map<String, String[]> secondSecondMap = new HashMap<>();
                Map<String, String> lastMap = new HashMap<>();
                for (int i = 0; i < secondFirstStrings.length; i++) {
                    String[] strings = new String[10];
                    for (int j = 0; j < strings.length; j++) {
                        strings[j] = "二级条目" + (i + 1) + "_" + (j + 1);
                        lastMap.put(strings[j], (i + 1) * 10 + (j + 1) + "");
                    }
                    secondSecondMap.put(secondFirstStrings[i], strings);
                }

                SelectUtils.select2(this, secondFirstStrings, secondSecondMap, lastMap, new SelectIntCallback.Select2Callback() {
                    @Override
                    public void onSelectFinish(String text, String text2, int id) {
                        Toast.makeText(MainActivity.this, "选择了" + text + "下的" + text2 + ", id为" + id, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSelectCancle() {
                        Toast.makeText(MainActivity.this, "取消选择", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.btn_3:
                Map<String, String[]> thirdSecondMap = new HashMap<>();
                Map<String, String[]> thirdThirdMap = new HashMap<>();
                Map<String, String> last3Map = new HashMap<>();

                for (int i = 0; i < secondFirstStrings.length; i++) {
                    String[] strings = new String[6];
                    for (int j = 0; j < strings.length; j++) {
                        strings[j] = "二级" + "_" + (i + 1) + "_" + (j + 1);

                        String[] string3 = new String[8];

                        for (int k = 0; k < string3.length; k++) {
                            string3[k] = "三级" + "_" + (i + 1) + "_" + (j + 1) + "_" + (k + 1);
                            last3Map.put(string3[k], (i + 1) * 100 + (j + 1) * 10 + k + "");
                        }
                        thirdThirdMap.put(strings[j], string3);

                    }
                    thirdSecondMap.put(secondFirstStrings[i], strings);
                }

                SelectUtils.select3(this, secondFirstStrings, thirdSecondMap, thirdThirdMap, last3Map, new SelectStringCallback.Select3Callback() {
                    @Override
                    public void onSelectFinish(String firstString, String secondString, String thirdString, String id) {
                        Toast.makeText(MainActivity.this, firstString + "   " + secondString + "   " + thirdString + "   " + id, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSelectCancle() {

                    }
                });

                break;
        }
    }
}