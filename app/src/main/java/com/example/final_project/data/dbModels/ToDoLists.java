package com.example.final_project.data.dbModels;

//creates List object
public class ToDoLists {
    private Integer mListId;
    private String mListTitle;

    public ToDoLists(Integer num, String listName){
        mListId = num;
        mListTitle = listName;
    }

    public Integer getListId(){
        return mListId;
    }

    public String getListTitle(){
        return mListTitle;
    }

    public String toString(){
        return mListTitle;
    }

}
