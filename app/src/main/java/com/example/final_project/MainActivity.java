package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton launchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launchBtn = findViewById(R.id.launchBtn);
        launchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemsScreenActivity();
            }
        });

       
    }

    private void openItemsScreenActivity() {
        Intent intent = new Intent(this, ItemsScreen.class);
        startActivity(intent);
    }


    public void onClick(View view) {
        openItemsScreenActivity();
    }
}
