package com.example.final_project.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TaskDbHelper extends SQLiteOpenHelper {

    private final String PRIMARY_KEY_COL_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private final String INTEGER_COL_TYPE = "INTEGER";
    private final String TEXT_NON_NULL_COL_TYPE = "TEXT NOT NULL";

    private final String COLUMN_PATTERN = "%s %s";
    private final String FOREIGN_KEY_PATTERN = "FOREIGN KEY(%s) REFERENCES %s(%s)";

    public TaskDbHelper(@Nullable Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String listTablePattern = "CREATE TABLE %s (%s, %s);";
        String listTableCreateStatement = String.format(listTablePattern,
                // TableName
                TaskContract.ListTable.TABLE_NAME,
                // Columns
                String.format(COLUMN_PATTERN, TaskContract.ListTable.LIST_ID, PRIMARY_KEY_COL_TYPE),
                String.format(COLUMN_PATTERN, TaskContract.ListTable.COL_LIST_TITLE, TEXT_NON_NULL_COL_TYPE));

        db.execSQL(listTableCreateStatement);

        // Pattern string to create a table with 3 columns and a foreign key
        String taskTablePattern = "CREATE TABLE %s (%s, %s, %s, %s, %s, %s, %s, %s);";
        String taskTableCreateStatement = String.format(taskTablePattern,
                // Table Name
                TaskContract.TaskTable.TABLE_NAME,
                // Columns
                String.format(COLUMN_PATTERN, TaskContract.TaskTable.TASK_ID, PRIMARY_KEY_COL_TYPE),
                String.format(COLUMN_PATTERN, TaskContract.TaskTable.FKEY_LIST_ID, INTEGER_COL_TYPE),
                String.format(COLUMN_PATTERN, TaskContract.TaskTable.COL_TODO_ITEMS, TEXT_NON_NULL_COL_TYPE),
                String.format(COLUMN_PATTERN, TaskContract.TaskTable.COL_IS_CHECKED, INTEGER_COL_TYPE),
                String.format(COLUMN_PATTERN, TaskContract.TaskTable.COL_IS_PRIORITY, INTEGER_COL_TYPE),
                String.format(COLUMN_PATTERN, TaskContract.TaskTable.COL_TODO_ADDRESS, TEXT_NON_NULL_COL_TYPE),
                String.format(COLUMN_PATTERN, TaskContract.TaskTable.COL_TODO_NOTES, TEXT_NON_NULL_COL_TYPE),
                // Foreign Key
                String.format(FOREIGN_KEY_PATTERN, TaskContract.TaskTable.FKEY_LIST_ID,
                        TaskContract.ListTable.TABLE_NAME, TaskContract.ListTable.LIST_ID));

        db.execSQL(taskTableCreateStatement);

        // TODO: setup Index on ListId in Task Table
        /**
         * example create statement
         * CREATE [UNIQUE] INDEX index_name
         * ON table_name(column_list);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.ListTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskTable.TABLE_NAME);

        onCreate(db);
    }


}
