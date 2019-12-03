package com.example.final_project.data.dbModels;

public class Lists {
    private Integer mListId;
    private String mListTitle;

    public Lists(Integer num, String listName){
        mListId = num;
        mListTitle = listName;
    }

    public Integer getListId(){
        return mListId;
    }

    public String getmListTitle(){
        return mListTitle;
    }

}
