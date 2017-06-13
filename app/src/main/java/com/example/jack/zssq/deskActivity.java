package com.example.jack.zssq;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jack.zssq.adapter.ContentAdapter;
import com.example.jack.zssq.book.Book;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class deskActivity extends AppCompatActivity{
    private static final String TAG = "BookListActivity";
    private List<Book> bookList=new ArrayList<>();
    String Urlsearch= ZssqApi.SEARH_HEARD+ ZssqApi.BABADUSHU_SEARCH_HEARD;

    //    String Urlsearch="http://zhannei.baidu.com/cse/search?s=8823758711381329060&ie=utf-8&q="
    String search;
    String []bookname=new String[10];
    String []desc=new String[10];
    String []auther=new String[10];
    String []date=new String[10];
    String []url=new String[10];
    String[] picUrl1=new String[10];
    Document booklist_dc,urlsearch_dc;
    private List<Book> booksList=new ArrayList<>();
    private List<Book> booksList2=new ArrayList<>();
    private Element links;
    private BookAdapter bookadapter,bookadapter2;
    private RecyclerView recyclerView,recyclerView2;
    String str;


    @BindView(R.id.bt_desk)
    Button btDesk;
    @BindView(R.id.bt_find)
    Button btFind;
    @BindView(R.id.bt_user)
    Button btUser;
    private ViewPager viewPager;

          // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;
    private BookAdapter Bookadapter;
    private List<View> views;
    private EditText et;
    private Button bt;
    private Button bt1;
    private RecyclerView rv;
    private Element picUrl;
    private Document picUrle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk);
        ButterKnife.bind(this);
        //判断书架内是否有书




        if(land("count").isEmpty())
        {
            save("count","0");
        }

        viewPager = (ViewPager) findViewById(R.id.vp_content);
        View page_01 = View.inflate(deskActivity.this, R.layout.page_desk, null);
        View page_02 = View.inflate(deskActivity.this, R.layout.page_find, null);
        View page_03 = View.inflate(deskActivity.this, R.layout.page_user, null);

            views = new ArrayList<View>();
            views.add(page_01);
            views.add(page_02);
           views.add(page_03);


        this.adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        viewPager.setCurrentItem(0);
                        loaddesk();


                        break;
                    case 1:
                        viewPager.setCurrentItem(1);


                        bt1 = (Button) findViewById(R.id.bt_findbook_fragment);
                        et = (EditText) findViewById(R.id.et_findbook_fragment);
                        bt1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                booksList.clear();
                               if( et.getText()==null)
                               {
                                   Toast.makeText(deskActivity.this, "你还没有输入内容", Toast.LENGTH_SHORT).show();
                               }
                               else
                               {
                                   str=et.getText().toString();
                                   sendOkhttBookList(Urlsearch+str);
                               }
                            }
                        });


//                        search=getIntent().getStringExtra("search_context");








                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.bt_desk, R.id.bt_find, R.id.bt_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.bt_desk:

                viewPager.setCurrentItem(0);
                loaddesk();
                break;

            case R.id.bt_find:
                viewPager.setCurrentItem(1);
                Spinner spinner = (Spinner) findViewById(R.id.spinner_find_fragment);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override

                    public void onItemSelected(AdapterView<?> parent, View view,

                                               int pos, long id) {



                        String[] languages = getResources().getStringArray(R.array.weburl);

                        Toast.makeText(deskActivity.this, "你点击的是:"+languages[pos], Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                break;

            case R.id.bt_user:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    private void sendOkhttBookList(final String linkurll) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(linkurll)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responstade = response.body().string();
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
//                Log.e(TAG, "run: "+responstade );
                booklist_dc = Jsoup.parse(responstade);
                for(int i=0;i<=9;i++) {

                    links = booklist_dc.select("div.result-game-item-detail").get(i);
                    urlsearch_dc = Jsoup.parse(links.html());

                    Element links1 = urlsearch_dc.select("a").first();
                    String booklink = links1.attr("href");
//                    Log.e(TAG, "runwhy: " + booklink);
                    //拿到书籍的地址
                    url[i]=booklink;

                    String name=links1.attr("title");
//                    Log.e(TAG, "runwhy: " + name);
                    bookname[i]=name;

                   picUrl = booklist_dc.select("div.result-game-item-pic").get(i);
                    picUrle = Jsoup.parse(picUrl.html());

                    Element picUrl_e= picUrle.select("img").first();
                    String PicSting= picUrl_e.attr("src");
                    picUrl1[i] = PicSting;
                    Log.e(TAG, "run: 这是图片的地址" +picUrl1[i]);



//拿描述
                    Element linksp = urlsearch_dc.select("p").first();
                    desc[i]=linksp.toString().replace("<p class=\"result-game-item-desc\">","");
                    desc[i]=desc[i].replace("<em>","");
                    desc[i]=desc[i].replace("</em>","");

                    Element linkauther = urlsearch_dc.select("span").get(1);

                    auther[i]=linkauther.toString().replace("<span>","");
                    auther[i]=auther[i].replace("</span>","");

                    Element linkdate = urlsearch_dc.select("span").get(5);
//                    Log.e(TAG, "runwhy: " + linkdate.toString());
                    date[i]=linkdate.toString().replace("<span class=\"result-game-item-info-tag-title\">","");
                    date[i]=date[i].replace("</span>","");


                    Book book1 = new Book(url[i], bookname[i], desc[i], auther[i], "1231e", date[i], null,picUrl1[i]);
                    booksList.add(book1);
                    recyclerView = (RecyclerView) findViewById(R.id.rc_findbook_fragment);
                    recyclerView.setLayoutManager(new LinearLayoutManager(deskActivity.this));
                    bookadapter = new BookAdapter(booksList);
                    recyclerView.setAdapter(bookadapter);

                }


            }
        });

    }
    public void save(String filename,String i)
    {
        String data="data to save";
        FileOutputStream outputStream=null;
        BufferedWriter writer=null;
        try
        {
            outputStream=openFileOutput(filename, Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(i);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                if(writer!=null)
                {
                    writer.close();
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
    public String land(String filename)
    {

        FileInputStream inputStream=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try
        {
            inputStream=openFileInput(filename);
            reader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=reader.readLine())!=null)
            {
                content.append(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                if(reader!=null)
                {
                    reader.close();
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

public void   loaddesk()
{
    SharedPreferences pre=getSharedPreferences("booklist",MODE_PRIVATE);
    String booksize=pre.getString("booksize","");
    int count=pre.getInt("bookCount",0);



    if(booksize.isEmpty())
    {
        SharedPreferences.Editor editor1=getSharedPreferences("booklist",MODE_PRIVATE).edit();
        editor1.putString("booksize","0");
        editor1.putInt("bookCount",0);
        editor1.apply();
//
    }
    else
    {
        //加载书库里面的书
        booksList2.clear();
        for (int i=0;i<=count;i++)
        {
            int m=i+1;
            String mstr= String.valueOf(m);
            SharedPreferences readBook=getSharedPreferences("book"+mstr,MODE_PRIVATE);
            String name=readBook.getString("bookName","");
            String auther=readBook.getString("bookAuther","");
            String desc=readBook.getString("bookDesc","");
            String bookUrl=readBook.getString("bookUrl","");
            String picUrl=readBook.getString("picUrl","");
            Log.e(TAG,"数据的数量"+mstr);
            Log.e(TAG, "onCreate:这是首页获取的数据 "+name+bookUrl);
            if(bookUrl.isEmpty())
            {
                break;
            }
            else
            {
                Log.e(TAG, "onCreate: 数据"+name+auther+desc+bookUrl);
                Book booksr= new Book(bookUrl, name, desc, auther, "1231e", null, null,picUrl);
                booksList2.add(booksr);
                recyclerView2 = (RecyclerView) findViewById(R.id.rc_desk_book);
                LinearLayoutManager layoutManagerl=new LinearLayoutManager(deskActivity.this);
                recyclerView2.setLayoutManager(layoutManagerl);
                bookadapter2 = new BookAdapter(booksList2);
                recyclerView2.setAdapter(bookadapter2);

            }
        }
    }
}
}
