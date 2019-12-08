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
    private final String UPDATE_PATTERN = "%s = '%s'";
    private final String LIST_DELETE_PATTERN = "%s = '%s'";

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

    public Tasks getSingleTask(Integer taskId){

        Tasks task;
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + DbContract.TaskTable.TABLE_NAME + " WHERE " +
                DbContract.TaskTable.TASK_ID + " = " + taskId;

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        Integer idx = cursor.getColumnIndex(DbContract.TaskTable.COL_IS_PRIORITY);
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
        task = new Tasks(taskId, taskName, fKey, taskNote, taskAddress, isChecked, isPriority);

        cursor.close();
        db.close();

        return task;
    }

    public ArrayList<Tasks> getAllTasks(Integer listId){
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

    public void updateIsChecked(Integer taskId, boolean b){
        Integer isChecked;
        if(b){
            isChecked = 1;
        }
        else{
            isChecked = 0;
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //update helpers
        String updateIsCheckedPattern = "UPDATE %s SET %s WHERE %s;";
        String taskTableUpdateStatement = String.format(updateIsCheckedPattern,
                // Table Name
                DbContract.TaskTable.TABLE_NAME,
                // Columns
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_IS_CHECKED, isChecked),
                //WHERE
                String.format(UPDATE_PATTERN, DbContract.TaskTable.TASK_ID, taskId));

        db.execSQL(taskTableUpdateStatement);
        db.close();
    }

    public void updateTask(Integer taskId, String taskName, Integer priority, Integer isChecked, String notes, String address){


        SQLiteDatabase db = mHelper.getWritableDatabase();
        //update task helpers
        String updateTaskPattern = "UPDATE %s SET %s, %s, %s, %s, %s WHERE %s;";
        String taskTableUpdateStatement = String.format(updateTaskPattern,
                // Table Name
                DbContract.TaskTable.TABLE_NAME,
                // Columns
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_TODO_ITEMS, taskName),
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_IS_CHECKED, isChecked),
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_IS_PRIORITY, priority),
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_TODO_NOTES, notes),
                String.format(UPDATE_PATTERN, DbContract.TaskTable.COL_TODO_ADDRESS, address),
                //WHERE
                String.format(UPDATE_PATTERN, DbContract.TaskTable.TASK_ID, taskId));

        db.execSQL(taskTableUpdateStatement);
        db.close();
    }

    public void deleteSingleTask(Integer taskId){

        SQLiteDatabase db = mHelper.getWritableDatabase();
        //delete task helpers
        String deleteListPattern = "DELETE FROM %s WHERE %s";
        String deleteListStatement = String.format(deleteListPattern,
                //TABLE NAME
                DbContract.TaskTable.TABLE_NAME,
                //LIST TO DELETE CONDITION
                String.format(LIST_DELETE_PATTERN, DbContract.TaskTable.TASK_ID, taskId));
        db.execSQL(deleteListStatement);
        db.close();
    }

    public void deleteAllTasksFromList(Integer fkey){

        SQLiteDatabase db = mHelper.getWritableDatabase();
        String deleteListPattern = "DELETE FROM %s WHERE %s";
        String deleteListStatement = String.format(deleteListPattern,
                //TABLE NAME
                DbContract.TaskTable.TABLE_NAME,
                //LIST TO DELETE CONDITION
                String.format(LIST_DELETE_PATTERN, DbContract.TaskTable.FKEY_LIST_ID, fkey));
        db.execSQL(deleteListStatement);
        db.close();
    }

}
