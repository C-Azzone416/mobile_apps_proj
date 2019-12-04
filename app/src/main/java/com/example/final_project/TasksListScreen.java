package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.data.DbHelper;
import com.example.final_project.data.TaskTableHelper;
import com.example.final_project.data.dbModels.Tasks;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class TasksListScreen extends AppCompatActivity {

    private TextInputEditText itemET;
    private ListView itemsList;
    private ArrayAdapter<Tasks> adapter;
    private TaskTableHelper mTaskHelper;
    private TextView mTaskNameView;

    public static final String EXTRA_TASK_NAME = "task name";
    public static final String EXTRA_TASK_ADDRESS = "task address";
    public static final String EXTRA_TASK_NOTE = "task note";
    public static final String EXTRA_TASK_PRIORITY = "task priority";
    public static final String EXTRA_TASK_ID = "task id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //the following two lines get the list name from main activity
        Intent intent = getIntent();
        String listName = intent.getStringExtra(ToDoListsScreen.EXTRA_LISTNAME);

        Integer listId = intent.getIntExtra(ToDoListsScreen.EXTRA_LIST_ID, -1);

        super.onCreate(savedInstanceState);
        setTitle(listName);  //this sets the top of the app to show the list name
        setContentView(R.layout.activity_tasks_list_screen);

        mTaskHelper = new TaskTableHelper(this);
        itemET = findViewById(R.id.item_edit_text);
        MaterialButton addBtn = findViewById(R.id.add_item_button);
        itemsList = findViewById(R.id.items_list);
        mTaskNameView = findViewById(R.id.task_title);
        MaterialButton backBtn = findViewById(R.id.back_button);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnCancelButton(view);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskToList(view);
            }
        });
        updateTaskUI();
    }

    @Override
    protected void onResume() {
        updateTaskUI();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        //TODO:  THIS MENU SHOULD HAVE A SWITCH THAT ALLOWS YOU TO ADD A NEW TASK TO THE LIST,  RENAME LIST, AND MAYBE GO BACK TO MAIN LISTS SCREEN?
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void addTaskToList(View view){
        String itemEntered = itemET.getText().toString();
        mTaskHelper.createTask(itemEntered);
        itemET.setText("");
        updateTaskUI();

        //TODO:   FileHelper.writeData(items, this);

        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
    }

    public void OnTaskItemClick(View view){
        openItemDetailActivity(view);
    }

    private void openItemDetailActivity(View view){
        //TODO: FIX THESE
        //      items.remove(position);
        //      adapter.notifyDataSetChanged();
        //      FileHelper.writeData(items, this);
        //      Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();


        ArrayList<Tasks> tasksList = mTaskHelper.getTasks();
        Integer position = itemsList.getPositionForView(view);
        Tasks task = tasksList.get(position);

        Intent intent = new Intent(TasksListScreen.this, ItemDetailScreen.class);
        Integer priority = task.getPriority();
        intent.putExtra(EXTRA_TASK_PRIORITY, priority);

        String name = task.getTaskTitle();
        intent.putExtra(EXTRA_TASK_NAME, name);

        Integer taskId = task.getTaskId();
        intent.putExtra(EXTRA_TASK_ID, taskId);

        String address = task.getTaskAddress();
        intent.putExtra(EXTRA_TASK_ADDRESS, address);

        String note = task.getTaskNote();
        intent.putExtra(EXTRA_TASK_NOTE, note);

        startActivity(intent);
    }


    private void updateTaskUI(){
        ArrayList<Tasks> tasksList = mTaskHelper.getTasks();
        if(adapter == null){
            adapter = new ArrayAdapter<>(this, R.layout.master_task_item, R.id.task_title, tasksList);
            itemsList.setAdapter(adapter);
        }
        else{
            adapter.clear();
            adapter.addAll(tasksList);
            adapter.notifyDataSetChanged();
        }

    }

    private void OnCancelButton(View view){
        Intent intent = new Intent(this, ToDoListsScreen.class);
        startActivity(intent);
    }


}
