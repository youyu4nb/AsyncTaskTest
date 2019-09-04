package com.example.youyu4.asynctasktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TimerTask extends AppCompatActivity {
    private static final String TAG = "TimerTask";
    private Button timerBTN;
    private TextView windowTV;
    private EditText settingET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_task);
        timerBTN = findViewById(R.id.timer_btn);
        settingET = findViewById(R.id.setting_et);
        timerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time=Integer.parseInt(settingET.getText().toString());//将字符串转换成整形
                new MyTask().execute(time);
            }
        });
    }

    class MyTask extends AsyncTask<Integer, Integer, String> {
        //后台任务开始执行前调用，用于执行初始化操作
        @Override
        protected void onPreExecute() {
            LogUtil.d(TAG, "onPreExecute: executed");
            super.onPreExecute();
            windowTV = findViewById(R.id.window_tv);
        }

        //后台任务，用于执行耗时操作
        @Override
        protected String doInBackground(Integer... integers) {
            LogUtil.d(TAG, "doInBackground: executed");
            for(int i=integers[0]; i >= 0; i--){
                try{
                    Thread.sleep(1000);
                    publishProgress(i);//调用onProgressUpdate方法
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "计时结束";
        }

        //调用publishProgress()后调用，用于对UI进行操作
        @Override
        protected void onProgressUpdate(Integer... values) {
            LogUtil.d(TAG, "onProgressUpdate: executed");
            super.onProgressUpdate(values);
            windowTV.setText(values[0] + ""  );
        }

        //doInBackground完毕后调用，用于显示结果
        @Override
        protected void onPostExecute(String string) {
            LogUtil.d(TAG, "onPostExecute: executed");
            super.onPostExecute(string);
            windowTV.setText(string);
        }
    }
}

