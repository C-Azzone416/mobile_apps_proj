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

    public void createTask(String taskName){

        //TODO:  THIS NEEDS TO ALSO PASS THE FOREIGN KEY?
        //       what about other values?
        //       THIS CREATES A NEW ROW FOR THE NEW TASK
        //       MUST MAKE SURE IT GETS THE CORRECT FOREIGN KEY OR WONT LINK TO CORRECT LIST

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.TaskTable.COL_TODO_ITEMS, taskName);
        values.put(DbContract.TaskTable.COL_TODO_ADDRESS, "");
        values.put(DbContract.TaskTable.COL_TODO_NOTES, "");
        values.put(DbContract.TaskTable.COL_IS_CHECKED, 0);
        values.put(DbContract.TaskTable.COL_IS_PRIORITY, 0);
        db.insertWithOnConflict(DbContract.TaskTable.TABLE_NAME, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }


    //TODO: FINISH STUB  fix fkey args
    public ArrayList<Tasks> getTasks(){
        ArrayList<Tasks> mTasksArray = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DbContract.TaskTable.TABLE_NAME,
                new String[]{DbContract.TaskTable.TASK_ID,
                        DbContract.TaskTable.FKEY_LIST_ID,
                        DbContract.TaskTable.COL_TODO_ITEMS,
                        DbContract.TaskTable.COL_IS_CHECKED,
                        DbContract.TaskTable.COL_IS_PRIORITY,
                        DbContract.TaskTable.COL_TODO_ADDRESS,
                        DbContract.TaskTable.COL_TODO_NOTES,},
                null, null, null, null, null);
        while(cursor.moveToNext()){

            //FIXME:  MAY NEED TO MOVE SOME OF THE ABOVE CODE TO A NEW FILE FOR TASK DETAIL.
            //          OR JUST BUNDLE HERE AND PASS THE WHOLE TASK AS INTENT?
            int idx = cursor.getColumnIndex(DbContract.TaskTable.TASK_ID);
            Integer taskId = cursor.getInt(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_IS_PRIORITY);
            Integer isPriority = cursor.getInt(idx);

            idx = cursor.getColumnIndex(DbContract.TaskTable.COL_TODO_ITEMS);
            String taskName = cursor.getString(idx);

            mTasksArray.add(new Tasks(taskId, taskName, isPriority));
        }
        cursor.close();
        db.close();

        return mTasksArray;
    }





}
