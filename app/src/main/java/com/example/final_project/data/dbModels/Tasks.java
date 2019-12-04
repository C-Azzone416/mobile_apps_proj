package com.example.final_project.data.dbModels;


//creates a TASK object that stores values
public class Tasks {
    private Integer mTaskId;
    private String mTaskTitle;
    private Integer mForeignKey;
    private Integer mIsChecked;
    private Integer mHasPriority;
    private String mTaskAddress;
    private String mTaskNote;

    public Tasks(Integer num, String taskName, Integer fKey){
        mTaskId = num;
        mTaskTitle = taskName;
        mForeignKey = fKey;
        mIsChecked = 0;
        mHasPriority = 0;
        mTaskAddress = "";
        mTaskNote = "";
    }

    public String toString(){
        return mTaskTitle;
    }

    public Integer getTaskId(){return mTaskId;}

    public String getTaskTitle(){return mTaskTitle;}

    public Integer getmForiegnKey(){return mForeignKey;    }

    public Integer getIsChecked(){return mIsChecked;}

    public Integer getPriority(){return mHasPriority;}

    public String getTaskAddress(){return mTaskAddress;}

    public String getTaskNote(){return mTaskNote;}
}
