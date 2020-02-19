package com.XuanRan.UCTOMP3;

import android.*;
import android.content.*;
import android.content.pm.*;
import android.net.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v4.content.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.widget.*;
import java.util.*;

import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity 
{
    //权限数组
    List<String> list_permission=new ArrayList<String>();
    String[] Permiss ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        CheckSDK();
    }

    private void CheckSDK()
    {
        if (Build.VERSION.SDK_INT > 23)
        {
            //读写SD卡
            list_permission.add((Manifest.permission.WRITE_EXTERNAL_STORAGE));
            //将其添加到权限数组之中
            Permiss = list_permission.toArray(new String[list_permission.size()]); 

            //检查是否有权限
            if (ContextCompat.checkSelfPermission(this, list_permission.toString())
                != PackageManager.PERMISSION_GRANTED)
            {
                //显示赞助对话框
                ShowPayMehod();
            }
            else
            {
                showPermission();
            }
        }
    }
    public void ShowPayMehod()
    {
        android.support.v7.app. AlertDialog.Builder alert=new android.support.v7.app. AlertDialog.Builder(this);
        alert.setTitle("捐赠");
        alert.setMessage("本程序由 渲染 开发制作\n\n程序开发不易，请我喝瓶水嘛？");
        alert.setCancelable(true);
        alert.setPositiveButton("好的", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2)
                {
                    ZFBPay(MainActivity.this);
                }
            });
        alert.setNegativeButton("取消", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2)
                {

                }
            });
        alert.setNeutralButton("联系我", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2)
                {
                    Intent intent_d= new Intent();        
                    intent_d.setAction("android.intent.action.VIEW");    
                    Uri content_url = Uri.parse("http://3135419729.qzone.qq.com");   
                    intent_d.setData(content_url);  
                    startActivity(intent_d);

                }
            });
        alert.show();
    }
    public void showPermission()
    {
        android.support.v7.app. AlertDialog.Builder alert=new android.support.v7.app. AlertDialog.Builder(this);
        alert.setTitle("提示");
        alert.setMessage("程序缺少必要的权限，无法运行，请点击下方确定来申请权限");
        alert.setCancelable(false);
        alert.setPositiveButton("确定", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2)
                {
                    ActivityCompat.requestPermissions(MainActivity.this, Permiss, 1);
                }
            });
        alert.setNegativeButton("退出", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2)
                {
                    finish();
                }
            });
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    ShowPayMehod();
                }
                else
                {
                    Toast.makeText(this, "缺失权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    //跳转到支付宝付款界面
    private static void ZFBPay(Context context)
    {
        String webAddress="https://qr.alipay.com/fkx052808bdu1ozojpwrea0?t=1581764492506";
        String intentFullUrl = "intent://platformapi/startapp?saId=10000007&" +
            "clientVersion=3.7.0.0718&qrcode=https%3A%2F%2Fqr.alipay.com%2F" + "fkx052808bdu1ozojpwrea0?t=1581764492506" + "%3F_s" +   //这里的URLcode换成扫码得到的结果
            "%3Dweb-other&_t=1472443966571#Intent;" +
            "scheme=alipayqr;package=com.eg.android.AlipayGphone;end";
        try
        {
            Intent intent = Intent.parseUri(intentFullUrl, Intent.URI_INTENT_SCHEME);
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
