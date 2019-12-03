package com.example.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.final_project.data.ListTableHelper;
import com.example.final_project.data.dbModels.ToDoLists;

import java.util.ArrayList;

public class ToDoListsScreen extends AppCompatActivity {

    private static final String TAG = "placeholder";
    private ListView mTaskListView;
    private ArrayAdapter<ToDoLists> mAdapter;
    private TextView deleteButton;
    public static final String EXTRA_LISTNAME = "list name";
    private ListTableHelper mListTableHelper;
    public static final String EXTRA_LIST_ID = "listid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_lists_screen);
        mListTableHelper = new ListTableHelper(this);
        mTaskListView = findViewById(R.id.master_todo_list);
        deleteButton = findViewById(R.id.list_title);


        //TODO:  SET ONCLICK FOR DELETE BUTTON

        updateListUI();
    }

    @Override
    protected void onResume() {

        super.onResume();
        updateListUI();
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
                                String listName = String.valueOf(taskEditText.getText());
                                //Log.d(TAG, "Task to add: " + task);
                                mListTableHelper.createList(listName);
                                updateListUI();
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

    private void updateListUI() {

        ArrayList<ToDoLists>todoListsList = mListTableHelper.getLists();
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this, R.layout.master_todo_list,
                    R.id.list_title, todoListsList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(todoListsList);
            mAdapter.notifyDataSetChanged();
        }
    }


//    public void deleteList(View view){
//        View parent = (View) view.getParent();
//        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
//        String task = String.valueOf(taskTextView.getText());
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        db.delete(DbContract.ListTable.TABLE_NAME, DbContract.ListTable.COL_LIST_TITLE + " = ?",
//                new String[]{task});
//        db.close();
//        updateListUI();
//    }

    public void detailTask(View view){
        openTasksListScreenActivity(view);
    }

    private void openTasksListScreenActivity(View view) {

        //TODO:  TESTING
       ArrayList<ToDoLists>todoListsList = mListTableHelper.getLists();
       Integer position = mTaskListView.getPositionForView(view);

//     View parent = (View) view.getParent();
//     TextView listNameTextView = parent.findViewById(R.id.list_title);
//     String listName = String.valueOf(listNameTextView.getText());

        ToDoLists list = todoListsList.get(position);
        Integer listId = list.getListId();
        String listName = list.getListTitle();

        Intent intent = new Intent(this, TasksListScreen.class);
        intent.putExtra(EXTRA_LISTNAME, listName);
        intent.putExtra(EXTRA_LIST_ID, listId);

        startActivity(intent);
    }

}
