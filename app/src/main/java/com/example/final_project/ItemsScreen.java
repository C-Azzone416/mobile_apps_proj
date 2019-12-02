package com.example.final_project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.final_project.data.TaskContract;
import com.example.final_project.data.TaskDbHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ItemsScreen extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private TextInputEditText itemET;
    private MaterialButton addBtn;
    private ListView itemsList;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;
    private TaskDbHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //the following two lines get the list name from main activity
        Intent intent = getIntent();
        String listName = intent.getStringExtra(MasterListScreen.EXTRA_LISTNAME);

        super.onCreate(savedInstanceState);
        setTitle(listName);  //this sets the top of the app to show the list name
        setContentView(R.layout.items_screen_activity);


        itemET = findViewById(R.id.item_edit_text);
        addBtn = findViewById(R.id.add_item_button);
        itemsList = findViewById(R.id.items_list);

        items = FileHelper.readData(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_multichoice, items);
        itemsList.setAdapter(adapter);

        addBtn.setOnClickListener(this);

        itemsList.setOnItemClickListener(this);

        //updateTaskList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        //TODO:  THIS MENU SHOULD HAVE A SWITCH THAT ALLOWS YOU TO ADD A NEW TASK TO THE LIST,  RENAME LIST, AND MAYBE GO BACK TO MAIN LISTS SCREEN?
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_item_button:
                String itemEntered = itemET.getText().toString();
                adapter.add(itemEntered);
                itemET.setText("");

                //TODO:   FileHelper.writeData(items, this);

                Toast.makeText(this, "Item Added", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //TODO: FIX THESE
        //      items.remove(position);
        //      adapter.notifyDataSetChanged();
        //      FileHelper.writeData(items, this);
        //      Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ItemsScreen.this, Individual_Items.class);


        //TODO: Something here to move item name to top of next activity screen
        //      String message = itemsList.....
        startActivity(intent);
    }

    private void updateTaskList(){
        ArrayList<String> listList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskTable.TABLE_NAME,
            new String[]{TaskContract.TaskTable.TASK_ID,
            TaskContract.TaskTable.FKEY_LIST_ID,
            TaskContract.TaskTable.COL_TODO_ITEMS},
                null, null, null, null, null);


    }

}
