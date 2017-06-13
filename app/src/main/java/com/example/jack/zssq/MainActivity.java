package com.example.jack.zssq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.zssq.url.ZssqApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //    String url = "http://www.xxbiquge.com";
    String url = ZssqApi.BABADUSHU_URL;
    String key, strUrl;

    TextView textView;
    Document doc, doc2, mu2, doc3;
    @BindView(R.id.response_text)
    TextView responseText;
    @BindView(R.id.bt_up)
    Button btUp;
    @BindView(R.id.bt_down)
    Button btDown;
//    @BindView(R.id.ll_bt)
//    LinearLayout llBt;
    @BindView(R.id.bt_menu)
    Button btMenu;


    @BindView(R.id.sb_light)
    SeekBar sbLight;
    private String responstade;
    private int textSize;
    private SeekBar sbTextSize;
    private Switch switch1;
    private ScrollView sc;
    private Switch sw_joindesk;
    private int bookcount;
    private int m;
    private Switch sw_canesk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int WEB_CODE2 = 3;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        key = getIntent().getStringExtra("getCatalogUrl");
        textView = (TextView) findViewById(R.id.response_text);
        sc = (ScrollView) findViewById(R.id.sc_bg);

//判断是否可以拿getInt
        SharedPreferences read = getSharedPreferences("stat", MODE_PRIVATE);
        int stat = read.getInt("STAT", 0);
        Log.e(TAG, "onCreate:SharedPreferences " + stat);
        if (stat == 0) {
        }
        else {
            Log.e(TAG, "onCreate:SharedPreferencesafac " + stat);
            String strUrl = getIntent().getStringExtra("getUrl");
            save("CatalogUrl", strUrl);
        }


        switch1 = (Switch)findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Findcolor();
                    Toast.makeText(getApplicationContext(),"on",Toast.LENGTH_SHORT).show();


                }else {
                    backColor();
                    Toast.makeText(getApplicationContext(),"off",Toast.LENGTH_SHORT).show();
                }
            }
        });

        sw_joindesk = (Switch)findViewById(R.id.sw_joindesk);
        sw_joindesk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    joindesk();
//                    Toast.makeText(getApplicationContext(),"on",Toast.LENGTH_SHORT).show();


                }else {
//                    canneldesk();
//                    Toast.makeText(getApplicationContext(),"off",Toast.LENGTH_SHORT).show();
                }
            }
        });


        sw_canesk = (Switch)findViewById(R.id.sw_canldesk);
        sw_canesk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    canneldesk();


                }else {

                }
            }
        });


        sbTextSize = (SeekBar) findViewById(R.id.sb_textsize);
        initText();
        sendOkhttp(url + key);
        sbTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Toast.makeText(MainActivity.this, "haha", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor textSize=getSharedPreferences("TEXT",MODE_PRIVATE).edit();
                textSize.putString("textSizeSTAT","true");
                textSize.putInt("textSize",i);
                textSize.apply();
                textView.setTextSize((i*2)/10+5);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void canneldesk() {
        Log.e(TAG, "canneldesk: "+"进去取消工具类" );
        //拿到书库书籍的数量
        SharedPreferences pre = getSharedPreferences("booklist", MODE_PRIVATE);
        String booksize = pre.getString("booksize", "");
        bookcount = pre.getInt("bookCount",0);
        Log.e(TAG, "bookcount数量: "+bookcount );
        //找到要寻找的书
        for(int i=1;i<=bookcount;i++)
        {
            Log.e(TAG, "进入内循环" );
            String mstr= String.valueOf(i);
            SharedPreferences readBook = getSharedPreferences("book" + mstr, MODE_PRIVATE);
            String urlmm= readBook.getString("bookUrl", "");
            Log.e(TAG, "canneldesk:testtecanneldeskcanneldeskcanneldesk: "+ mstr);




            if((url + key).contains(urlmm))
            {
                for(int j=i;j<=bookcount;j++)
                {
                    if(j==bookcount);
                    {
//清除最后一个数据
                        String cm= String.valueOf(bookcount);
                        SharedPreferences sp = getSharedPreferences("book"+cm, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();


                        //减少书籍的数量
                        bookcount=bookcount-1;
                        SharedPreferences.Editor editor1 = getSharedPreferences("booklist", MODE_PRIVATE).edit();
                        editor1.putString("booksize", "0");
                        editor1.putInt("bookCount", bookcount);
                        editor1.apply();
                        Toast.makeText(this, "已经从书柜中移除", Toast.LENGTH_SHORT).show();

                    }

                    String kl= String.valueOf(j+1);
                        SharedPreferences sf = getSharedPreferences("book"+kl, MODE_PRIVATE);
                        String bookname = sf.getString("bookName", "");
                        String bookAuther = sf.getString("bookAuther", "");
                        String bookdesc = sf.getString("bookDesc", "");
                        String bookUrl = sf.getString("bookUrl", "");
                        String picUrl = sf.getString("picUrl", "");


                        SharedPreferences.Editor readBookgt = getSharedPreferences("book" + j, MODE_PRIVATE).edit();
                        readBookgt.putString("bookName", bookname);
                        readBookgt.putString("bookAuther", bookAuther);
                        readBookgt.putString("bookDesc", bookdesc);
                        readBookgt.putString("bookUrl", bookUrl);
                         readBookgt.putString("picUrl", picUrl);
                        readBookgt.apply();
                    }




                }



            }
    }






    private void joindesk() {

        Log.e(TAG, "joindesk: "+"进入添加书籍的工具类" );
        SharedPreferences pre = getSharedPreferences("booklist", MODE_PRIVATE);
        String booksize = pre.getString("booksize", "");
        bookcount = pre.getInt("bookCount", 0);
        Log.e(TAG, "joindesk: "+ bookcount);

//增加书籍的数量

//读取书籍的数据
            SharedPreferences readbookdata = getSharedPreferences("book", MODE_PRIVATE);
            String bookname = readbookdata.getString("bookName", "");
            String bookAuther = readbookdata.getString("bookAuther", "");
            String bookdesc = readbookdata.getString("bookDesc", "");
            String bookUrl = readbookdata.getString("bookUrl", "");
            String picUrl = readbookdata.getString("picUrl", "");

            if (bookcount != 0) {
                for (int i = 1; i <= bookcount; i++) {
                    String mstr1 = String.valueOf(i);
                    SharedPreferences rl = getSharedPreferences("book" + mstr1, MODE_PRIVATE);
                    String bookUrlm = rl.getString("bookUrl", "");
                    Log.e(TAG, "joindesk: 保存的url"+bookUrlm);
                    Log.e(TAG, "joindesk: 判断能不能拿的值"+url + key);
                    if ((url + key).contains(bookUrlm)&&!bookUrlm.isEmpty()) {
                        Toast.makeText(this, "你已经添加过这本书。", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (i == bookcount) {
                        String m = String.valueOf(i);
                        //添加书籍数据
                        SharedPreferences.Editor readBook = getSharedPreferences("book" + m, MODE_PRIVATE).edit();
                        readBook.putString("bookName", bookname);
                        readBook.putString("bookAuther", bookAuther);
                        readBook.putString("bookDesc", bookdesc);
                        readBook.putString("bookUrl", bookUrl);
                        readBook.putString("picUrl",picUrl);
                        readBook.apply();



//增加书籍的数量
                        bookcount++;


                        SharedPreferences.Editor editor1 = getSharedPreferences("booklist", MODE_PRIVATE).edit();
                        editor1.putString("booksize", "0");
                        editor1.putInt("bookCount", bookcount);
                        editor1.apply();
//                        String mstr = String.valueOf(bookcount);
//
//                        Log.e(TAG, "joindesk: " + i);
                        Toast.makeText(this, "添加成功。", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }


//                Log.e(TAG, "joindesk: " + bookname + bookUrl);


            }
            else
            {
                SharedPreferences.Editor readBook = getSharedPreferences("book" + "1", MODE_PRIVATE).edit();
                readBook.putString("bookName", bookname);
                readBook.putString("bookAuther", bookAuther);
                readBook.putString("bookDesc", bookdesc);
                readBook.putString("bookUrl", bookUrl);
                readBook.apply();



//增加书籍的数量


                SharedPreferences.Editor editor1 = getSharedPreferences("booklist", MODE_PRIVATE).edit();
                editor1.putString("booksize", "0");
                editor1.putInt("bookCount", 1);
                editor1.apply();
//                        String mstr = String.valueOf(bookcount);
//
//                        Log.e(TAG, "joindesk: " + i);
                Toast.makeText(this, "添加成功。", Toast.LENGTH_SHORT).show();
            }
        }

    private void backColor() {
        SharedPreferences read = getSharedPreferences("COLOR1", MODE_PRIVATE);
        String stat = read.getString("colorSTAT", "");
        if(stat.isEmpty())
        {
            SharedPreferences.Editor textSize=getSharedPreferences("COLOR1",MODE_PRIVATE).edit();
            textSize.putString("colorSTAT","true");
            String textColor ="#303030";
            String  bgColor="#FFFFFF";

            Log.e(TAG, "initText: "+textColor+bgColor);
            textView.setTextColor(Color.parseColor(textColor));
            sc.setBackgroundColor(Color.parseColor(bgColor));
            textSize.apply();
        }
        else
        {
            SharedPreferences getTextSize = getSharedPreferences("COLOR1", MODE_PRIVATE);
            String textColor ="#303030";
            String  bgColor="#FFFFFF";

//            Log.e(TAG, "initText: "+textColor+bgColor);
            textView.setTextColor(Color.parseColor(textColor));
            sc.setBackgroundColor(Color.parseColor(bgColor));

        }

    }

    private void Findcolor() {

        SharedPreferences read = getSharedPreferences("COLOR", MODE_PRIVATE);
        String stat = read.getString("colorSTAT", "");
        if(stat.isEmpty())
        {
            SharedPreferences.Editor textSize=getSharedPreferences("COLOR",MODE_PRIVATE).edit();
            textSize.putString("colorSTAT","true");
            textSize.putString("textColor","#FFFAFA");
            textSize.putString("bgColor","#000000");
            textSize.apply();
        }
        else
        {
            SharedPreferences getTextSize = getSharedPreferences("COLOR", MODE_PRIVATE);
            String textColorsa = getTextSize.getString("textColor","");
            String  bgColorsa= getTextSize.getString("bgColor","");

//
            textView.setTextColor(Color.parseColor(textColorsa));
            sc.setBackgroundColor(Color.parseColor(bgColorsa));

        }


    }

    private void initText() {

//判断字体大小
        SharedPreferences read = getSharedPreferences("TEXT", MODE_PRIVATE);
        String stat = read.getString("textSizeSTAT", "");
        if(stat.isEmpty())
        {
            SharedPreferences.Editor textSize=getSharedPreferences("TEXT",MODE_PRIVATE).edit();
            textSize.putString("textSizeSTAT","true");
            textSize.putInt("textSize",25);
            textSize.apply();
        }
        else
        {
            SharedPreferences getTextSize = getSharedPreferences("TEXT", MODE_PRIVATE);
            int m = getTextSize.getInt("textSize", 0);
            textView.setTextSize((m*2)/10+5);
            Log.e(TAG, "initText: "+m );
            sbTextSize.setProgress(m);
        }




    }


    private void sendOkhttp(final String linkurl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(linkurl)
                            .build();
                    int WEB_CODE2 = 3;
                    Response response = client.newCall(request).execute();
                    if (WEB_CODE2 == 3) {
                        //解决编码问题
                        byte[] responseBytes = response.body().bytes();

                        responstade = new String(responseBytes, "GBK");
                    } else {
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
                Element link = doc.select("div.bookname>h1").first();
                Element pns = doc.select("div#content").first();
//拿书名
                String str,str1;
                try {
                        str = pns.toString().replace("&nbsp;&nbsp;&nbsp;&nbsp;", "   ");
                    str = str.replace("<br>", "");
                    str1 = link.toString().replace("<h1>", "");
                    str1 = str1.replace("</h1>", "");
                    str = str.replace("<div id=\"content\">", "");
                    Log.e(TAG, "run:str1 "+str1);
                    textView.setText(str1 + str);
                }
                catch (RuntimeException e)
            {
                    e.toString();
           }

            }
        });

    }

    @OnClick({R.id.bt_up, R.id.bt_down,R.id.sb_textsize, R.id.sb_light, R.id.switch1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_up:
                Element link = doc.select("div.bottem1").first();
                doc2 = Jsoup.parse(link.html());
                Element link2 = doc2.select("a").first();
                String linkHref = link2.attr("href");

                Element link3 = doc2.select("a").get(1);
                String middle = link3.attr("href");


                if(linkHref.contains(middle)&&linkHref!=middle) {
                    sendOkhttp(url + linkHref);
                }
                else if (linkHref==middle)
                {
                    Toast.makeText(this, "已经是第一章了", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendOkhttp(url + middle+linkHref);
                }


//                Log.e(TAG, "run: bt" + middle + linkHref);
                break;
            case R.id.bt_down:
                Element link4 = doc.select("div.bottem1").first();;
                doc2 = Jsoup.parse(link4.html());
                Element link5 = doc2.select("a").get(2);
                String linkHref1 = link5.attr("href");


                Element link6 = doc2.select("a").get(1);
                String middle2 = link6.attr("href");

                if(linkHref1.contains(middle2)&&linkHref1!=middle2) {
                    sendOkhttp(url + linkHref1);
                }else if (linkHref1==middle2)
                {
                    Toast.makeText(this, "已经是最后一章了", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    sendOkhttp(url + middle2+linkHref1);
                }


//                sendOkhttp(url + middle2 + linkHref1);

                Log.e(TAG, "onViewClickedaa: " + middle2 + linkHref1);
                break;
        }
    }

    @OnClick(R.id.bt_menu)
    public void onViewClicked() {

        Intent intent = new Intent(this, ListMunuActivity.class);
        save("null", "1");
        intent.putExtra("zjlb", land("CatalogUrl"));

        Log.e(TAG, "onViewCsadvadvqavqdfa: " + land("CatalogUrl"));
        startActivity(intent);
        finish();

    }

    public void save(String filename, String i) {
        FileOutputStream outputStream = null;
        BufferedWriter writer = null;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(i);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String land(String filename) {
//        String data="data to save";
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            inputStream = openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }


}
