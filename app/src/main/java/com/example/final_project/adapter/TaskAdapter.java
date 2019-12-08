package com.example.final_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.final_project.R;
import com.example.final_project.data.TaskTableHelper;
import com.example.final_project.data.dbModels.Tasks;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Tasks>{


    public TaskAdapter(@NonNull Context context, int resource, @NonNull List<Tasks> objects) {

        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View taskView, ViewGroup container) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        taskView = inflater.inflate(R.layout.master_task_item, container, false);

        Tasks taskForView = getItem(position);
        ((TextView) taskView.findViewById(R.id.task_title))
            .setText(taskForView.getTaskTitle());

        Integer checked = taskForView.getIsChecked();
        Boolean isChecked = false;
        if(checked == 1 ){
            isChecked = true;
        }

        final Integer taskId = taskForView.getTaskId();
        final TaskTableHelper tableHelper = new TaskTableHelper(getContext());

        MaterialCheckBox checkBox = taskView.findViewById(R.id.task_checkable);
        checkBox.setChecked(isChecked);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               tableHelper.updateIsChecked(taskId, b);
            }
        });
        return taskView;
    }

}
