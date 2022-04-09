package com.kanxue.picload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private HttpURLConnection httpURLConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List <String> data=new ArrayList<>();
        data.add("title one ");
        data.add("title two ");
        data.add("title three ");
        data.add("title four ");
        data.add("title five ");

    }

    public void loaPic(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                //设置为true也就是图片不载入内存，拿到图片的宽和高
                options.inJustDecodeBounds = true;
                //拿到图片大小
                BitmapFactory.decodeResource(getResources(), R.mipmap.test_pic, options);

                ImageView imageView = findViewById(R.id.result_image);

                int outHeight = options.outHeight;
                int outWidth = options.outWidth;

                //拿到控件的尺寸
                int measuredHeight = imageView.getMeasuredHeight();
                int measuredWidth = imageView.getMeasuredWidth();




                options.inSampleSize = 1;

                //图片的宽度/控件的宽度
                //图片的高度/控件的高度
                //取两者最小值,值越大图片就越模糊
                if (outHeight > measuredHeight || outWidth > measuredWidth) {
                    int subHeight = outHeight / measuredHeight;
                    int subWidth = outWidth / measuredWidth;
                    options.inSampleSize = subHeight > subWidth ? subWidth : subHeight;
                }
                //需要设置回原来的值，否则就会返回null
                options.inJustDecodeBounds = false;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test_pic, options);
                        imageView.setImageBitmap(bitmap);

                    }
                });


//                try {
//                    URL url =new URL("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.daimg.com%2Fuploads%2Fallimg%2F151217%2F3-15121GHI7.jpg&refer=http%3A%2F%2Fimg.daimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652066074&t=06f4a17afe48a051f1ed4190710b14d5");
//                    httpURLConnection = (HttpURLConnection)url.openConnection();
//                    httpURLConnection.setConnectTimeout(10000);
//                    httpURLConnection.setRequestMethod("GET");
//                    httpURLConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
//                    httpURLConnection.setRequestProperty("Accept","*/*");
//                    httpURLConnection.connect();
//                    int responseCode= httpURLConnection.getResponseCode();
//                    if(responseCode==HttpURLConnection.HTTP_OK){
//                        InputStream inputStream =httpURLConnection.getInputStream();
//                        //转成bitmap
//                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
//                        //更新UI
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ImageView imageView=findViewById(R.id.result_image);
//                                imageView.setImageBitmap(bitmap);
//                            }
//                        });
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


            }
        }).start();
    }
}