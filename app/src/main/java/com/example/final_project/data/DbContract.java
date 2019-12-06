package com.example.final_project.data;

import android.provider.BaseColumns;

public class DbContract {

    public static final String DB_NAME = "com.example.final_project";
    public static final int DB_VERSION = 6;
    //TODO:  NEED TO UPDATE VERSION OF DB AFTER CHANGES

    public class ListTable implements BaseColumns{
        public static final String TABLE_NAME = "lists";
        public static final String LIST_ID = TABLE_NAME + _ID;
        public static final String COL_LIST_TITLE = "list_title";
    }

    public class TaskTable implements BaseColumns{
        public static final String TABLE_NAME = "tasks";
        public static final String TASK_ID = TABLE_NAME + _ID;
        public static final String FKEY_LIST_ID = ListTable.LIST_ID;
        public static final String COL_TODO_ITEMS = "task_title";
        public static final String COL_IS_CHECKED = "is_complete";
        public static final String COL_IS_PRIORITY = "is_priority";
        public static final String COL_TODO_ADDRESS = "todo_address";
        public static final String COL_TODO_NOTES = "todo_notes";
    }

}
