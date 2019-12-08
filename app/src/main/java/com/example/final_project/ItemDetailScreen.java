package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.final_project.data.TaskTableHelper;
import com.example.final_project.data.dbModels.Tasks;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;

public class ItemDetailScreen extends AppCompatActivity {

    private String address;
    private String taskName;
    private String taskNote;
    private Integer taskId;
    private Integer isChecked;
    private Integer priority;
    private TaskTableHelper mTaskTableHelper;
    private MaterialRadioButton checkedButton;
    private Tasks mTask;
    private TextInputEditText addressField;
    private TextInputEditText noteField;
    public static final String EXTRA_TASK_ADDRESS = "taskAddress";

    public static final String EXTRA_LIST_ID = "list id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();

        taskId = intent.getIntExtra(TasksListScreen.EXTRA_TASK_ID, -1);
        mTaskTableHelper = new TaskTableHelper(this);
        mTask = mTaskTableHelper.getSingleTask(taskId);
        taskName = mTask.getTaskTitle();
        address = mTask.getTaskAddress();
        priority = mTask.getPriority();
        taskNote = mTask.getTaskNote();
        isChecked = mTask.getIsChecked();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail_screen);

        setTitle(taskName);
        setPriorityView(priority);

        noteField = findViewById(R.id.notesField);
        noteField.setText(taskNote);

        addressField = findViewById(R.id.addressField);
        addressField.setText(address);

        RadioGroup urgencyRadioGroup = findViewById(R.id.urgencyRadioGroup);
        urgencyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkedButton = (MaterialRadioButton) radioGroup.findViewById(i);
                updatePriority(checkedButton);
            }
        });

    }

    public void setPriorityView(Integer priority){
        RadioGroup group = findViewById(R.id.urgencyRadioGroup);
        switch (priority){

            case 0:
                group.check(R.id.normalPriority);
                break;
            case 1:
                group.check(R.id.urgentPriority);
                break;
            default:
                break;
        }
    }

    public void updatePriority (MaterialRadioButton checkedButton){

        switch(checkedButton.getId()){

            case R.id.normalPriority:
                priority = 0;
                break;
            case R.id.urgentPriority:
                priority = 1;
                break;
            default:
                break;
        }
    }

    public void onMapBtnClick(View view) {
        address = addressField.getText().toString();

        String placeUri = String.format("geo:0,0?q=(%s)", address);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(placeUri));
        startActivity(intent);
    }

    public void onCancel(View view){
        Intent intent = new Intent(this, TasksListScreen.class);
        startActivity(intent);
    }

    public void onSave(View view){
        taskNote = noteField.getText().toString();
        address = addressField.getText().toString();
        mTaskTableHelper.updateTask(taskId, taskName, priority, isChecked, taskNote, address);
        Intent intent = new Intent(this, TasksListScreen.class);
        startActivity(intent);
    }


}
