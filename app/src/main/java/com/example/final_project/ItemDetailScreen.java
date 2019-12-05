package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

public class ItemDetailScreen extends AppCompatActivity {

    private String address;
    private String taskName;
    private String taskNote;
    private Integer taskId;
    private Integer listId;
    private Integer priority;
    private TasksListScreen previousScreen;

    private TextInputEditText noteField;

    public static final String EXTRA_LIST_ID = "list id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        taskName = intent.getStringExtra(TasksListScreen.EXTRA_TASK_NAME);
        address = intent.getStringExtra(TasksListScreen.EXTRA_TASK_ADDRESS);
        taskId = intent.getIntExtra(TasksListScreen.EXTRA_TASK_ID, -1);
        priority = intent.getIntExtra(TasksListScreen.EXTRA_TASK_PRIORITY, -1);
        taskNote = intent.getStringExtra(TasksListScreen.EXTRA_TASK_NOTE);
        listId = intent.getIntExtra(TasksListScreen.EXTRA_LIST_ID, -1);

        super.onCreate(savedInstanceState);
        setTitle(taskName);
        setContentView(R.layout.activity_item_detail_screen);
        setPriority(priority);

        noteField = findViewById(R.id.notesField);
        //FIXME:  TEST INPUT
        taskNote = "My dogs rock";
        noteField.setText(taskNote);

    }

    public void setPriority(Integer priority){
        RadioGroup group = findViewById(R.id.urgencyRadioGroup);
        switch (priority){
            case 0:
                group.check(R.id.lowPriority);
                break;
            case 1:
                group.check(R.id.normalPriority);
                break;
            case 2:
                group.check(R.id.urgentPriority);
                break;
            default:
                break;
        }
    }

    public void setAddress(String address){
        //TODO: ADD ADDRESS FIELD SO CAN DISPLAY ADDRESS
    }

    public void onMapBtnClick(View view) {
        String place = address;
        String placeUri = String.format("geo:0,0?q=(%s)", place);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(placeUri));
        startActivity(intent);
    }

    public void onCancel(View view){
        Intent intent = new Intent(this, TasksListScreen.class);
        startActivity(intent);
    }

}
