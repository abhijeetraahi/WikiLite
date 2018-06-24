package com.raahi.wikilite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raahi on 24-06-2018.
 */

public class Terms implements Serializable{
    @SerializedName("description")
    @Expose
    private List<String> description = null;

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }
}
