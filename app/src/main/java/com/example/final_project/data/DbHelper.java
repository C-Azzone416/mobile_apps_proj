package com.example.final_project.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


//CREATES/UPDATES/DELETES DB
public class DbHelper extends SQLiteOpenHelper {

    private final String PRIMARY_KEY_COL_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private final String INTEGER_COL_TYPE = "INTEGER";
    private final String TEXT_NON_NULL_COL_TYPE = "TEXT NOT NULL";

    private final String COLUMN_PATTERN = "%s %s";
    private final String FOREIGN_KEY_PATTERN = "FOREIGN KEY(%s) REFERENCES %s(%s)";

    public DbHelper(@Nullable Context context) {
        super(context, DbContract.DB_NAME, null, DbContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String listTablePattern = "CREATE TABLE %s (%s, %s);";
        String listTableCreateStatement = String.format(listTablePattern,
                // TableName
                DbContract.ListTable.TABLE_NAME,
                // Columns
                String.format(COLUMN_PATTERN, DbContract.ListTable.LIST_ID, PRIMARY_KEY_COL_TYPE),
                String.format(COLUMN_PATTERN, DbContract.ListTable.COL_LIST_TITLE, TEXT_NON_NULL_COL_TYPE));

        db.execSQL(listTableCreateStatement);

        // Pattern string to create a table with 7 columns and a foreign key
        String taskTablePattern = "CREATE TABLE %s (%s, %s, %s, %s, %s, %s, %s, %s);";
        String taskTableCreateStatement = String.format(taskTablePattern,
                // Table Name
                DbContract.TaskTable.TABLE_NAME,
                // Columns
                String.format(COLUMN_PATTERN, DbContract.TaskTable.TASK_ID, PRIMARY_KEY_COL_TYPE),
                String.format(COLUMN_PATTERN, DbContract.TaskTable.FKEY_LIST_ID, INTEGER_COL_TYPE),
                String.format(COLUMN_PATTERN, DbContract.TaskTable.COL_TODO_ITEMS, TEXT_NON_NULL_COL_TYPE),
                String.format(COLUMN_PATTERN, DbContract.TaskTable.COL_IS_CHECKED, INTEGER_COL_TYPE),
                String.format(COLUMN_PATTERN, DbContract.TaskTable.COL_IS_PRIORITY, INTEGER_COL_TYPE),
                String.format(COLUMN_PATTERN, DbContract.TaskTable.COL_TODO_ADDRESS, TEXT_NON_NULL_COL_TYPE),
                String.format(COLUMN_PATTERN, DbContract.TaskTable.COL_TODO_NOTES, TEXT_NON_NULL_COL_TYPE),
                // Foreign Key
                String.format(FOREIGN_KEY_PATTERN, DbContract.TaskTable.FKEY_LIST_ID,
                        DbContract.ListTable.TABLE_NAME, DbContract.ListTable.LIST_ID));

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
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.ListTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TaskTable.TABLE_NAME);

        onCreate(db);
    }


}
