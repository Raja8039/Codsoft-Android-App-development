package com.example.todo_list;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        // Get data passed from MainActivity
        String taskName = getIntent().getStringExtra("task_name");
        int hours = getIntent().getIntExtra("hours", 0);
        int minutes = getIntent().getIntExtra("minutes", 0);

        // Find TextViews in the layout
        TextView taskNameTextView = findViewById(R.id.taskNameTextView);
        TextView taskTimeTextView = findViewById(R.id.taskTimeTextView);

        // Set the task details in the TextViews
        taskNameTextView.setText(taskName);
        taskTimeTextView.setText(getString(R.string.task_time_format, hours, minutes));
    }
}

