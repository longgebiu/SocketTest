package com.example.administrator.sockettest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView2= (TextView) findViewById(R.id.textView2);

        Button btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView3= (TextView) findViewById(R.id.textView3);
                textView3.setText(" ");
                new Thread(runnable).start();
            }
        });
    }
    Handler handler=new Handler(){
        public void handleMessage(Message mes){
            super.handleMessage(mes);
            Log.d("TAG","我进来了");
            Bundle getbundle=mes.getData();
            String getresult=getbundle.getString("result");
            TextView textView3= (TextView) findViewById(R.id.textView3);
            textView3.setText(getresult);
        }
    };
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try {
                Log.d("TAG","runnable触发");
                Socket s = new Socket("10.10.13.76", 2016);
                InputStream is=s.getInputStream();
                OutputStream os =s.getOutputStream();
                PrintStream ps=new PrintStream(os);
                ps.println("hai8888888888888猜猜我是谁");
                DataInputStream dis=new DataInputStream(is);
                Log.d("TAG","发送触发");
                BufferedReader br = new BufferedReader(new InputStreamReader(dis,"gbk"));
                String result=br.readLine();
                Log.d("TAG",result);
                Message message=new Message();
                Bundle bundle=new Bundle();
                bundle.putString("result",result);
                message.setData(bundle);
                handler.sendMessage(message);
               // TextView textView3= (TextView) findViewById(R.id.textView3);
                //textView3.setText(result);

            }
             catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
