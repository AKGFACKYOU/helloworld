package com.example.jack.zssq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.jack.zssq.book.Book;
import com.example.jack.zssq.url.ZssqApi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BookListActivity extends AppCompatActivity {
    private static final String TAG = "BookListActivity";

    String Urlsearch=ZssqApi.SEARH_HEARD+ ZssqApi.BABADUSHU_SEARCH_HEARD;

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
    private Element links;
    private BookAdapter adapter;
    private RecyclerView recyclerView;
    private Element picUrl;
    private Document picUrle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        search=getIntent().getStringExtra("search_context");

        sendOkhttBookList(Urlsearch+search);

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
//                    Log.e(TAG, "onCreate: " + responstade);
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

                    //拿图片地址
                    picUrl = booklist_dc.select("div.result-game-item-pic").get(i);
                    picUrle = Jsoup.parse(picUrl.html());
                    Log.e(TAG, "run: 这是选择后的地址" +picUrle.toString());
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
                    recyclerView = (RecyclerView) findViewById(R.id.rc_booklist);
                    LinearLayoutManager layoutManager=new LinearLayoutManager(BookListActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter = new BookAdapter(booksList);
                    recyclerView.setAdapter(adapter);

                }


            }
        });

    }


}
