package com.example.jack.zssq.url;

/**
 * 位置：
 * 作用：
 * 时间：2017/6/7
 */

public interface ZssqApi {
    /**
     *内容：百度站内搜索
     *
     *
     **/

    public  static  final  String SEARH_HEARD ="http://zhannei.baidu.com/cse/search?s=";


    /**
     *内容：新笔趣阁的请求头
     *
     *
     **/
    public  static  final  String XBIQIG_SEARCH_HEARD="8823758711381329060&ie=utf-8&q=";
    public  static  final  String XBIQIGE_NET_CAPTER="http://www.xxbiquge.com/";
    public  static  final  String XBIQIGE_SELECT_CAPTER="div#list";
    public  static  final  String XBIQIGE_CONTENT="div#content";

    /**
     *内容：笔趣阁个请求头
     *http://zhannei.baidu.com/cse/search?s=9974397986872341910&ie=utf-8&q=重生之九尾凶猫
     *
     **/
    public  static  final  String BIQIGE_SEARCH_HEARD="1393206249994657467&q=";
    public  static  final  String BIQIGE_NET_URL="http://www.xs.la/";
    public  static  final  String BIQUGE_SELECT_CAPTER="div#list";//类选择器
    public  static  final  String BIQUGE_CONTENT="div#content";//类选择器


    /**
     *内容：88读书网
     *http://www.88dushu.com/xiaoshuo/
     *
     **/
    public  static  final  String BABADUSHU_SEARCH_HEARD="9974397986872341910&q=";
    public  static  final  String BABADUSHU_NET_URL="http://www.zwdu.com/book/";
    public  static  final  String BABADUSHU_URL="http://www.zwdu.com";
    public  static  final  String BABADUSHU_SELECT_CAPTER="div#list";//类选择器
    public  static  final  String BABADUSHU_CONTENT="div#content";//类选择器

    /**
    *内容：http://www.88dushu.com/xiaoshuo/
    *
    *
    **/
    public  static  final  String DUSHU_NET_URL="http://www.88dushu.com/xiaoshuo/";
    public  static  final  String DUSHU_SELECT_CAPTER="div.mulu";//类选择器
    public  static  final  String ADUSHU_CONTENT="div.yd_text2";//类选择器
}
