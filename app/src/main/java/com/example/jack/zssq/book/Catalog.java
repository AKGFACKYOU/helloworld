package com.example.jack.zssq.book;

/**
 * 位置：
 * 作用：
 * 时间：2017/6/7
 */

public class Catalog {
    private  String listContext;
    private   String catalogUrl;

    public Catalog(String listContext, String catalogUrl) {
        this.listContext = listContext;
        this.catalogUrl = catalogUrl;
    }

    public String getCatalogUrl() {
        return catalogUrl;
    }

    public void setCatalogUrl(String catalogUrl) {
        this.catalogUrl = catalogUrl;
    }

    public String getListContext() {
        return listContext;
    }

    public void setListContext(String listContext) {
        this.listContext = listContext;
    }
}
