package com.example.youyu4.asynctasktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressBarTask extends AppCompatActivity {
    private static final String TAG = "ProgressBarTask";

    private ProgressBar progressBar;
    private TextView progress;
    private Button downloadBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_task);

        downloadBt = findViewById(R.id.download_btn);
        downloadBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadBt.setEnabled(false);
                downloadBt.setText("正在下载");
                new MyTask().execute();
            }
        });
    }

    class MyTask extends AsyncTask<Integer, Integer, String > {

        //后台任务开始执行前调用，用于执行初始化操作
        @Override
        protected void onPreExecute() {
            LogUtil.d(TAG, "onPreExecute: executed");
            progressBar = findViewById(R.id.progress_bar);
            progress = findViewById(R.id.progress);
            super.onPreExecute();
        }

        //后台任务，用于执行耗时操作
        @Override
        protected String doInBackground(Integer... integers) {//参数类型为AsyncTask第一个泛型参数，返回值为AsyncTask第三个泛型参数
            LogUtil.d(TAG, "doInBackground: executed");
            for(int i = 1; i <= 10; i ++){
                try{
                    Thread.sleep(1000);//每秒下载10%
                    publishProgress(10 * i);//调用onProgressUpdate方法，传递数据values
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "下载完成";
        }

        //调用publishProgress()后调用，用于对UI进行操作
        @Override
        protected void onProgressUpdate(Integer... values) {//接收数据values，参数类型为AsyncTask第二个泛型参数
            LogUtil.d(TAG, "onProgressUpdate: executed");
            super.onProgressUpdate(values);
            //更新下载进度
            progressBar.setProgress(values[0]);
            progress.setText(values[0] + "%");
        }

        //doInBackground完毕后调用，用于显示结果
        @Override
        protected void onPostExecute(String string) {//接收doInBackground的返回值
            LogUtil.d(TAG, "onPostExecute: executed");
            super.onPostExecute(string);
            downloadBt.setText(string);
            downloadBt.setEnabled(true);
        }

    }
}
