package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ItemDetailScreen extends AppCompatActivity {

    String address = "";

    public static final String EXTRA_TASK_NAME = "task name";
    public static final String EXTRA_TASK_ADDRESS = "task address";
    public static final String EXTRA_TASK_NOTE = "task note";
    public static final String EXTRA_TASK_PRIORITY = "task priority";
    public static final String EXTRA_TASK_ID = "task id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        String taskName = intent.getStringExtra(TasksListScreen.EXTRA_TASK_NAME);

        Integer taskId = intent.getIntExtra(TasksListScreen.EXTRA_TASK_ID, -1);
        super.onCreate(savedInstanceState);
        setTitle(taskName);
        setContentView(R.layout.activity_item_detail_screen);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

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
