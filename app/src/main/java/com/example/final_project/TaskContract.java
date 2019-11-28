package com.example.final_project;

import android.provider.BaseColumns;

public class TaskContract {

    public static final String DB_NAME = "com.example.final_project";
    public static final int DB_VERSION = 1;
    //TODO:  NEED TO UPDATE VERSION OF DB

    public class ListTable implements BaseColumns{
        public static final String TABLE_NAME = "primaryLists";
        public static final String COL_LIST_TITLE = "listTitle";
    }


    public class TaskTable implements BaseColumns{
        public static final String TABLE_NAME = "individualTasks";
        public static final String TASK_ID = TABLE_NAME + _ID;
        public static final String FOREIGN_ID = "primaryList";
        public static final String COL_TODO_ITEMS = "listItems";

    }

}
