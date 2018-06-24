package com.raahi.wikilite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raahi on 24-06-2018.
 */

public class Query implements Serializable{
    @SerializedName("pages")
    @Expose
    private List<Page> pages = null;

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
