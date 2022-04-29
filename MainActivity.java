package com.example.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
      public void onClick(View v) {
        if (v.getId()==R.id.send_request){
            sendRequestWithHttpURLConnection();

                         }
            }

    private void sendRequestWithHttpURLConnection() {
        //开启新线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try {
                    //获取实例，使用HttpURLConnection发出一条http请求，目标地址为百度首页
                    URL url=new URL("https://weather1.sina.cn/?vt=4");
                    connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");  //设置HTTP请求所使用的方法
                    connection.setConnectTimeout(8000);  //连接超时时间
                    connection.setReadTimeout(8000);  //读取超时时间
                    InputStream is=connection.getInputStream();  //获取到服务器返回的输入流
                    //下面对获取到的输入流进行读取
                    reader=new BufferedReader(new InputStreamReader(is));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    //读取结果传入showResponse方法中
                    showResponse(response.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private  void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
