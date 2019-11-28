package com.example.final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskDbHelper extends SQLiteOpenHelper {
    public TaskDbHelper(@Nullable Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //FIXME:  WE NEED A SECONDARY TABLE_NAME TO HOLD THE LIST ITEMS AND LINK THEM TO THE LISTS
        //   THIS WILL USE A FOREIGN KEY AND THREE COLUMN TABLE_NAME

        String listTable = "CREATE TABLE " + TaskContract.ListTable.TABLE_NAME +
        " ( " + TaskContract.ListTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        TaskContract.ListTable.COL_LIST_TITLE + " TEXT NOT NULL);";

        db.execSQL(listTable);

        String taskTable = "CREATE TABLE " + TaskContract.TaskTable.TABLE_NAME +
                " ( " + TaskContract.TaskTable.TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TaskContract.TaskTable.FOREIGN_ID + " TEXT NOT NULL," +
                TaskContract.TaskTable.COL_TODO_ITEMS + " TEXT NOT NULL);";

        //FIXME:  need to link foreign_id to listTable does not auto increment!
        db.execSQL(taskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.ListTable.TABLE_NAME);
        onCreate(db);
    }
}
