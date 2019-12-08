package com.example.final_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.adapter.TaskAdapter;
import com.example.final_project.data.TaskTableHelper;
import com.example.final_project.data.dbModels.Tasks;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class TasksListScreen extends AppCompatActivity {

    private TextInputEditText itemET;
    private ListView itemsList;
    private TaskAdapter adapter;
    private TaskTableHelper mTaskHelper;
    private Integer listId;
    private String listName;
    private static Integer tempId;
    private static String tempName;
    private ImageButton deleteButton;
    private TextView taskTV;

    public static final String EXTRA_TASK_ID = "task id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //the following two lines get the list name from main activity
        Intent intent = getIntent();
        listName = intent.getStringExtra(ToDoListsScreen.EXTRA_LISTNAME);
        listId = intent.getIntExtra(ToDoListsScreen.EXTRA_LIST_ID, -1);

        super.onCreate(savedInstanceState);
        setTitle(listName);  //this sets the top of the app to show the list name
        setContentView(R.layout.activity_tasks_list_screen);

        mTaskHelper = new TaskTableHelper(this);
        itemET = findViewById(R.id.item_edit_text);
        MaterialButton addBtn = findViewById(R.id.add_item_button);
        itemsList = findViewById(R.id.items_list);
        MaterialButton backBtn = findViewById(R.id.back_button);
        deleteButton = findViewById(R.id.todo_delete);
        taskTV = findViewById(R.id.task_title);

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
    protected void onStop() {
        super.onStop();
        tempId = listId;
        tempName = listName;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(listId < 0) {
            listId = tempId;
            listName = tempName;
            setTitle(listName);
        }
        updateTaskUI();
    }

    private void addTaskToList(View view){
        String itemEntered = itemET.getText().toString();
        mTaskHelper.createTask(itemEntered, listId);
        itemET.setText("");
        updateTaskUI();

        Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
    }


    public void deleteTask(View view){

        ArrayList<Tasks> tasksList = mTaskHelper.getAllTasks(listId);
        Integer position = itemsList.getPositionForView(view);
        Tasks task = tasksList.get(position);

        mTaskHelper.deleteSingleTask(task.getTaskId());
        updateTaskUI();
    }

    public void OnTaskItemClick(View view){
        openItemDetailActivity(view);
    }

    private void openItemDetailActivity(View view){

        ArrayList<Tasks> tasksList = mTaskHelper.getAllTasks(listId);
        Integer position = itemsList.getPositionForView(view);
        Tasks task = tasksList.get(position);

        Intent intent = new Intent(TasksListScreen.this, ItemDetailScreen.class);
        Integer taskId = task.getTaskId();
        intent.putExtra(EXTRA_TASK_ID, taskId);
        startActivity(intent);
    }

  private void updateTaskUI(){
    ArrayList<Tasks> tasksList = mTaskHelper.getAllTasks(listId);
    if(adapter == null){
        adapter = new TaskAdapter(this, R.layout.master_task_item, tasksList);
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
