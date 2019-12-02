package com.example.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.final_project.data.TaskContract;
import com.example.final_project.data.TaskDbHelper;

import java.util.ArrayList;

public class MasterListScreen extends AppCompatActivity {

    private static final String TAG = "placeholder";
    private ListView mTaskListView;
    private TaskDbHelper mHelper;
    private ArrayAdapter<String> mAdapter;
    private TextView detailButton;
    public static final String EXTRA_LISTNAME = "list name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list_screen);

        mHelper = new TaskDbHelper(this);
        mTaskListView = findViewById(R.id.master_todo_list);
        detailButton = findViewById(R.id.task_title);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        //TODO:  I think it may be worth trying to add some options to the menu on each screen
        //        for example this one has add a list and maybe add a "logout" on this page
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_task:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a New Task")
                        .setMessage("What would you like to Add?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                //Log.d(TAG, "Task to add: " + task);
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.ListTable.COL_LIST_TITLE, task);
                                db.insertWithOnConflict(TaskContract.ListTable.TABLE_NAME, null, values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        // TODO: consider moving this to a new file, ListTableHelper, which makes nice Java
        //  interface to the database. Methods like:
        //      public ArrayList<String> GetLists()
        //      public void createList(String listName)
        //      public void deleteList(String listName)
        //      public void renameList(String oldName, String newName)

        // TODO: consider making a class, TaskList, that we can use to represent lists
        //  rather than just passing around the list name as a string.
        //      Q: how would you handle two lists with the same name?
        //      Q: If you dont have list Id (only name) how will you query tasks Db?
        ArrayList<String> listList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.ListTable.TABLE_NAME,
                new String[]{TaskContract.ListTable.LIST_ID, TaskContract.ListTable.COL_LIST_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.ListTable.COL_LIST_TITLE);
            listList.add(cursor.getString(idx));
        }
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.master_todo_item,
                    R.id.task_title, listList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(listList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }


    // TODO: Rename to deleteList
    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.ListTable.TABLE_NAME, TaskContract.ListTable.COL_LIST_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    // TODO: remove this, just call openItemsScreenActivity?
    public void detailTask(View view){
        openItemsScreenActivity(view);
    }

    private void openItemsScreenActivity(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());

        Intent intent = new Intent(this, ItemsScreen.class);
        intent.putExtra(EXTRA_LISTNAME, task);
        startActivity(intent);
    }
}
