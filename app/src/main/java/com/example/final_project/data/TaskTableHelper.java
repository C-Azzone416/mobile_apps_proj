package com.example.final_project.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.final_project.data.dbModels.Tasks;

import java.util.ArrayList;


//manages TaskTable from DB
public class TaskTableHelper {
    public DbHelper mHelper;

    public TaskTableHelper(Context context){
        mHelper = new DbHelper(context);
    }

    public void createTask(String taskName, Integer listId){

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.TaskTable.COL_TODO_ITEMS, taskName);
        values.put(DbContract.TaskTable.COL_TODO_ADDRESS, "");
        values.put(DbContract.TaskTable.COL_TODO_NOTES, "");
        values.put(DbContract.TaskTable.COL_IS_CHECKED, 0);
        values.put(DbContract.TaskTable.COL_IS_PRIORITY, 0);
        values.put(DbContract.TaskTable.FKEY_LIST_ID, listId);
        db.insertWithOnConflict(DbContract.TaskTable.TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public ArrayList<Tasks> getTasks(Integer listId){
        ArrayList<Tasks> mTasksArray = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        //better way
        String selectQuery = "SELECT * FROM " + DbContract.TaskTable.TABLE_NAME + " WHERE " +
                DbContract.TaskTable.FKEY_LIST_ID + " = " + listId;

        Cursor cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToNext()) {

            Integer idx = cursor.getColumnIndex(DbContract.TaskTable.TASK_ID);
            Integer taskId = cursor.getInt(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_IS_PRIORITY);
            Integer isPriority = cursor.getInt(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_IS_CHECKED);
            Integer isChecked = cursor.getInt(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.FKEY_LIST_ID);
            Integer fKey = cursor.getInt(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_TODO_ITEMS);
            String taskName = cursor.getString(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_TODO_NOTES);
            String taskNote = cursor.getString(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_TODO_ADDRESS);
            String taskAddress = cursor.getString(idx);

            mTasksArray.add(new Tasks(taskId, taskName, fKey, taskNote, taskAddress, isChecked, isPriority));
            }

        cursor.close();
        db.close();

        return mTasksArray;
    }

    //TODO:  NEED TO HAVE A WAY TO UPDATE DB INFO

    public void updateTask(Integer taskId, String taskName, Integer priority, Integer isChecked, String notes, String address){


        SQLiteDatabase db = mHelper.getWritableDatabase();
        //update task tools
        String updateTaskPattern = "UPDATE %s SET %s, %s, %s, %s, %s WHERE %s;";
        String UPDATE_PATTERN = "%s %s '%s'";
        String taskTableUpdateStatement = String.format(updateTaskPattern,
                // Table Name
                DbContract.TaskTable.TABLE_NAME,
                // Columns
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_TODO_ITEMS, "=", taskName),
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_IS_CHECKED, "=", isChecked),
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_IS_PRIORITY, "=", priority),
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_TODO_NOTES, "=", notes),
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_TODO_ADDRESS, "=", address),
                //WHERE
                String.format(UPDATE_PATTERN, DbContract.TaskTable.TASK_ID, "=", taskId));

        db.execSQL(taskTableUpdateStatement);
        db.close();
    }

}
