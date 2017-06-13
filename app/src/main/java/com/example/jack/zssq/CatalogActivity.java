package com.example.jack.zssq;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jack.zssq.book.Catalog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CatalogActivity extends AppCompatActivity {
    final static int  WEB_CODE=3;

    private static final String TAG ="CatalogActivity" ;
    private List<Catalog> catalogList=new ArrayList<>();
    Document doc,doc2;
    final  String []catelog=new String[5000];
    final  String []catelogUrl=new String[5000];
    private Document catalogDoc;
    private String responstade;
    String strList;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
//获得书籍的链接
        str=getIntent().getStringExtra("url");

//保存在顶部

        SharedPreferences.Editor editor1=getSharedPreferences("book",MODE_PRIVATE).edit();
        editor1.putString("bookName",getIntent().getStringExtra("bookname"));
        editor1.putString("bookAuther",getIntent().getStringExtra("bookauther"));
        editor1.putString("bookDesc",getIntent().getStringExtra("desc"));
        editor1.putString("bookUrl",getIntent().getStringExtra("url"));
        editor1.putString("picUrl",getIntent().getStringExtra("picUrl"));
        editor1.apply();
//        Log.e(TAG, "onCreate:(\"book\",MODE_PRIVATE).edit() "+getIntent().getStringExtra("bookname")+getIntent().getStringExtra("url"));

   sendOkhttp(str);
    }
    /**
    *内容： intent.putExtra("bookname",book.getBookname());
     intent.putExtra("bookauther",book.getAuther());
     intent.putExtra("desc",book.getDesc());
    *
    *
    **/
    private void sendOkhttp(final String linkurl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(linkurl)
                            .build();
                    Log.e(TAG, "onCreate: " + linkurl);
                    Response response = client.newCall(request).execute();

                    if(WEB_CODE==3) {
                        //解决编码问题
                        byte[] responseBytes=response.body().bytes();

                        responstade = new String(responseBytes,"GBK");
                    }else
                    {
                        responstade = response.body().string();
                    }

                    showResponse(responstade);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void showResponse(final String responstade) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                doc = Jsoup.parse(responstade);
                Element pns = doc.select("div#list").first();

                doc2=Jsoup.parse(pns.html());
//
                int count=0;
                int m=1;
        try {


                for(int i=0;i<=m;i++)
                {

                    Element pns1 = doc2.select("a").get(i);
                    Element str=pns1;
                    if(!isContainChinese(str.toString()))

                    {
                        m=0;
                    }
                    catelog[i+1]=pns1.toString();
//                    Log.e(TAG, "run: "+pns1.toString() +count);
                    count++;
                }
        }
        catch (RuntimeException e)
        {
                e.toString();
            Log.e(TAG, "showResponse: "+count );
        }


                for (int i=0;i<count;i++)
                {
                    String html=catelog[i];
                    if(html!=null) {
                        catalogDoc = Jsoup.parse(html);
                        Element link=catalogDoc.select("a").first();

                        //获得链接
                        String linkHref = link.attr("href");


                        //获得章节
                        catelog[i]=catelog[i].replace("<a href=\""+linkHref+"\">","");
                        catelog[i]=catelog[i].replace("</a>","");
                        Log.e(TAG, "ll: "+catelog[i]+linkHref);

                        SharedPreferences.Editor read = getSharedPreferences("stat", MODE_PRIVATE).edit();
                        read.putInt("STAT", 1);
                        read.apply();
                        Intent intent=new Intent(CatalogActivity.this,MainActivity.class);
                        intent.putExtra("getCatalogUrl",linkHref);
                        intent.putExtra("getUrl",str);
                        startActivity(intent);
                        finish();

                    }
                    else
                    {
                        Log.e(TAG, "showResponse: "+i+"处有空值" );
                    }



                }

            }
        });


            }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    public String subString(String str)
    {

        return null;
    }
    public byte[] gbk2utf8(String chenese){
        char c[] = chenese.toCharArray();
        byte [] fullByte =new byte[3*c.length];
        for(int i=0; i<c.length; i++){
            int m = (int)c[i];
            String word = Integer.toBinaryString(m);
            // System.out.println(word);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            //补零
            for(int j=0; j<len; j++){
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");

//             System.out.println(sb.toString());

            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i*3] = bf[0];
            bf[1] = b1;
            fullByte[i*3+1] = bf[1];
            bf[2] = b2;
            fullByte[i*3+2] = bf[2];

        }
        return fullByte;
    }
}

