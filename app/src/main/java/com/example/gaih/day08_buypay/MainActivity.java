package com.example.gaih.day08_buypay;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.gaih.day08_yuancheng.Iservice;

public class MainActivity extends AppCompatActivity {

    private Myconn conn;
    private Iservice iservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("com.alipay");
        intent.setPackage("com.example.gaih.day08_yuancheng");
        conn = new Myconn();
        bindService(intent,conn,BIND_AUTO_CREATE);
    }
    public void click(View view){
        try {
            boolean result = iservice.callPay("abc","123",100);
            if (result){
                Toast.makeText(getApplication(),"成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplication(),"失败",Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e){
            e.printStackTrace();
        }

    }
    private class Myconn implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iservice = Iservice.Stub.asInterface(service);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
