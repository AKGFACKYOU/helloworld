package com.example.jack.zssq.book;

/**
 * Created by Jack on 2017/6/6.
 */

public class Book {
    private  String picUrl;
    private String pic;
    private  String bookname;
    private   String desc;
    private String auther;
    private String type;
    private String date;
    private String newchapter;

    public Book(String picUrl, String bookname, String desc, String auther, String type, String date, String newchapter,String pic) {
        this.picUrl = picUrl;
        this.bookname = bookname;
        this.desc = desc;
        this.auther = auther;
        this.type = type;
        this.date = date;
        this.newchapter = newchapter;
        this.pic=pic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNewchapter() {
        return newchapter;
    }

    public void setNewchapter(String newchapter) {
        this.newchapter = newchapter;
    }
}
