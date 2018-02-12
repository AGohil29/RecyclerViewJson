package com.example.arunr.recyclerviewjson;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by arun.r on 12-02-2018.
 */

public class DataList {

    private String name;
    private String genre;
    private String images;
    private String id;

    public String getId() {
        return id;
    }

    public HashMap<String, JSONArray> getImageList() {
        return imageList;
    }

    HashMap<String, JSONArray> imageList;

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getImages() {
        return images;
    }

    public DataList(String id, String name, String genre, String images, HashMap<String, JSONArray> list) {
        this.name = name;
        this.id = id;
        this.genre = genre;
        this.images = images;
        this.imageList = list;
    }
}
