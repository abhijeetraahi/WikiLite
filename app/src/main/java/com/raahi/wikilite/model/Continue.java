package com.raahi.wikilite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Raahi on 24-06-2018.
 */

public class Continue implements Serializable{
    @SerializedName("gpsoffset")
    @Expose
    private Integer gpsoffset;
    @SerializedName("continue")
    @Expose
    private String _continue;

    public Integer getGpsoffset() {
        return gpsoffset;
    }

    public void setGpsoffset(Integer gpsoffset) {
        this.gpsoffset = gpsoffset;
    }

    public String getContinue() {
        return _continue;
    }

    public void setContinue(String _continue) {
        this._continue = _continue;
    }
}
